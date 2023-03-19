import React from "react";
import Nav from "./Nav";
import ListOfProviders from "./ListOfProviders";
import AddServiceBtn from "./AddServiceBtn";

function ServiceProvider() {
  return (
    <div className="w-full px-[15px] md:px-[50px] my-[121px]">
      <Nav>
        <AddServiceBtn />
      </Nav>
      <div className="border border-gray w-full mt-[60px] rounded-[22px] p-[33px] flex flex-col gap-8">
        <ListOfProviders />
      </div>
    </div>
  );
}

export default ServiceProvider;
