"use client";
import { useState, useEffect } from "react";
import axios from "axios";
import Image from "next/image";
import NavBar from "../Components/NavBar";
import ActiveOrderItem from "../Components/ActiveOrderItem";

const API_BASE_URL = "http://localhost:8080/api";

export default function ActiveOrdersPage() {
    const [orders, setOrders] = useState([]);
    const [customer, setCustomer] = useState(null);

    useEffect(() => {
        const savedCustomer = localStorage.getItem("customer") || sessionStorage.getItem("customer");
        if (savedCustomer) {
            const cust = JSON.parse(savedCustomer);
            setCustomer(cust);
            loadActiveOrders(cust.id);
        }
    }, []);

    const loadActiveOrders = async (customerId) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/orders/customer/${customerId}`);
            const allOrders = response.data;

            // Filter for active orders only (not DELIVERED or CANCELLED)
            const activeOrders = allOrders.filter(order =>
                order.status !== "DELIVERED" && order.status !== "CANCELLED"
            );

            setOrders(activeOrders);

            // Start status progression for each active order
            activeOrders.forEach(order => {
                startOrderProgression(order);
            });
        } catch (error) {
            console.error("Failed to load orders:", error);
        }
    };

    const startOrderProgression = async (order) => {
        const orderId = order.id;

        try {
            // Step 1: PREPARING (10 seconds)
            await updateOrderStatus(orderId, "PREPARING");
            await new Promise(resolve => setTimeout(resolve, 10000));

            // Check if order still exists before continuing
            if (!orders.find(o => o.id === orderId)) return;

            // Step 2: OUT_FOR_DELIVERY (20 seconds)
            await updateOrderStatus(orderId, "OUT_FOR_DELIVERY");
            await new Promise(resolve => setTimeout(resolve, 20000));

            // Check if order still exists before continuing
            if (!orders.find(o => o.id === orderId)) return;

            // Step 3: DELIVERED (5 seconds, then remove)
            await updateOrderStatus(orderId, "DELIVERED");
            await new Promise(resolve => setTimeout(resolve, 5000));

            // Remove from active orders
            setOrders(prev => prev.filter(o => o.id !== orderId));
        } catch (error) {
            console.error(`Failed to progress order ${orderId}:`, error);
        }
    };

    const updateOrderStatus = async (orderId, status) => {
        try {
            const response = await axios.put(
                `${API_BASE_URL}/orders/${orderId}/status`,
                { status }
            );

            const updatedOrder = response.data;

            // Update local state
            setOrders(prev => prev.map(o =>
                o.id === orderId ? updatedOrder : o
            ));
        } catch (error) {
            console.error("Failed to update order status:", error);
            throw error;
        }
    };

    const getStatusDisplay = (status) => {
        switch (status) {
            case "PREPARING":
                return "prep";
            case "OUT_FOR_DELIVERY":
                return "otw";
            case "DELIVERED":
                return "done";
            default:
                return "prep";
        }
    };

    const getETA = (status) => {
        switch (status) {
            case "PREPARING":
                return "10 mins";
            case "OUT_FOR_DELIVERY":
                return "8 mins";
            case "DELIVERED":
                return "Arrived";
            default:
                return "Calculating...";
        }
    };

    return (
        <div className="flex min-h-screen bg-zinc-50 font-sans pt-0 p-4">
            <NavBar activeOrders={orders.length} />

            <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
                <h1 className="text-4xl font-bold">Active Orders</h1>
                <h2 className="text-black opacity-60 text-lg">
                    Track your robot deliveries in real-time
                </h2>

                {orders.length === 0 ? (
                    <div className="text-center py-12 bg-white rounded-2xl shadow-sm border-2 border-gray-200">
                        <div className="flex flex-col items-center gap-4">
                            <Image
                                src="/logo.svg"
                                width={100}
                                height={100}
                                alt="NavidEats logo"
                                className="opacity-50"
                            />
                            <p className="text-xl text-gray-500 mb-2">No active orders</p>
                            <p className="text-gray-400">
                                Place an order from the Restaurants page to see it here!
                            </p>
                        </div>
                    </div>
                ) : (
                    <div className="flex flex-col gap-4">
                        {orders.map(order => (
                            <ActiveOrderItem
                                key={order.id}
                                restaurantName={order.restaurantName || "Restaurant"}
                                orderId={order.id.toString()}
                                cost={parseFloat(order.totalPrice).toFixed(2)}
                                botName={`Bot-${String(order.id % 10).padStart(2, '0')}`}
                                eta={getETA(order.status)}
                                status={getStatusDisplay(order.status)}
                                trackerLink="https://www.google.com/maps"
                            />
                        ))}
                    </div>
                )}
            </main>
        </div>
    );
}