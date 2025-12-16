import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen bg-zinc-50 font-sans flex flex-col">
      {/* Navbar */}
      <nav className="flex justify-between items-center p-6 bg-white shadow-md">
        <div className="flex items-center gap-4">
          <Image
            src=""
            alt="Logo"
            width={50}
            height={50}
            className="rounded-full"
          />
          <div>
            <h1 className="text-2xl font-kanit font-normal text-black">NavidEats</h1>
            <p className="text-gray-600 text-sm">Autonomous Delivery, Anywhere</p>
          </div>
        </div>
      </nav>

  {/* Hero Section */}
  <section className="flex-1 flex flex-col md:flex-row items-center justify-between p-6 md:p-12 bg-gradient-to-b from-white to-purple-200">
        <div className="md:w-1/2 space-y-6">
          <h2 className="text-4xl md:text-6xl font-kanit font-normal leading-tight">
            Food & groceries delivered by robots
          </h2>
          <p className="text-lg md:text-2xl text-gray-700">
            Experience the future of delivery today. Fast, eco-friendly and always on time.
          </p>

          <div className="flex gap-16">
            <div className="flex flex-col items-center bg-white rounded-3xl w-64 h-32 justify-center shadow">
              <span className="text-indigo-700 text-3xl">15min</span>
              <span className="text-gray-600">Avg Delivery</span>
            </div>
            <div className="flex flex-col items-center bg-white rounded-3xl w-64 h-32 justify-center shadow">
              <span className="text-purple-700 text-3xl">100%</span>
              <span className="text-gray-600">Eco-Friendly</span>
            </div>
            <div className="flex flex-col items-center bg-white rounded-3xl w-64 h-32 justify-center shadow">
              <span className="text-green-600 text-3xl">24/7</span>
              <span className="text-gray-600">Availability</span>
            </div>
          </div>
        </div>

        <div className="md:w-1/2 mt-8 md:mt-0 space-y-6">
          {/* Login form */}
          <div className="bg-white rounded-3xl p-6 shadow-md space-y-4">
            <div>
              <label className="block text-gray-800 font-kanit">Email Address</label>
              <input
                type="email"
                placeholder="you@example.com"
                className="w-full mt-1 p-2 border rounded-md text-black/70"
              />
            </div>
            <div>
              <label className="block text-gray-800 font-kanit">Password</label>
              <input
                type="password"
                placeholder="••••••••"
                className="w-full mt-1 p-2 border rounded-md text-black/70"
              />
            </div>
            <div className="flex justify-between items-center">
              <label className="flex items-center gap-2">
                <input type="checkbox" className="accent-black" />
                Remember me
              </label>
              <button className="text-indigo-700 underline text-sm">Forgot password?</button>
            </div>
            <button className="w-full bg-purple-700 text-white py-2 rounded-2xl text-xl font-kanit">
              Sign in →
            </button>
            <p className="text-center text-gray-700 text-sm">
              By continuing, you agree to our Terms of Service and Privacy Policy
            </p>
          </div>
        </div>
        
      </section>

      {/* Footer */}
      <footer className="text-center text-gray-600 py-6">
        Copyright @ NavidEats
      </footer>
    </div>
  );
}
