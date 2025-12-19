"use client";
import Image from "next/image";
import Link from "next/link";

export default function NavBar() {
  return (
    <div className="fixed left-0 top-0 h-screen w-1/4 bg-zinc-50 font-sans">
      <nav className="flex flex-col h-full w-full gap-8 p-8 bg-white rounded-2xl shadow-lg border border-gray-100">
        <div className="flex gap-2">
          <Image src="/logo.svg" alt="NavidEats logo" width={80} height={80} />
          <div>
            <p className="text-5xl font-bold text-gray-800">NavidEats</p>
            <p className="text-gray-500 text-lg mt-1">Customer Portal</p>
          </div>
        </div>

        <div className="flex-1 flex flex-col gap-3">
          <Link
            href="/"
            className="flex items-center gap-3 px-4 py-3.5 rounded-lg hover:bg-gray-100 hover:text-gray-700 duration-200 transition-colors text-gray-700"
          >
            <Image
              src="/home.svg"
              width={50}
              height={50}
              className="text-xl w-8 h-8"
            />
            <span className="font-medium text-lg">Restaurants</span>
          </Link>

          <Link
            href="/ActiveOrders"
            className="flex items-center gap-3 px-4 py-3.5 rounded-lg hover:bg-gray-100 duration-200 transition-colors text-gray-700"
          >
            <Image
              src="/orders.svg"
              width={50}
              height={50}
              className="text-xl w-8 h-8"
            />
            <span className="font-medium text-lg">Active Orders</span>
            {/* <span className="ml-auto bg-red-500 text-white text-xs px-2.5 py-1.5 rounded-full">67</span> */}
          </Link>

          <Link
            href="/OrderHistory"
            className="flex items-center gap-3 px-4 py-3.5 rounded-lg hover:bg-gray-100 duration-200 transition-colors text-gray-700"
          >
            <Image
              src="/clock.svg"
              width={50}
              height={50}
              className="text-xl w-8 h-8"
            />
            <span className="font-medium text-lg">Order History</span>
          </Link>

          <Link
            href="/Profile"
            className="flex items-center gap-3 px-4 py-3.5 rounded-lg hover:bg-gray-100 duration-200 transition-colors text-gray-700"
          >
            <Image
              src="/profile_icon.svg"
              width={50}
              height={50}
              className="text-xl w-8 h-8"
            />
            <span className="font-medium text-lg">Profile</span>
          </Link>
        </div>

        <div className="pt-8 border-t border-gray-200">
          <Link
            href="/login"
            className="flex items-center gap-3 px-4 py-3.5 rounded-lg hover:bg-gray-100 duration-200 transition-colors text-gray-700"
          >
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 bg-red-300 rounded-xl flex items-center justify-center">
                <Image
                  src="/logout.svg"
                  width={50}
                  height={50}
                  className="text-red-600 font-semibold text-lg p-3"
                  alt="logout icon"
                />
              </div>
              <div>
                <p className="font-medium text-red-600">Log Out</p>
                <p className="text-sm text-red-600">Sign out of your account</p>
              </div>
            </div>
          </Link>
        </div>
      </nav>
    </div>
  );
}
