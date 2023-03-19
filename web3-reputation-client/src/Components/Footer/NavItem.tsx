import React from "react";
import { FooterNavs } from "./Navigation";
import { Link } from "react-router-dom";

function NavItem({ nav }: { nav: FooterNavs }) {
  return (
    <div className="flex flex-col gap-[16px] ">
      <h3 className="font-bold text-[1.2em] tracking-[0.05em]">{nav.title}</h3>
      <ul className="flex flex-col gap-[10px] ">
        {nav.items.map((item, index) => (
          <Link to="#" key={index.toString()}>
            <li className="text-white/80">{item}</li>
          </Link>
        ))}
      </ul>
    </div>
  );
}

export default NavItem;
