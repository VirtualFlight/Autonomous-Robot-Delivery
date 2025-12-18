"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import Image from "next/image";
import axios from "axios";
import NavBar from "../Components/NavBar";

export default function PersonalInformation() {
  return (
    <div className="min-h-screen bg-gray-50">
      <NavBar />

      <div className="ml-[25%] w-[75%] py-8 px-8">
        {/* Back to Profile Link */}
        <div className="mb-6">
          <a
            href="Profile"
            className="text-blue-600 hover:text-blue-800 flex items-center"
          >
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
          </a>
        </div>

        {/* Main Content Container */}
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Left Column - Personal Information Form */}
          <div className="lg:w-2/3">
            <div className="bg-white shadow rounded-lg p-6">
              <h2 className="text-2xl font-bold text-gray-800 mb-6">
                Help Center
              </h2>
              <p className="text-gray-600 mb-8">Find answers and get support</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}