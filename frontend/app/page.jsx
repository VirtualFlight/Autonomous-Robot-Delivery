"use client";

import { useState, useRef } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import Image from "next/image";

const API_BASE_URL = "http://localhost:8080/api/customers";

export default function LoginPage() {
    const [isSignup, setIsSignup] = useState(false);
    const [direction, setDirection] = useState("right");
    const containerRef = useRef(null);

    return (
        <div className="min-h-screen flex bg-gradient-to-br from-white to-purple-50">
            {/* LEFT SIDE */}
            <div className="w-5/5 px-16 py-12 flex flex-col justify-between">
                <div className="flex items-center gap-3">
                    <div className="flex gap-2 items-center">
                        <Image
                            src="/logo.svg"
                            alt="NavidEats logo"
                            width={80}
                            height={80}
                        />
                        <div>
                            <p className="text-5xl font-bold text-gray-800">NavidEats</p>
                            <p className="text-gray-500 text-lg mt-1">
                                Autonomous Delivery, Anywhere
                            </p>
                        </div>
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

                <p className="text-sm text-gray-400">Copyright © NavidEats</p>
            </div>

            {/* RIGHT SIDE */}
            <div className="w-4/5 flex items-center justify-center bg-gradient-to-b from-purple-50 to-white">
                <div className="w-full max-w-4xl bg-white rounded-3xl shadow-2xl p-10 border border-gray-100 hover:shadow-5xl duration-300">
                    {/* Tabs */}
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

                    {/* FORM CONTAINER WITH ANIMATION */}
                    <div 
                        ref={containerRef}
                        className="relative overflow-hidden min-h-[500px]"
                    >
                        {/* Login Form with Animation */}
                        <div 
                            className={`absolute inset-0 transition-all duration-500 ease-in-out ${
                                !isSignup 
                                    ? "translate-x-0 opacity-100" 
                                    : direction === "right" 
                                        ? "-translate-x-full opacity-0" 
                                        : "translate-x-full opacity-0"
                            }`}
                        >
                            <LoginForm />
                        </div>
                        
                        {/* Signup Form with Animation */}
                        <div 
                            className={`absolute inset-0 transition-all duration-500 ease-in-out ${
                                isSignup 
                                    ? "translate-x-0 opacity-100" 
                                    : direction === "right" 
                                        ? "translate-x-full opacity-0" 
                                        : "-translate-x-full opacity-0"
                            }`}
                        >
                            <SignupForm />
                        </div>
                    </div>
                    {/* CONDITIONAL FORM
                    {isSignup ? <SignupForm /> : <LoginForm />} */}
                </div>
            </div>
        </div>
    );
}

/* ---------------- FORMS ---------------- */

