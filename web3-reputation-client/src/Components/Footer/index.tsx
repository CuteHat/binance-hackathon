import React from "react";
import Info from "./Info";
import Navigation from "./Navigation";

function Footer() {
  return (
    <div className="bg-secondary mt-auto w-full px-[15px] md:px-[50px] py-[50px] gap-[50px] flex flex-col md:flex-row justify-between">
      <Info />
      <Navigation />
    </div>
  );
}

export default Footer;
