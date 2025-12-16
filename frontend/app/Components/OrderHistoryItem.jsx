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
                <Image  width={70} alt="order icon" className="border-2 border-green-500"/>
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
                <Image  alt="star" className="border-2 border-red-500 ml-2"/>
                <Image  alt="star" className="border-2 border-red-500 ml-2"/>
                <Image  alt="star" className="border-2 border-red-500 ml-2"/>
                <Image  alt="star" className="border-2 border-red-500 ml-2"/>
                <Image  alt="star" className="border-2 border-red-500 ml-2"/>
              </div>
              <div className="flex gap-2 bg-blue-50 justify-center items-center p-2 w-36 h-full rounded-xl">
                <Image  alt="star" className="border-2 border-blue-200 ml-2"/>
                <p className="text-xl text-blue-500">Reorder</p>
              </div>
            </div>
          </div>
        </div>
    </div>
  );
}
