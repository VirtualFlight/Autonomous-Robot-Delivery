"use client";
import { useState, useEffect } from "react";
import axios from "axios";
import NavBar from "../Components/NavBar";
import RestaurantFeatureItem, { restaurantPool } from "../Components/RestaurantFeatureItem";

const API_BASE_URL = "http://localhost:8080/api";

export default function RestaurantPage() {
  const [restaurants, setRestaurants] = useState([]);
  const [filteredRestaurants, setFilteredRestaurants] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [customer, setCustomer] = useState(null);
  const [activeOrders, setActiveOrders] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const savedCustomer = localStorage.getItem("customer") || sessionStorage.getItem("customer");
    if (!savedCustomer) {
      window.location.href = "/login";
      return;
    }

    const cust = JSON.parse(savedCustomer);
    setCustomer(cust);
    fetchActiveOrders(cust.id);

    setRestaurants(restaurantPool);
    setFilteredRestaurants(restaurantPool);
    setLoading(false);
  }, []);

  const fetchActiveOrders = async (customerId) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/orders/customer/${customerId}`);
      const orders = response.data;
      const active = orders.filter(o =>
          o.status !== "DELIVERED" && o.status !== "CANCELLED"
      ).length;
      setActiveOrders(active);
    } catch (error) {
      console.error("Failed to fetch orders:", error);
    }
  };

  const handleSearch = (e) => {
    const query = e.target.value.toLowerCase();
    setSearchQuery(query);

    if (query === "") {
      setFilteredRestaurants(restaurants);
    } else {
      const filtered = restaurants.filter(r =>
          r.name.toLowerCase().includes(query) ||
          r.cuisine.toLowerCase().includes(query)
      );
      setFilteredRestaurants(filtered);
    }
  };

  const handleQuickOrder = async (restaurant) => {
    if (!customer) {
      alert("Please log in to place an order");
      window.location.href = "/login";
      return;
    }

    try {
      const orderData = {
        customerId: customer.id,
        deliveryAddress: customer.address || "1 University Blvd, L6G OH2",
        restaurantName: restaurant.name,
        items: restaurant.items.map(itemId => ({
          menuItemId: itemId,
          quantity: 1
        }))
      };

      const response = await axios.post(`${API_BASE_URL}/orders`, orderData);
      const order = response.data;

      setActiveOrders(prev => prev + 1);

      alert(`✅ Order placed from ${restaurant.name}! Order ID: ${order.id}`);

    } catch (error) {
      console.error("Order error:", error);
      alert("Failed to place order. Please try again.");
    }
  };

  if (!customer) {
    return (
        <div className="min-h-screen bg-zinc-50 flex items-center justify-center">
          <p className="text-xl">Redirecting to login...</p>
        </div>
    );
  }

  return (
      <div className="min-h-screen bg-zinc-50">
        <NavBar activeOrders={activeOrders} />

        <main className="ml-[25%] p-8">
          <div className="mb-8">
            <h1 className="text-5xl font-bold mb-4">What's for dinner?</h1>

            <div className="flex items-center gap-2 mb-6">
              <span className="text-2xl">📍</span>
              <p className="text-gray-700 text-lg">
                Delivering to: {customer.address || "1 University Blvd, L6G OH2"}
              </p>
            </div>

            <div className="relative">
            <span className="absolute left-4 top-1/2 transform -translate-y-1/2 text-2xl">
              🔍
            </span>
              <input
                  type="text"
                  placeholder="Search for restaurants or cuisines..."
                  value={searchQuery}
                  onChange={handleSearch}
                  className="w-full pl-14 pr-4 py-4 rounded-2xl border-2 border-gray-300 text-lg focus:outline-none focus:border-purple-700"
              />
            </div>

            {searchQuery && (
                <p className="mt-2 text-gray-600">
                  Found {filteredRestaurants.length} restaurant{filteredRestaurants.length !== 1 ? 's' : ''}
                </p>
            )}
          </div>

          <h2 className="text-3xl font-bold mb-6">Featured Restaurants</h2>

          {loading ? (
              <p>Loading restaurants...</p>
          ) : filteredRestaurants.length === 0 ? (
              <div className="text-center py-12">
                <p className="text-xl text-gray-600">
                  No restaurants found matching "{searchQuery}"
                </p>
                <button
                    onClick={() => {
                      setSearchQuery("");
                      setFilteredRestaurants(restaurants);
                    }}
                    className="mt-4 text-purple-700 underline"
                >
                  Clear search
                </button>
              </div>
          ) : (
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                {filteredRestaurants.map((restaurant, index) => (
                    <RestaurantFeatureItem
                        key={index}
                        restaurant={restaurant}
                        onQuickOrder={handleQuickOrder}
                    />
                ))}
              </div>
          )}
        </main>
      </div>
  );
}