import React from "react";
import { Web3Button } from "@web3modal/react";
import { motion } from "framer-motion";
import NavItem from "./NavItem";
import { useAccount } from "wagmi";
import Dropdown from "./Dropdown";

const variants = {
  open: {
    opacity: 1,
  },
  closed: {
    opacity: 0,
  },
  exit: {
    opacity: 0,
  },
};

function Navigation({
  className = "",
  handleOpen,
}: {
  className?: string;
  handleOpen?: () => void;
}) {
  const account = useAccount();

  const [navs] = React.useState<
    Array<{ id: number; name: string; path: string }>
  >([
    {
      id: 2,
      name: "Product",
      path: "/product",
    },
    {
      id: 3,
      name: "Partnership",
      path: "/partnership",
    },
  ]);

  return (
    <motion.div
      variants={variants}
      initial="close"
      animate="open"
      exit="exit"
      className={`flex flex-col md:flex-row gap-6 items-center py-4 md:py-0 ${className}`}
    >
      <ul className="w-full text-white flex flex-col md:flex-row items-center gap-4">
        {navs.map((nav) => (
          <NavItem key={nav.id.toString()} nav={nav} handleOpen={handleOpen} />
        ))}
      </ul>
      <div className="w-full flex justify-center connectBtn">
        {account.isConnected ? <Dropdown /> : <Web3Button icon={"hide"} />}
      </div>
    </motion.div>
  );
}

export default Navigation;
