"use client";
export default function PrivacyText() {
  return (
    <>
        <h1 className="text-black mb-4">Privacy Policy</h1>
        <p>1. Information We Collect:<br/>We collect information that you provide directly to us, including:</p>
            <ul className="list-disc list-inside ml-4">
                <li>Account information (name, email address, phone number)</li>
                <li>Delivery addresses and location data</li>
                <li>Order history and preferences</li>
                <li>Payment information (processed securely through third-party providers)</li>
                <li>Communications with customer support</li>
            </ul>
        <p><br/>2. How We Use Your Information<br/>We use the information we collect to:</p>
            <ul className="list-disc list-inside ml-4">
                <li>Process and deliver your orders via our autonomous robots</li>
                <li>Provide real-time delivery tracking and notifications</li>
                <li>Improve our AI routing algorithms and service quality</li>
                <li>Communicate with you about orders, updates, and promotions</li>
                <li>Ensure platform security and prevent fraud</li>
                <li>Comply with legal obligations</li>
            </ul>
        <p><br/>3. Contact Us<br/>For privacy-related questions or requests, contact us at:</p>
            <ul className="list-disc list-inside ml-4">
                <li>Email: johndoe@gmail.com</li>
                <li>Phone: +1 (XXX) XXX-XXXX</li>
            </ul>
    </>
  );
}
