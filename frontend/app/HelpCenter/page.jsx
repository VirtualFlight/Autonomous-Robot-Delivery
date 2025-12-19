"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import Image from "next/image";
import axios from "axios";
import NavBar from "../Components/NavBar";

export default function PersonalInformation() {
  const [openDropdown, setOpenDropdown] = useState(null);
  const [heights, setHeights] = useState({});

  const faqItems = [
    { 
      id: "faq1", 
      title: "How do I reset my password?", 
      content: "To reset your password, go to the login page and click 'Forgot Password'. Enter your email address and follow the instructions sent to your email." 
    },
    { 
      id: "faq2", 
      title: "How can I track my order?", 
      content: "You can track your order by going to 'Active Orders' in your dashboard. Click on the order you want to track to see real-time updates." 
    },
    { 
      id: "faq3", 
      title: "What payment methods do you accept?", 
      content: "We accept all major credit cards (Visa, MasterCard, American Express), PayPal, and Apple Pay for iOS users." 
    },
    { 
      id: "faq4", 
      title: "How do I update my delivery address?", 
      content: "Go to 'Profile' > 'Address Book' to add, edit, or remove delivery addresses. You can set a default address for faster checkout." 
    },
    { 
      id: "faq5", 
      title: "How can I contact customer support?", 
      content: "You can contact our 24/7 support team by clicking the 'Contact Support' button at the bottom of this page, or by emailing support@navideats.com." 
    },
  ];

  const toggleDropdown = (id) => {  
    if (openDropdown === id) {
      setOpenDropdown(null);
    } else {
      setOpenDropdown(id);
    }
  };

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
          {/* Left Column - Help Center Content */}
          <div className="lg:w-2/3">
            <div className="bg-white shadow rounded-lg p-6">
              <h2 className="text-2xl font-bold text-gray-800 mb-2">
                Help Center
              </h2>
              <p className="text-gray-600 mb-8">Find answers and get support</p>

              {/* Frequently Asked Questions Section */}
              <div className="mb-8">
                <h3 className="text-lg font-semibold text-gray-800 mb-4">
                  Frequently Asked Questions
                </h3>
                
                <div className="space-y-3">
                  {faqItems.map((item) => (
                    <div key={item.id} className="border border-gray-200 rounded-lg overflow-hidden">
                      <button
                        onClick={() => toggleDropdown(item.id)}
                        className="w-full flex justify-between items-center p-4 bg-gray-50 hover:bg-gray-100 transition-all duration-300 text-left"
                      >
                        <span className="font-medium text-gray-800">
                          {item.title}
                        </span>
                        <svg
                          className={`w-5 h-5 text-gray-600 transition-all duration-400 ${openDropdown === item.id ? 'rotate-180' : ''}`}
                          fill="none"
                          stroke="currentColor"
                          viewBox="0 0 24 24"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M19 9l-7 7-7-7"
                          />
                        </svg>
                      </button>
                      
                      {/* Animated Dropdown Content */}
                      <div 
                        className={`overflow-hidden transition-all duration-300 ease-in-out ${
                          openDropdown === item.id 
                            ? 'max-h-96 opacity-100' 
                            : 'max-h-0 opacity-0'
                        }`}
                      >
                        <div className="p-4 bg-white border-t border-gray-200">
                          <p className="text-gray-600 animate-fadeIn">
                            {item.content}
                          </p>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              {/* Divider */}
              <div className="border-t border-gray-200 my-6"></div>

              {/* Contact Support Section */}
              <div className="text-center bg-linear-to-r from-[#8908EC] to-[#4E0586] rounded-xl p-6 animate-slideUp">
                <h3 className="text-lg font-semibold text-white mb-2">
                  Still need help?
                </h3>
                <p className="text-white mb-6">
                  Our support team is available 24/7 to assist you
                </p>
                <button className="px-6 py-3 bg-white text-[#8908EC] font-medium rounded-lg hover:bg-gray-100 transition-all duration-200 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-white">
                  Contact Support
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Add custom animations to global CSS or use a style tag */}
      <style jsx global>{`
        @keyframes fadeIn {
          from {
            opacity: 0;
            transform: translateY(-10px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }
        
        @keyframes slideUp {
          from {
            opacity: 0;
            transform: translateY(20px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }
        
        .animate-fadeIn {
          animation: fadeIn 0.5s ease-out;
        }
        
        .animate-slideUp {
          animation: slideUp 0.6s ease-out;
        }
      `}</style>
    </div>
  );
}