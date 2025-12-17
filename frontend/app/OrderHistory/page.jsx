"use client";
import Image from "next/image";
import Link from "next/link";
import NavBar from "../Components/NavBar"
import OrderHistoryItem from "../Components/OrderHistoryItem"

export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <h1>Order History</h1>
        <h2 className="text-gray-500">View your past deliveries and reorder favourites</h2>
        <div className="flex h-1/5 w-full gap-8">
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between">
            <p className="text-gray-500 text-2xl">Total Orders</p>
            <p className="text-3xl">4</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between">
            <p className="text-gray-500 text-2xl">Total Spent</p>
            <p className="text-3xl">$82.62</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-between">
            <p className="text-gray-500 text-2xl">Avg Rating</p>
            <div className="flex items-center">
              <p className="text-3xl">2.8</p>
              <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-8 h-8"/>
            </div>
          </div>
        </div>

        <div className="flex flex-col gap-4 mt-4">
          <OrderHistoryItem item="Green Bowl" cost="44.61" id="ORD-1240" time="2 days" tags={["Ceaser Salad", "Greek Salad"]} stars={2}/>
          <OrderHistoryItem item="Pickle Burger" cost="12.67" id="ORD-8913" time="1 week" tags={["Pickles", "Hamburger", "Halal"]} stars={5}/>
          <OrderHistoryItem item="Pickle Burger" cost="12.67" id="ORD-8913" time="1 week" tags={["Pickles", "Hamburger", "Halal"]} stars={3}/>
          <OrderHistoryItem item="Pickle Burger" cost="12.67" id="ORD-8913" time="1 week" tags={["Pickles", "Hamburger", "Halal"]} stars={1}/>
        </div>

      </main>
    </div>
  );
}
