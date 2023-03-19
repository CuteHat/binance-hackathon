import React from "react";
import Start from "./Start";

function MainBannerInfo() {
  const [info] = React.useState<string[]>([
    "Create your own NFTs and sell them on the marketplace.",
    "Buy NFTs from other creators and add them to your collection.",
    "Join the NFT revolution and see the benefits of your creativity in the rankings!",
  ]);

  return (
    <div className="mt-[100px] flex flex-col gap-8 px-[15px] lg:pr-[15px] lg:pr-[50px]">
      <div className="font-bold text-[45px]">
        Join the NFT revolution and see the benefits of your creativity in the
        rankings!
      </div>
      <ul className="flex flex-col gap-6 lg:pl-[100px]">
        {info.map((item, index) => {
          return (
            <li
              key={index.toString()}
              className="flex items-center gap-[23px] text-[1.2em]"
            >
              <Start />
              <span>{item}</span>
            </li>
          );
        })}
        <li>
          <button
            type="button"
            className="rounded-[41px] p-4  min-w-[242px] bg-rose mt-[40px] hover:bg-rose-light transition "
          >
            Join Us
          </button>
        </li>
      </ul>
    </div>
  );
}

export default MainBannerInfo;
