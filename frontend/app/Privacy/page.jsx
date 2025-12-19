"use client";
import Image from "next/image";
import Link from "next/link";
import {useState} from "react"
import PrivacyText from "./Privacy/page";
import TermsText from "./Terms/page";
import NavBar from "../Components/NavBar";

export default function Home() {
    const [activeTab, setActiveTab] = useState("privacy"); // "privacy" or "terms"
  return (
    <div className="flex min-h-screen bg-zinc-50 font-sans p-4">
      <NavBar/>

      <main className="ml-[25%] flex flex-col p-8 gap-4 w-full">
        <Link href="Profile" className="text-blue-600 hover:text-blue-800 flex items-center">
            <svg
              className="w-4 h-4 mr-1"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M10 19l-7-7m0 0l7-7m-7 7h18"
              />
            </svg>
            Back to Profile
          </Link>
            <h1>Privacy & Terms</h1>
        <h2 className="text-gray-500">Our commitment to your privacy and security</h2>
       
       <div className="w-full h-12 flex items-end border-b-2 border-black/20">
            {/* Privacy Tab */}
            <button 
              onClick={() => setActiveTab("privacy")}
              className={`w-1/6 h-full justify-center flex items-center cursor-pointer transition-all ${
                activeTab === "privacy" 
                  ? "border-b-2 border-[#2D35C9] text-[#2D35C9]" 
                  : "text-gray-500 hover:text-gray-700"
              }`}
            >
                <Image 
                  src="/privacy.svg" 
                  width={55} 
                  height={55} 
                  className={`w-6 ${activeTab === "privacy" ? "opacity-100" : "opacity-70"}`}
                  alt="Privacy icon"
                />
                <p className="ml-2 font-semibold">Privacy Policy</p>
            </button>
            
            {/* Terms Tab */}
            <button 
              onClick={() => setActiveTab("terms")}
              className={`w-1/6 h-full justify-center flex items-center cursor-pointer transition-all ${
                activeTab === "terms" 
                  ? "border-b-2 border-[#2D35C9] text-[#2D35C9]" 
                  : "text-gray-500 hover:text-gray-700"
              }`}
            >
                <Image 
                  src="/terms.svg" 
                  width={55} 
                  height={55} 
                  className={`w-4 ${activeTab === "terms" ? "opacity-100" : "opacity-70"}`}
                  alt="Terms icon"
                />
                <p className="ml-2 font-semibold">Terms of Service</p>
            </button>
       </div>
       <div className="w-full border-2 border-black/20 rounded-4xl h-full p-8 flex flex-col font-semibold text-gray-600">
            {activeTab === "privacy" ? <PrivacyText /> : <TermsText />}
       </div>

      </main>
    </div>
  );
}