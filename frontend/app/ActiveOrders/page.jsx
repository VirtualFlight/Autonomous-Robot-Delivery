"use client";
import Image from "next/image";
import NavBar from "../Components/NavBar"


export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full border">
        <h1>Active Orders</h1>
        <h2 className="text-black opacity-60">Track your robot deliveries in real-time</h2>

        <div className="p-5 w-full border border-purple-500 h-96">
            
            {/* CONTAINER: Robot Status + Timer */}
            <div className="flex justify-between">
                {/* Robot Status */}
                <div className="flex">
                    <Image src="/location.svg" alt="temp robot icon" width={24} height={24}></Image>
                    <p>Robot on the way</p>
                </div>

                {/* Timer */}
                <div className="flex gap-1">
                    <Image src="/clock.svg" alt="clock icon" width={18} height={18}></Image>
                    <p>8 min</p>
                </div>
            </div>

            {/* CONTAINER: Restaurant Name + Total */}
            <div className="flex justify-between">
                <h3>The Burger Palace</h3>
                <h3>$24.97</h3>
            </div>

            <p className="text-base opacity-60">Order #ORD-1240</p>
            
            

        </div>

      </main>
    </div>
  );
}
