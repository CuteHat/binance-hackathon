import React from "react";
import Nav from "../ServiceProvider/Nav";
import NftList from "./NftList";

function MyNftList() {
  return (
    <div className="w-full px-[15px] md:px-[50px] my-[121px]">
      <Nav />
      <div className="border border-gray w-full mt-[60px] rounded-[22px] p-[33px] flex flex-col gap-8">
        <NftList />
      </div>
    </div>
  );
}

export default MyNftList;
