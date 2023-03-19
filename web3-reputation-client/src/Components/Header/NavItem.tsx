import React from "react";
import { Link, useLocation } from "react-router-dom";

function NavItem({
  nav,
  handleOpen,
}: {
  nav: { id: number; name: string; path: string };
  handleOpen?: () => void;
}) {
  const location = useLocation();
  return (
    <Link to={nav.path}>
      <li
        className="group/home flex gap-2 items-center text-[18px] tracking-[0.05em]"
        onClick={handleOpen}
      >
        <div
          className={`w-[18px] hidden md:block h-[18px] border rounded-full group-hover/home:bg-white transition duration-150 ease-out group-hover/home:ease-in ${
            location.pathname === nav.path ? "bg-white" : ""
          }`}
        />
        <span>{nav.name}</span>
      </li>
    </Link>
  );
}

export default NavItem;
