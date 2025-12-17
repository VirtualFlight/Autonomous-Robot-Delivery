"use client";

import { useState } from "react";
import Link from "next/link"; // Import Link

export default function LoginPage() {
  const [isSignup, setIsSignup] = useState(false);

  return (
    <div className="min-h-screen flex bg-gradient-to-br from-white to-purple-50">
      {/* LEFT SIDE - Made slightly smaller */}
      <div className="w-5/5 px-16 py-12 flex flex-col justify-between">
        <div className="flex items-center gap-3">
          <div className="w-25 h-25 rounded-xl bg-gradient-to-r from-purple-600 to-purple-800 flex items-center justify-center">
            <span className="text-white font-bold text-sm"></span>
          </div>
          <div>
            <p className="font-medium text-5xl tracking-tight">NavidEats</p>
            <p className="text-xl text-gray-500 tracking-wide">
              Autonomous Delivery, Anywhere
            </p>
          </div>
        </div>

        <div className="mt-8">
          <p className="text-7xl leading-tight tracking-tight">
            Food & groceries delivered by robots
          </p>
          <p className="mt-4 text-gray-600 leading-tight tracking-tight text-3xl max-w-md">
            Experience the future of delivery today. Fast, eco-friendly and
            always on time.
          </p>

          {/* Stats section */}
          <div className="mt-12 flex gap-4">
            <FeatureCard 
              title="15min" 
              subtitle="Avg Delivery" 
              color="text-blue-600" 
              icon="🚚"
            />
            <FeatureCard 
              title="100%" 
              subtitle="Eco-Friendly" 
              color="text-green-600" 
              icon="🌿"
            />
            <FeatureCard 
              title="24/7" 
              subtitle="Availability" 
              color="text-purple-600" 
              icon="⏰"
            />
          </div>
        </div>

        <p className="text-sm text-gray-400">
          Copyright © NavidEats
        </p>
      </div>

      {/* RIGHT SIDE - Made larger (3/5 of screen) */}
      <div className="w-4/5 flex items-center justify-center bg-gradient-to-b from-purple-50 to-white">
        <div className="w-full max-w-4xl bg-white rounded-3xl shadow-2xl p-10 border border-gray-100">
          {/* Tabs - Larger */}
          <div className="flex bg-gray-100 rounded-xl p-1 mb-10">
            <button
              onClick={() => setIsSignup(false)}
              className={`flex-1 py-4 rounded-lg font-medium text-base transition-all ${
                !isSignup 
                  ? "bg-white shadow-sm text-purple-700" 
                  : "text-gray-500 hover:text-gray-700"
              }`}
            >
              Login
            </button>
            <button
              onClick={() => setIsSignup(true)}
              className={`flex-1 py-4 rounded-lg font-medium text-base transition-all ${
                isSignup 
                  ? "bg-white shadow-sm text-purple-700" 
                  : "text-gray-500 hover:text-gray-700"
              }`}
            >
              Sign Up
            </button>
          </div>

          {/* CONDITIONAL FORM */}
          {isSignup ? <SignupForm /> : <LoginForm />}
        </div>
      </div>
    </div>
  );
}

/* ---------------- FORMS ---------------- */

function LoginForm() {
  return (
    <>
      <Input label="Email Address" placeholder="you@example.com" />
      <Input label="Password" placeholder="••••••••" type="password" />

      <div className="flex justify-between items-center text-base mb-10">
        <label className="flex items-center gap-3 text-gray-600">
          <input 
            type="checkbox" 
            className="rounded border-gray-300 text-purple-600 focus:ring-purple-500 w-5 h-5" 
          />
          Remember me
        </label>
        <a href="#" className="text-purple-600 hover:text-purple-800 font-medium">
          Forgot password?
        </a>
      </div>
      
      <Link href="/">
      <PrimaryButton text="Sign in →" />
      </Link>

      <Terms />
    </>
  );
}

function SignupForm() {
  return (
    <>
      <Input label="Full Name" placeholder="John Doe" />
      <Input label="Email Address" placeholder="you@example.com" />
      <Input label="Password" placeholder="••••••••" type="password" />

      <Link href="/">
      <PrimaryButton text="Sign in →" />
      </Link>
      
      <div className="mt-8 bg-gradient-to-r from-purple-50 to-indigo-50 border border-purple-100 text-purple-800 text-base p-5 rounded-xl">
        <div className="flex items-start gap-4">
          <span className="text-2xl">🎉</span>
          <div>
            <strong className="text-lg">Welcome Offer!</strong>
            <p className="mt-2 text-purple-700">
              Get $10 off your first robot delivery
            </p>
          </div>
        </div>
      </div>

      <Terms />
    </>
  );
}

/* ---------------- UI PIECES ---------------- */

function Input({ label, placeholder, type = "text" }) {
  return (
    <div className="mb-6">
      <label className="text-base font-medium text-gray-700 mb-2 block">{label}</label>
      <input
        type={type}
        placeholder={placeholder}
        className="w-full border border-gray-300 rounded-xl px-5 py-4 text-base outline-none focus:ring-3 focus:ring-purple-500 focus:border-transparent transition-all"
      />
    </div>
  );
}

function PrimaryButton({ text }) {
  return (
    <button className="w-full bg-gradient-to-r from-purple-600 to-purple-800 text-white py-4 rounded-xl font-semibold text-base hover:opacity-90 transition-all shadow-md hover:shadow-lg">
      {text}
    </button>
  );
}

function Terms() {
  return (
    <p className="text-sm text-gray-500 text-center mt-8 leading-relaxed">
      By continuing, you agree to our <a href="#" className="text-purple-600 hover:underline font-medium">Terms of Service</a> and <a href="#" className="text-purple-600 hover:underline font-medium">Privacy Policy</a>
    </p>
  );
}

function FeatureCard({ title, subtitle, color, icon }) {
  return (
    <div className="flex-1 bg-white rounded-xl p-4 shadow-sm border border-gray-100">
      <div className="flex items-center gap-2 mb-2">
        <span className="text-lg">{icon}</span>
        <p className={`font-bold text-2xl ${color}`}>{title}</p>
      </div>
      <p className="text-sm text-gray-600 font-medium">{subtitle}</p>
    </div>
  );
}