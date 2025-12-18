"use client";
import Image from "next/image";
import Link from "next/link";

export default function ActiveOrderItem({restaurantName, orderId, cost, botName, eta, status, trackerLink}) {
  return (
    <div className="border-t-8 border-[#8908EC] rounded-2xl flex flex-col h-72">
    <div className="p-5 w-full border-2 h-72 rounded-lg border-[#8908EC] flex flex-col justify-between">     
        {/* CONTAINER: Robot Status + Timer */}
        <div className="flex justify-between">
            {/* Robot Status */}
            {status === "otw" ? (
                <div className="flex rounded-xl bg-purple-100 px-3 py-1 gap-2 border border-purple-500">
                    <Image src="/location.svg" alt="robot icon" width={24} height={24} className="opacity-50"/>
                    <p className="text-[#8908EC] font-medium">Robot on the way</p>
                </div>
            ) : status === "prep" ? (
                <div className="flex rounded-xl bg-[#FF8D28]/20 border border-amber-500 px-3 py-1 gap-2">
                    <Image src="/location.svg" alt="robot icon" width={24} height={24} className="opacity-50"/>
                    <p className="text-[#b56f1f] font-medium">Restaurant is preparing</p>
                </div>
            ) : status === "done" ? (
                <div className="flex rounded-xl bg-[#34C759]/20 px-3 py-1 gap-2 border border-green-500">
                    <Image src="/location.svg" alt="robot icon" width={24} height={24} className="opacity-50"/>
                    <p className="text-[#2c901f] font-medium">Delivered!</p>
                </div>
            ) : (
                <div className="flex rounded-xl bg-[#FF0000]/20 px-3 py-1 gap-2 border border-red-500">
                    <Image src="/location.svg" alt="robot icon" width={24} height={24} className="filter hue-rotate-90 brightness-95 contrast-90"/>
                    <p className="text-[#ad0202] font-medium">Error</p>
                </div>
            )}

            {/* Timer */}
            <div className="flex gap-1 items-center">
                <Image src="/clock.svg" alt="clock icon" width={18} height={18}></Image>
                <p>{eta}</p>
            </div>
        </div>

        {/* CONTAINER: Restaurant Name + Total */}
        <div className="flex justify-between mt-4">
            <h3>{restaurantName}</h3>
            <h3>${cost}</h3>
        </div>

        <p className="text-base opacity-60 font-semibold">Order #ORD-{orderId}</p>
        <div className="w-full flex h-full flex-col justify-end px-4">
            <div className="w-full flex border-t border-black/20 h-2/3 items-center gap-4">
                <Image src="/logo.svg" width={100} height={100} className="w-18 h-18" />
                <div className="flex flex-col w-1/4">
                    <p className="text-xl font-semibold">{botName}</p>
                    <p className="text-md text-black/40 font-semibold">Autonomous Delivery</p>
                </div>
                <div className="w-full h-full flex justify-end items-center ">
                    <Link href={trackerLink} target="_blank" className="w-1/5 h-1/2 flex justify-center items-center rounded-xl gap-3 hover:bg-[#6b19ab]/20 duration-300">
                        <Image src="/search.svg" width={50} height={50} className="w-6 h-6"/>
                        <p className="text-[#2D35C9] font-semibold">Track Robot</p>
                    </Link>
                </div>
            </div>
        </div>
    </div>
    </div>
  );
}
