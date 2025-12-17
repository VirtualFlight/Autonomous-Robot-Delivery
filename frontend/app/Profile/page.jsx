"use client";
import Image from "next/image";
import Link from "next/link";
import NavBar from "../Components/NavBar"

export default function Home() {
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      {/* <NavBar/> */}

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <h1>Profile</h1>
        <h2 className="text-gray-500">Manage your account and preferences</h2>
        
        <div className="w-full bg-[#0000FF] border-2 border-red-500 h-full">
            <p className="text-blue-500">hiiiiiiiii</p>
        </div>

      </main>
    </div>
  );
}
