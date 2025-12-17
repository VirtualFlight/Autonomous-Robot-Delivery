"use client";
import Image from "next/image";
import Link from "next/link";
import { useState } from "react";
import NavBar from "../Components/NavBar"

export default function OrderHistoryItem({item, id, cost, time, tags, stars}) {
  
  return (
    <div>
        <div className=" w-full h-full">
          <div className="flex flex-col border-2 border-gray-200 bg-white w-full h-48 rounded-xl pt-4 pl-4 pr-4 pb-2">
            <div className="flex"> {/* TOP ITEM INFO */}
              <div className="flex w-full gap-4">
                <Image src="/orders_green.svg" width={70} height={70} alt="order icon" className=" bg-[#34C759]/40 rounded-xl p-3 w-16 h-16"/>
                <div className="flex flex-col w-full justify-between">
                  <p className="text-xl">{item}</p>
                  <p className="text-sm text-gray-500">Order #{id}</p>
                  <p className="text-xs text-gray-500">{time} ago</p>
                </div>
                <p className="text-xl">${cost}</p>
              </div>
            </div>
            <div className="flex mt-2 gap-2 flex-wrap"> {/* TAGS */}
              {tags.map((tag, index) => (
                <div key={index} className="flex rounded-md px-2 bg-gray-200 border border-gray-400">
                  <p className="text-sm text-gray-700">{tag}</p>
                </div>
              ))}
            </div>

            <div className="flex h-full border-t border-gray-200 mt-4 p-2 justify-between"> {/* RATING & REORDER */}
              <div className="flex items-center">
                <p className="text-sm">Your Rating: </p>
                <div className="flex ml-2">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <Image
                      key={star}
                      src="/star.svg"
                      width={20}
                      height={20}
                      alt="star"
                      className={`w-5 h-5 ml-1 ${
                        star <= stars ? "" : "grayscale"
                      }`}
                    />
                  ))}
                </div>
              </div>
              <Link href="../ActiveOrders" className="flex gap-2 bg-[#2D35C9]/5 justify-center items-center p-2 w-36 h-full rounded-xl hover:bg-[#2D35C9]/15 duration-200 ">
                <Image src="redo.svg" height={50} width={50} alt="star" className=" w-6 h-6"/>
                <p className="text-xl text-[#2D35C9]/90">Reorder</p>
              </Link>
            </div>
          </div>
        </div>
    </div>
  );
}
