import Image from "next/image";
export default function Home() {
  return (
    <div>



        <div className="flex">
            <Image src="/clock.svg" alt="clocl icon" width={19} height = {19}></Image>

            <div className="flex flex-col ">
                <div>
                    <h2 className="text-2xl">The Burger Place</h2>
                    <h3 className="text-xl"> Burgers • American </h3>
                </div>
                <div className="flex gap-3 pt-5">
                                  
                    {/* Rating */}
                    <div className="flex">

                    <Image
                        src="/star.svg"
                        alt="star icon"
                        width={19}
                        height={19}
                    />

                    <h3 className="text-xl opacity-60">4.9</h3>
                    </div>

                    {/* Duration */}
                    <div className="flex">

                    <Image
                        src="/clock.svg"
                        alt="clock icon"
                        width={19}
                        height={19}
                    />

                    <h3 className="text-xl opacity-60">12-15 min</h3>
                    </div>

                    {/* Distance */}
                    <div className="flex">

                    <Image
                        src="/location.svg"
                        alt="location icon"
                        width={19}
                        height={19}
                    />

                    <h3 className="text-xl opacity-60">1.3km</h3>
                    </div>
                </div>
                
                <div className="flex justify-between border">
                    <div>$$</div>
                    <div>
                        Free Robot Delivery
                    </div>
                </div>


            </div>
            
        </div>
    </div>

  );
}

