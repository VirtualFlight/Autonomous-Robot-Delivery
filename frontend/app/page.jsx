import Image from "next/image";
import Link from "next/link";
import NavBar from "./Components/NavBar"

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

        {/* Bottom half */}
        <div className="block">

          <h1> Featured </h1>

          {/* Multiple restaurant container */}

          <div className="">


            {/* Individual restaurant stuff */}
            <div className="flex border-2 border-amber-500 w-[5rem] h-[10rem]">
              {/* Image container, controls image size */}
              <div className="overflow-hidden">
                <Image
                  src="/search.svg"
                  alt="temp burger"
                  width={200}
                  height={200}
                  // className="w-full h-full object-cover"
                />
              </div>

              {/* Restaurant Description */}
              <div className="flex flex-col p-3">
                <h2> The Burger Place</h2>
                <h3 className="opacity-60"> Burgers • American</h3>

                {/* Rating, min, km */}
                <div className="flex gap-3">
                  
                  {/* Rating */}
                  <div className="flex">

                    <Image
                      src="/star.svg"
                      alt="star icon"
                      width={19}
                      height={19}
                    />

                    <h3 className="opacity-60">4.9</h3>
                  </div>

                  {/* Duration */}
                  <div className="flex">

                    <Image
                      src="/clock.svg"
                      alt="clock icon"
                      width={19}
                      height={19}
                    />

                    <h3 className="opacity-60">12-15 min</h3>
                  </div>

                  {/* Distance */}
                  <div className="flex">

                    <Image
                      src="/location.svg"
                      alt="location icon"
                      width={19}
                      height={19}
                    />

                    <h3 className="opacity-60">1.3km</h3>
                  </div>


                </div>
              </div>


            </div>

            


          </div>



        </div>




      </main>

      
      {/* <main className="flex min-h-screen w-full max-w-3xl flex-col items-center justify-between py-32 px-16 bg-white dark:bg-black sm:items-start">
        <Image
          className="dark:invert"
          src="/next.svg"
          alt="Next.js logo"
          width={100}
          height={20}
          priority
        />
        <div className="flex flex-col items-center gap-6 text-center sm:items-start sm:text-left">
          <h1 className="max-w-xs text-3xl font-semibold leading-10 tracking-tight text-black dark:text-zinc-50">
            To get started, edit the page.tsx file.
          </h1>
          <p className="max-w-md text-lg leading-8 text-zinc-600 dark:text-zinc-400">
            Looking for a starting point or more instructions? Head over to{" "}
            <a
              href="https://vercel.com/templates?framework=next.js&utm_source=create-next-app&utm_medium=appdir-template-tw&utm_campaign=create-next-app"
              className="font-medium text-zinc-950 dark:text-zinc-50"
            >
              Templates
            </a>{" "}
            or the{" "}
            <a
              href="https://nextjs.org/learn?utm_source=create-next-app&utm_medium=appdir-template-tw&utm_campaign=create-next-app"
              className="font-medium text-zinc-950 dark:text-zinc-50"
            >
              Learning
            </a>{" "}
            center.
          </p>
        </div>
        <div className="flex flex-col gap-4 text-base font-medium sm:flex-row">
          <a
            className="flex h-12 w-full items-center justify-center gap-2 rounded-full bg-foreground px-5 text-background transition-colors hover:bg-[#383838] dark:hover:bg-[#ccc] md:w-[158px]"
            href="https://vercel.com/new?utm_source=create-next-app&utm_medium=appdir-template-tw&utm_campaign=create-next-app"
            target="_blank"
            rel="noopener noreferrer"
          >
            <Image
              className="dark:invert"
              src="/vercel.svg"
              alt="Vercel logomark"
              width={16}
              height={16}
            />
            Deploy Now
          </a>
          <a
            className="flex h-12 w-full items-center justify-center rounded-full border border-solid border-black/[.08] px-5 transition-colors hover:border-transparent hover:bg-black/[.04] dark:border-white/[.145] dark:hover:bg-[#1a1a1a] md:w-[158px]"
            href="https://nextjs.org/docs?utm_source=create-next-app&utm_medium=appdir-template-tw&utm_campaign=create-next-app"
            target="_blank"
            rel="noopener noreferrer"
          >
            Documentation
          </a>
        </div>
      </main> */}
    </div>
  );
}
