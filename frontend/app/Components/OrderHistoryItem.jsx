"use client";
import Image from "next/image";
import Link from "next/link";
import { useState } from "react";
import NavBar from "../Components/NavBar"

export default function OrderHistoryItem() {
  return (
    <div className="">
        <div className=" w-full h-full">
          <div className="flex flex-col border-2 border-gray-200 bg-white w-full h-48 rounded-xl pt-4 pl-4 pr-4 pb-2">
            <div className="flex"> {/* TOP ITEM INFO */}
              <div className="flex w-full gap-4">
                <Image src="/orders_green.svg" width={70} height={70} alt="order icon" className=" bg-[#34C759]/40 rounded-xl p-3 w-16 h-16"/>
                <div className="flex flex-col w-full justify-between">
                  <p className="text-xl">Green Bowl</p>
                  <p className="text-sm text-gray-500">Order #ORD-1240</p>
                  <p className="text-xs text-gray-500">2 days ago</p>
                </div>
                <p className="text-xl">$44.61</p>
              </div>
            </div>
            <div className="flex mt-2 gap-2"> {/* TAGS */}
              <div className="flex rounded-md px-2 bg-gray-200 border border-gray-400">
                <p className="text-sm text-gray-700">Ceaser Salad</p>
              </div>
              <div className="flex rounded-md px-2 bg-gray-200 border border-gray-400">
                <p className="text-sm text-gray-700">Greek Salad</p>
              </div>
            </div>

            <div className="flex h-full border-t border-gray-200 mt-4 p-2 justify-between"> {/* RATING & REORDER */}
              <div className="flex items-center">
                <p className="text-sm">Your Rating: </p>
                <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-5 h-5"/>
                <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-5 h-5"/>
                <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-5 h-5"/>
                <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-5 h-5"/>
                <Image src="/star.svg" width={50} height={50} alt="star" className="ml-2 w-5 h-5"/>
              </div>
              <div className="flex gap-2 bg-[#2D35C9]/5 justify-center items-center p-2 w-36 h-full rounded-xl">
                <Image src="redo.svg" height={50} width={50} alt="star" className=" w-6 h-6"/>
                <p className="text-xl text-[#2D35C9]/90">Reorder</p>
              </div>
            </div>
          </div>
        </div>
    </div>
  );
}
