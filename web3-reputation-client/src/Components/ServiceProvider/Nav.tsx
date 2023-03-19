import React from "react";
import { Link, useLocation } from "react-router-dom";

function Nav({
  children = null,
  addHandler,
}: {
  children?: React.ReactNode;
  addHandler?: (x?: boolean) => void;
}) {
  const location = useLocation();
  return (
    <div className="flex justify-between">
      <ul className="flex gap-[60px] items-center text-[20px]">
        <Link to={"/my-nft"}>
          <li
            className={
              location.pathname === "/my-nft" ? "text-white" : "text-white/50"
            }
            onClick={() => addHandler?.(false)}
          >
            NFTs List
          </li>
        </Link>
        <Link to={"/service-provider"}>
          <li
            onClick={() => addHandler?.(false)}
            className={
              location.pathname === "/service-provider"
                ? "text-white"
                : "text-white/50"
            }
          >
            Service Provider
          </li>
        </Link>
      </ul>
      {children}
    </div>
  );
}

export default Nav;
