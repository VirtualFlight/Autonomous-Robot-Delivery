"use client";
import Image from "next/image";
import NavBar from "../Components/NavBar"
import ActiveOrderItem from "../Components/ActiveOrderItem"


export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans pt-0 p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <h1>Active Orders</h1>
        <h2 className="text-black opacity-60">Track your robot deliveries in real-time</h2>

        <ActiveOrderItem restaurantName={"The Burger Place"} orderId={"1240"} cost={24.97} botName={"Bot-03"} eta={"8 mins"} status={"done"} trackerLink={"https://www.google.com/maps/place/The+Burger+Palace/@34.0257435,-118.320002,16z/data=!3m1!4b1!4m6!3m5!1s0x80c2b81470d4fdfb:0xdae0c1b9d713cd98!8m2!3d34.0257435!4d-118.3174217!16s%2Fg%2F11c71mdw8_?entry=ttu&g_ep=EgoyMDI1MTIwOS4wIKXMDSoASAFQAw%3D%3D"}/>
        <ActiveOrderItem restaurantName={"The Burger Place"} orderId={"1240"} cost={24.97} botName={"Bot-03"} eta={"8 mins"} status={"otw"} trackerLink={"https://www.google.com/maps"}/>
        <ActiveOrderItem restaurantName={"The Burger Place"} orderId={"1240"} cost={24.97} botName={"Bot-03"} eta={"8 mins"} status={"prep"} trackerLink={"https://www.google.com/maps"}/>
      </main>
    </div>
  );
}
