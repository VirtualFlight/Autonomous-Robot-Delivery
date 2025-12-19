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
              <h2 className="text-2xl font-bold text-gray-800 mb-2">
                Personal Information
              </h2>
              <p className="text-gray-600 mb-8">Update your account details</p>

              {/* Profile Picture Section */}
              <div className="mb-8">
                <h3 className="text-sm font-medium text-gray-700 mb-4">
                  Profile Picture
                </h3>
                <div className="flex items-center">
                  <div className="w-16 h-16 bg-blue-600 rounded-full flex items-center justify-center text-white font-bold text-xl mr-4">
                    JD
                  </div>
                  <div className="flex flex-col">
                    <button className="px-4 py-2 bg-white border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors w-fit">
                      Change Photo
                    </button>
                    <p className="text-xs text-gray-500 mt-2">
                      JPG or PNG | Max Size 2MB
                    </p>
                  </div>
                </div>
              </div>

              {/* Form Fields Grid */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* First Name */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    First Name
                  </label>
                  <input
                    type="text"
                    placeholder="John"
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  />
                </div>

                {/* Last Name */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Last Name
                  </label>
                  <input
                    type="text"
                    placeholder="Doe"
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  />
                </div>
 
                {/* Email Address */}
                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Email Address
                  </label>
                  <input
                    type="email"
                    placeholder="you@example.com"
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  />
                </div>

                {/* Phone Number */}
                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Phone Number
                  </label>
                  <input
                    type="tel"
                    placeholder="+1 (XXX) XXX-XXXX"
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  />
                </div>

                {/* Account Status */}
                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Account Status
                  </label>
                  <div className="flex items-center p-4 bg-green-50 rounded-md border border-green-200">
                    <div className="flex-shrink-0">
                      <svg
                        className="h-5 w-5 text-green-400"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          fillRule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clipRule="evenodd"
                        />
                      </svg>
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-green-800">
                        Email Verified
                      </p>
                      <p className="text-sm text-green-700">
                        Your email address is verified
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              {/* Form Actions */}
              <div className="flex justify-end space-x-4 mt-8 pt-6 border-t border-gray-200">
                <button className="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors">
                  Cancel
                </button>
                <button className="px-6 py-2 bg-blue-600 border border-transparent rounded-md text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors">
                  Save Changes
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
