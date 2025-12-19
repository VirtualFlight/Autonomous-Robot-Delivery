"use client";
import Image from "next/image";
import Link from "next/link";
import NavBar from "../Components/NavBar"
import OrderHistoryItem from "../Components/OrderHistoryItem"
import { useState, useMemo } from "react";

export default function Home() {
    const [orders, setOrders] = useState([ //ORDERS HERE
      { item: "Green Bowl", cost: 44.61, id: "ORD-1240", time: "2 days", tags: ["Ceaser Salad", "Greek Salad"], stars: 0 },
      { item: "Pickle Burger", cost: 12.67, id: "ORD-8913", time: "1 week", tags: ["Pickles", "Hamburger", "Halal"], stars: 0 },
      { item: "Pickle Burger", cost: 10.41, id: "ORD-6820", time: "2 years", tags: ["Pickles", "Hamburger", "Halal"], stars: 5 }
    ]);

    const [reorders, setReorders] = useState([]);

    const stats = useMemo(() => {
      if (orders.length === 0) {
        return {
          totalOrders: 0,
          totalSpent: 0,
          avgRating: 0,
        };
      }
      const totalOrders = orders.length;
      const totalSpent = orders.reduce((sum, order) => sum + order.cost, 0);
      const avgRating = orders.reduce((sum, order) => sum + order.stars, 0) / totalOrders;

      return {
        totalOrders,
        totalSpent,
        avgRating: avgRating.toFixed(1)
      };
    }, [orders]);

    const handleReorder = (orderData) => {
      console.log("Reorder clicked:", orderData);
      
      const newReorder = { // add to reorders list
        ...orderData,
        reorderDate: new Date().toISOString(),
        reorderId: `REORDER-${Date.now()}`
      };
      
      setReorders(prev => [...prev, newReorder]);
      
      // Optional: Update the order's stars if they were changed
      setOrders(prev => prev.map(order => {
        if (order.id === orderData.id && order.item === orderData.item) {
          return { ...order, stars: orderData.stars };
        }
        return order;
      }));
      
      alert(`Added "${orderData.item}" to reorders!\n\nOrder Data:\n- Item: ${orderData.item}\n- Cost: $${orderData.cost}\n- ID: ${orderData.id}\n- Stars: ${orderData.stars}/5\n\nCheck console for full data.`);
    };
    
    const handleStarUpdate = (orderId, itemName, newStars) => { // rating update
      setOrders(prev => prev.map(order => {
        if (order.id === orderId && order.item === itemName) {
          return { ...order, stars: newStars };
        }
        return order;
      }));
    };



  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans pt-0 p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <h1>Order History</h1>
        <h2 className="text-gray-500">View your past deliveries and reorder favourites</h2>
        <div className="flex h-1/5 w-full gap-8">
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between hover:scale-102 hover:shadow-md duration-300">
            <p className="text-gray-500 text-2xl">Total Orders</p>
            <p className="text-3xl">{stats.totalOrders}</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between hover:scale-102 shadow-md duration-300">
            <p className="text-gray-500 text-2xl">Total Spent</p>
            <p className="text-3xl">${stats.totalSpent.toFixed(2)}</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between hover:scale-102 shadow-md duration-300">
            <p className="text-gray-500 text-2xl">Avg Rating</p>
            <div className="flex items-center">
              <p className="text-3xl">{stats.avgRating}</p>
              <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-8 h-8"/>
            </div>
          </div>
        </div>

        <div className="flex flex-col gap-4 mt-4">
          {orders.map((order, index) => (
            <OrderHistoryItem 
              key={`${order.id}-${index}-${order.stars}`}
              item={order.item}
              cost={order.cost.toFixed(2)}
              id={order.id}
              time={order.time}
              tags={order.tags}
              initialStars={order.stars}
              onReorder={handleReorder}
              onStarUpdate={(newStars) => handleStarUpdate(order.id, order.item, newStars)}
            />
          ))}
        </div>
          
          {/* FOR DEBUGGING */}
        {/* <details className="mt-8 p-4 bg-gray-100 rounded-lg">
          <summary className="font-bold cursor-pointer">Debug Info (Click to view)</summary>
          <div className="mt-2">
            <h3 className="font-semibold">Current Orders:</h3>
            <pre className="text-sm bg-gray-800 text-white p-2 rounded mt-1 overflow-auto">
              {JSON.stringify(orders, null, 2)}
            </pre>
            
            <h3 className="font-semibold mt-4">Reorders:</h3>
            <pre className="text-sm bg-gray-800 text-white p-2 rounded mt-1 overflow-auto">
              {JSON.stringify(reorders, null, 2)}
            </pre>
            
            <h3 className="font-semibold mt-4">Stats:</h3>
            <pre className="text-sm bg-gray-800 text-white p-2 rounded mt-1 overflow-auto">
              {JSON.stringify(stats, null, 2)}
            </pre>
          </div>
        </details> */}


      </main>
    </div>
  );
}