function LoginForm() {
    const router = useRouter();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [rememberMe, setRememberMe] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [showForgotPassword, setShowForgotPassword] = useState(false);

    const isEmailValid = email.includes("@") && email.length > 0;
    const isPasswordValid = password.length >= 8;
    const isFormValid = isEmailValid && isPasswordValid;

    async function handleLogin(e) {
        e.preventDefault();
        if (!isFormValid) return;

        setLoading(true);
        setError("");

        try {
            const response = await fetch(`${API_BASE_URL}/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) {
                throw new Error("Invalid email or password");
            }

            const customer = await response.json();

            // Store customer data
            const storage = rememberMe ? localStorage : sessionStorage;
            storage.setItem("customer", JSON.stringify(customer));
            storage.setItem("customerId", customer.id.toString());

            router.push("/Restaurant");
        } catch (err) {
            console.error("Login error:", err);
            setError(err.message || "An error occurred. Please try again.");
        } finally {
            setLoading(false);
        }
    }

    if (showForgotPassword) {
        return <ForgotPasswordForm onBack={() => setShowForgotPassword(false)} />;
    }

    return (
        <>
            {error && (
                <div className="mb-6 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
                    {error}
                </div>
            )}

            <Input
                label="Email Address"
                placeholder="you@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                error={!isEmailValid && email}
            />
            <Input
                label="Password"
                placeholder="••••••••"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                error={!isPasswordValid && password}
            />

            <div className="flex justify-between items-center text-base mb-10">
                <label className="flex items-center gap-3 text-gray-600">
                    <input
                        type="checkbox"
                        checked={rememberMe}
                        onChange={(e) => setRememberMe(e.target.checked)}
                        className="rounded border-gray-300 text-purple-600 focus:ring-purple-500 w-5 h-5"
                    />
                    Remember me
                </label>
                <button
                    onClick={() => setShowForgotPassword(true)}
                    className="text-purple-600 hover:text-purple-800 font-medium"
                >
                    Forgot password?
                </button>
            </div>

            <PrimaryButton
                text={loading ? "Signing in..." : "Sign in →"}
                onClick={handleLogin}
                disabled={!isFormValid || loading}
            />

            <Terms />
        </>
    );
}

function SignupForm() {
    const router = useRouter();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");
    const [city, setCity] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const isNameValid = firstName.trim().length >= 2;
    const isEmailValid = email.includes("@") && email.length > 0;
    const isPasswordValid =
        password.length >= 8 && /[A-Z]/.test(password) && /[a-z]/.test(password);

    const isFormValid = isNameValid && isEmailValid && isPasswordValid;

    async function handleSubmit(e) {
        e.preventDefault();
        if (!isFormValid) return;

        setLoading(true);
        setError("");

        try {
            const response = await fetch(`${API_BASE_URL}/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email,
                    password,
                    firstName,
                    lastName,
                    phoneNumber: phoneNumber || null,
                    address: address || null,
                    city: city || null,
                    postalCode: postalCode || null,
                }),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Registration failed");
            }

            const customer = await response.json();

            localStorage.setItem("customer", JSON.stringify(customer));
            localStorage.setItem("customerId", customer.id.toString());

            router.push("/Restaurant");
        } catch (err) {
            console.error("Registration error:", err);
            if (err.message.includes("already exists")) {
                setError("Email already exists. Please use a different email or login.");
            } else {
                setError("An error occurred during registration. Please try again.");
            }
        } finally {
            setLoading(false);
        }
    }

    return (
        <>
            {error && (
                <div className="mb-6 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
                    {error}
                </div>
            )}

            <div className="grid grid-cols-2 gap-4">
                <Input
                    label="First Name"
                    placeholder="John"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    error={!isNameValid && firstName}
                />
                <Input
                    label="Last Name"
                    placeholder="Doe"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />
            </div>

            <Input
                label="Email Address"
                placeholder="you@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                error={!isEmailValid && email}
            />

            <Input
                label="Password"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                error={!isPasswordValid && password}
                helper="Include an uppercase and lowercase letter. Minimum 8 characters."
            />

            <Input
                label="Phone Number (Optional)"
                placeholder="+1 (555) 123-4567"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
            />

            <Input
                label="Address (Optional)"
                placeholder="123 Main Street"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />

            <div className="grid grid-cols-2 gap-4">
                <Input
                    label="City (Optional)"
                    placeholder="Toronto"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                />
                <Input
                    label="Postal Code (Optional)"
                    placeholder="M5H 2N2"
                    value={postalCode}
                    onChange={(e) => setPostalCode(e.target.value)}
                />
            </div>

            <PrimaryButton
                text={loading ? "Creating account..." : "Sign up →"}
                disabled={!isFormValid || loading}
                onClick={handleSubmit}
            />

            <div className="mt-4 bg-purple-100 text-purple-700 text-sm p-3 rounded-lg">
                🎉 <strong>Welcome Offer!</strong>
                <br />
                Get $10 off your first robot delivery
            </div>

            <Terms />
        </>
    );
}

