import Image from "next/image";
export default function RestaurantFeatureItem() {
  return (
    <div className="flex border-2 border-black/20 w-[40rem] h-80 rounded-2xl overflow-hidden">
        <div className="w-2/5">
            {/* <Image src="/boursin_burgers.jpg" alt="food image" width={0} height = {0} className="object-cover"></Image> */}
            <img className="object-cover  h-full w-full" src="/boursin_burgers.jpg" alt="food image"></img>
        </div>

        <div className="flex flex-col p-5 w-3/5">
            <div className="flex flex-col justify-between h-full">

                {/* CONTAINER: Heading + RDD Container */}
                <div>
                    {/* CONTAINER: Heading */}
                    <div>
                        <h2 className="text-2xl">The Burger Palace</h2>
                        <h3 className="text-xl opacity-70"> Burgers • American </h3>
                    </div>

                    {/* Rating, Duration, Distance Container */}
                    <div className="flex justify-between pt-5">
                                    
                        {/* Rating */}
                        <div className="flex">

                            <Image
                                src="/star.svg"
                                alt="star icon"
                                width={19}
                                height={19}
                            />

                            <p className="text-xl opacity-70">4.9</p>
                        </div>

                        {/* Duration */}
                        <div className="flex">

                            <Image
                                src="/clock.svg"
                                alt="clock icon"
                                width={19}
                                height={19}
                            />

                        <p className="text-xl opacity-70">12-15 min</p>
                        </div>

                        {/* Distance */}
                        <div className="flex">

                            <Image
                                src="/location.svg"
                                alt="location icon"
                                width={19}
                                height={19}
                            />

                        <p className="text-xl opacity-70">1.3km</p>
                        </div>
                    </div>
                </div>
                
                <div className="flex justify-between items-center">
                    <p className="text-xl opacity-70">$$</p>
                    <div className="p-1 px-2 rounded-3xl bg-[#34C75966]">
                        <p className="text-base text-[#1B9F3C]">
                            Free Robot Delivery
                        </p>
                    </div>
                </div>
            </div>


        </div>
        
    </div>

  );
}

