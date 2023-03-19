import React from "react";

type Props = {
  handleOpen: () => void;
};

function HamburgerMenu({ handleOpen }: Props) {
  return (
    <div className="p-4 space-y-2 md:hidden" onClick={handleOpen}>
      <span className="block w-8 h-0.5 bg-white"></span>
      <span className="block w-8 h-0.5 bg-white"></span>
      <span className="block w-8 h-0.5 bg-white"></span>
    </div>
  );
}

export default HamburgerMenu;
