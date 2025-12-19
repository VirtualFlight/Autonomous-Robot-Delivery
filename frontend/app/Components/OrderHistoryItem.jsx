"use client";
import Image from "next/image";
import Link from "next/link";
import { useState, useEffect } from "react";

export default function OrderHistoryItem({item, id, cost, time, tags, initialStars, onReorder, onStarUpdate}) {
  const [stars, setStars] = useState(initialStars);
  
  const handleStarClick = (clickedStar) => {
    const newStars = clickedStar === stars ? 0 : clickedStar;
    setStars(clickedStar === stars ? 0 : clickedStar); //calculate star value
    if (onStarUpdate) {onStarUpdate(newStars);};
  };

  useEffect(() => { //update star val
    setStars(initialStars);
  }, [initialStars]);

  const handleReorderClick = () => { //when reorder button is clicked
    const orderData = {item, id, cost: Number(cost), time, tags: [...tags], stars, originalOrder: {item, id, cost, time, tags}};
    
    if (onReorder) {onReorder(orderData);} // call parent
  };
  

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
                <div className="flex ml-2 items-center">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <button
                      key={star}
                      type="button"
                      onClick={() => handleStarClick(star)}
                      className="p-1 hover:scale-120 transition-transform duration-200"
                      aria-label={`Rate ${star} star${star !== 1 ? 's' : ''}`}
                    >
                      <Image
                        src="/star.svg"
                        width={20}
                        height={20}
                        alt={`Star ${star}`}
                        className={`w-5 h-5 ${
                          star <= stars ? "" : "grayscale opacity-80"
                        }`}
                      />
                    </button>
                  ))}
                  {/* Optional: Show the current rating value */}
                  <span className="ml-2 text-sm text-gray-600">
                    ({stars > 0 ? `${stars}/5` : "Not rated"})
                  </span>
                </div>
              </div>
              <button 
                onClick={handleReorderClick}
                className="flex gap-2 bg-[#2D35C9]/5 justify-center items-center p-2 w-36 h-full rounded-xl hover:bg-[#2D35C9]/15 duration-200"
              >
                <Image src="redo.svg" height={50} width={50} alt="reorder icon" className="w-6 h-6"/>
                <p className="text-xl text-[#2D35C9]/90">Reorder</p>
              </button>
            </div>
          </div>
        </div>
    </div>
  );
}
