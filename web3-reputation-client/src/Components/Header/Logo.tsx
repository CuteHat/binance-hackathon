import React from "react";
import logo from "../../assets/logo.png";
import { Link } from "react-router-dom";

function Logo() {
  return (
    <Link to="/" className="flex items-center gap-2">
      <img src={logo} alt="Betelgeuse" />
      <h1 className="font-black text-[24px] md:text-[20px] tracking-[0.05em] uppercase">
        Betelgeuse
      </h1>
    </Link>
  );
}

export default Logo;