function ForgotPasswordForm({ onBack }) {
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState("");

    const isEmailValid = email.includes("@") && email.length > 0;

    async function handleForgotPassword(e) {
        e.preventDefault();
        if (!isEmailValid) return;

        setLoading(true);
        setError("");

        try {
            const response = await fetch(`${API_BASE_URL}/forgot-password`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email }),
            });

            if (!response.ok) {
                throw new Error("Failed to send reset email");
            }

            setSuccess(true);
        } catch (err) {
            console.error("Forgot password error:", err);
            setError("An error occurred. Please try again or contact support.");
        } finally {
            setLoading(false);
        }
    }

    if (success) {
        return (
            <div className="text-center">
                <div className="mb-6 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg">
                    Password reset instructions have been sent to your email.
                </div>
                <button
                    onClick={onBack}
                    className="text-purple-600 hover:text-purple-800 font-medium"
                >
                    ← Back to login
                </button>
            </div>
        );
    }

    return (
        <>
            <div className="mb-6">
                <button
                    onClick={onBack}
                    className="text-purple-600 hover:text-purple-800 font-medium flex items-center gap-2"
                >
                    ← Back to login
                </button>
            </div>

            <h2 className="text-2xl font-bold text-gray-800 mb-2">Reset Password</h2>
            <p className="text-gray-600 mb-6">
                Enter your email address and we'll send you instructions to reset your
                password.
            </p>

            {error && (
                <div className="mb-6 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
                    {error}
                </div>
            )}

            <Input
                label="Email Address"
                placeholder="you@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                error={!isEmailValid && email}
            />

            <PrimaryButton
                text={loading ? "Sending..." : "Send Reset Link"}
                onClick={handleForgotPassword}
                disabled={!isEmailValid || loading}
            />
        </>
    );
}

/* ---------------- UI PIECES ---------------- */

function Input({
                   label,
                   placeholder,
                   type = "text",
                   value,
                   onChange,
                   error,
                   helper,
               }) {
    return (
        <div className="mb-6">
            <label className="text-base font-medium text-gray-700 mb-2 block">
                {label}
            </label>

            <input
                type={type}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`w-full rounded-xl px-5 py-4 text-base outline-none transition-all
          ${
                    error
                        ? "border-red-400 focus:ring-red-400"
                        : "border-gray-300 focus:ring-purple-500"
                }
          border focus:ring-2`}
            />

            {helper && <p className="text-sm text-gray-500 mt-1">{helper}</p>}
        </div>
    );
}

function PrimaryButton({ text, onClick, disabled }) {
    return (
        <button
            onClick={onClick}
            disabled={disabled}
            className={`w-full py-4 rounded-xl font-semibold text-base transition-all shadow-md
        ${
                disabled
                    ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                    : "bg-gradient-to-r from-purple-600 to-purple-800 text-white hover:opacity-90 hover:shadow-lg"
            }`}
        >
            {text}
        </button>
    );
}

function Terms() {
    return (
        <p className="text-sm text-gray-500 text-center mt-8 leading-relaxed">
            By continuing, you agree to our{" "}
            <Link href="/out_privacy" className="text-purple-600 hover:underline font-medium">
                Terms of Service
            </Link>{" "}
            and{" "}
            <Link href="/out_privacy" className="text-purple-600 hover:underline font-medium">
                Privacy Policy
            </Link>
        </p>
    );
}

function FeatureCard({ title, subtitle, color, icon }) {
    return (
        <div className="flex-1 bg-white rounded-xl p-4 shadow-sm border border-gray-100 hover:scale-102 hover:shadow-lg duration-300">
            <div className="flex items-center gap-2 mb-2">
                <span className="text-lg">{icon}</span>
                <p className={`font-bold text-2xl ${color}`}>{title}</p>
            </div>
            <p className="text-sm text-gray-600 font-medium">{subtitle}</p>
        </div>
    );
}