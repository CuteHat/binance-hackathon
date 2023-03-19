import React, { useCallback, useEffect } from "react";
import HamburgerMenu from "./HamburgerMenu";
import Logo from "./Logo";
import Navigation from "./Navigation";
import isMobile from "is-mobile";
import { AnimatePresence } from "framer-motion";

function Header() {
  const [show, setShow] = React.useState(false);
  const handleOpen = useCallback(() => setShow((state) => !state), []);

  useEffect(() => {
    window.addEventListener(
      "resize",
      () => {
        if (!show) return;
        if (!isMobile()) setShow(false);
      },
      false
    );
  }, []);

  return (
    <>
      <div className="py-[10px] bg-black flex justify-between items-center md:py-[15px] px-[15px] md:px-[50px] text-white relative select-none">
        <Logo />
        <HamburgerMenu handleOpen={handleOpen} />
        <Navigation className="hidden md:flex" />
      </div>
      {show && (
        <AnimatePresence>
          <Navigation
            className="md:hidden fixed bg-black/50 w-full h-full z-10"
            handleOpen={handleOpen}
          />
        </AnimatePresence>
      )}
    </>
  );
}

export default Header;
