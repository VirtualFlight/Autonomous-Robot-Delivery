"use client";
import Image from "next/image";
import PrivacyText from "../Privacy/Privacy/page";
import TermsText from "../Privacy/Terms/page";

export default function out() {
  return (
    <div className="p-8 flex flex-col gap-12">
        <div className="">
            <PrivacyText/>
        </div>
        <div className="">
            <PrivacyText/>
        </div>
    </div>
  );
}
