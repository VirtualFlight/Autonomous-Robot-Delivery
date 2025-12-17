import Image from "next/image";
import Link from "next/link";
import NavBar from "./Components/NavBar"
import RestaurantFeatureItem from "./Components/RestaurantFeatureItem";

export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      {/* nav bar */}
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 w-full">
        
        {/* Top half */}
        <div className="flex flex-col gap-2"> 

          <h1> What's for dinner? </h1>
          {/* location and delivery thing */}
          <div className="flex gap-2">
            <Image
              src="/location.svg"
              alt="location icon"
              width={24}
              height={24}
            />
            <h3 className="opacity-60"> Delivering to: 1 University Blvd, L6G OH2 </h3>
          </div>
          
          {/* Search for restaurants */}
          <div className="flex border border-foreground/20  p-4 gap-2 rounded-xl">
            <Image
              src="/search.svg"
              alt="search icon"
              width={28}
              height={28}
            />
            <h2 className="opacity-60">Search for restaurants or cuisines...</h2>
          </div>
        </div>

        {/* Bottom Half */}
        <h1 className="pt-7 pb-4">Featured</h1>
        <div className="flex justify-between flex-wrap gap-y-3">
          <RestaurantFeatureItem/>
          <RestaurantFeatureItem/>
          <RestaurantFeatureItem/>
          <RestaurantFeatureItem/>
        </div>
        



      </main>
    </div>
  );
}
