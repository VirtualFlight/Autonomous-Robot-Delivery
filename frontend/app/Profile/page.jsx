"use client";
import Image from "next/image";
import NavBar from "../Components/NavBar"
import Link from "next/link";

export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <h1>Profile</h1>
        <h2 className="text-gray-500">Manage your account and preferences</h2>
        
        <div className="w-full bg-linear-to-r from-[#8908EC] to-[#4E0586] rounded-xl p-8 flex">
            <Image src="/vercel.svg" width={100} height={100} className="rounded-full bg-purple-500 mr-8 h-32 w-32" alt="Profile Picture"/>
            <div className="flex flex-col w-full">
              <p className="text-white text-4xl font-bold">John Doe</p>
              <p className="text-white text-lg">john.doe@example.com</p>
              <div className="bg-purple-600 w-1/6 py-0.5 rounded-xl flex justify-center">
                <p className="text-sm text-white">Member since Dec 2024</p>
              </div>
            </div>
        </div>

        <div className="flex h-1/5 w-full gap-12">
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-around items-center ">
            <p className="text-4xl font-semibold">6</p>
            <p className="text-gray-500 text-xl">Orders</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-around items-center">
            <p className="text-4xl font-semibold">$82.62</p>
            <p className="text-gray-500 text-xl">Spent</p>
          </div>
          <div className="flex flex-col rounded-xl border-2 border-gray-200 bg-white h-36 w-1/3 p-6 justify-around items-center">
            <p className="text-4xl font-semibold">2.8</p>
            <p className="text-gray-500 text-xl">Avg Rating</p>
          </div>
        </div>

        <div className="bg-white w-full border-2 border-gray-200 rounded-xl pt-2 px-4">
          <div className="w-full pb-2">
            <p className="text-2xl font-semibold">Account Settings</p>
          </div>

          <div className="w-full border-t border-gray-200 py-2 flex">
            <Image src="/profile_icon.svg" width={50} height={50} className="rounded-xl bg-gray-200 mr-4 h-12 w-12 p-2" alt="Personal Info Icon"/>
            <div>
              <Link href="#" className="text-md font-semibold hover:underline">Personal Information</Link>
              <p className="text-sm text-gray-500">Update your name, email, and phone</p>
            </div>
          </div>
          
          <div className="w-full border-t border-gray-200 py-2 flex">
            <Image src="/location.svg" width={50} height={50} className="rounded-xl h-12 w-12 bg-gray-200 mr-4 p-2" alt="delivery icon"/>
            <div>
              <Link href="#" className="text-md font-semibold hover:underline">Delivery Addresses</Link>
              <p className="text-sm text-gray-500">Manage your saved addresses</p>
            </div>
          </div>

          <div className="w-full border-t border-gray-200 py-2 flex">
            <Image src="/payment.svg" width={50} height={50} className="rounded-xl bg-gray-200 mr-4 h-12 w-12 p-2" alt="payment icon"/>
            <div>
              <Link href="#" className="text-md font-semibold hover:underline">Payment methods</Link>
              <p className="text-sm text-gray-500">Add or remove payment options</p>
            </div>
          </div>
        </div>
        
        <div className="bg-white w-full border-2 border-gray-200 rounded-xl pt-2 px-4">
          <div className="w-full pb-2">
            <p className="text-2xl font-semibold">Support & Legal</p>
          </div>
          
          <div className="w-full border-t border-gray-200 py-2 flex">
            <Image src="/help.svg" width={50} height={50} className="rounded-xl bg-gray-200 mr-4 h-12 w-12 p-2" alt="Profile Picture"/>
            <div>
              <Link href="#" className="text-md font-semibold hover:underline">Help Center</Link>
              <p className="text-sm text-gray-500">Get help and FAQs</p>
            </div>
          </div>

          <div className="w-full border-t border-gray-200 py-2 flex">
            <Image src="/privacy.svg" width={50} height={50} className="rounded-xl bg-gray-200 mr-4 h-12 w-12 p-2" alt="Profile Picture"/>
            <div>
              <Link href="#" className="text-md font-semibold hover:underline">Privacy & Terms</Link>
              <p className="text-sm text-gray-500">Review our policies</p>
            </div>
          </div>
        </div>



      </main>
    </div>
  );
}
