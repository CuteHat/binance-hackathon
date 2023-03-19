import React from "react";
import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import { AnimatePresence, motion } from "framer-motion";
import { Link, useLocation } from "react-router-dom";
import { useWeb3Modal } from "@web3modal/react";
import OutsideClickHandler from "react-outside-click-handler";

function Dropdown() {
  const [open, setOpen] = React.useState(false);
  const location = useLocation();
  const modal = useWeb3Modal();
  const toggle = () => setOpen((state) => !state);
  return (
    <>
      <div
        className="flex items-center gap-2 bg-pso rounded-[41px] px-4 py-3 cursor-pointer"
        onClick={toggle}
      >
        <span className="tracking-[0.5px]">My Profile</span>
        {open ? (
          <ChevronUpIcon className="w-[20px] h-[20px]" />
        ) : (
          <ChevronDownIcon className="w-[20px] h-[20px]" />
        )}
      </div>
      {open && (
        <AnimatePresence>
          <OutsideClickHandler onOutsideClick={toggle}>
            <motion.ul className="absolute bg-pso p-4 mt-2 rounded-[8px] flex flex-col  z-10 top-[65px] right-[50px] min-w-[256px]">
              <Link
                to="/my-nft"
                className={`hover:bg-zGray p-3 rounded-[8px] transition-all tracking-[0.5px] ${
                  location.pathname === "/my-nft" ? "bg-zGray" : ""
                }`}
                onClick={toggle}
              >
                <li
                  className={`before:w-[2px] before:content-[''] before:mr-3  flex
                 ${
                   location.pathname === "/my-nft"
                     ? "before:bg-white"
                     : " before:bg-white/50"
                 }
              `}
                >
                  My NFTs List
                </li>
              </Link>
              <Link
                to="/service-provider"
                className={`hover:bg-zGray p-3 rounded-[8px] transition-all tracking-[0.5px] ${
                  location.pathname === "/service-provider" ? "bg-zGray" : ""
                }`}
                onClick={toggle}
              >
                <li
                  className={`before:w-[2px] before:content-[''] before:mr-3  flex
                 ${
                   location.pathname === "/service-provider"
                     ? "before:bg-white"
                     : " before:bg-white/50"
                 }
              `}
                >
                  Service Provider
                </li>
              </Link>
              <Link
                to="/buy-nft"
                className={`hover:bg-zGray p-3 rounded-[8px] transition-all tracking-[0.5px] ${
                  location.pathname === "/buy-nft" ? "bg-zGray" : ""
                }`}
                onClick={toggle}
              >
                <li
                  className={`before:w-[2px] before:content-[''] before:mr-3  flex
                 ${
                   location.pathname === "/"
                     ? "before:bg-white"
                     : "before:bg-white/50"
                 }
              `}
                >
                  Buy NFTs
                </li>
              </Link>
              <li
                className="hover:bg-zGray p-3 rounded-[8px] transition-all tracking-[0.5px] before:w-[2px] before:content-[''] before:mr-3  flex before:bg-white/50"
                onClick={() => {
                  modal.open();
                  toggle();
                }}
              >
                Logout
              </li>
            </motion.ul>
          </OutsideClickHandler>
        </AnimatePresence>
      )}
    </>
  );
}

export default Dropdown;
