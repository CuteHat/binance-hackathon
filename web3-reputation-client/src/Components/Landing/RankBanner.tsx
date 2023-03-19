import React from "react";
import rankBanner from "../../assets/rankBanner.png";

function RankBanner() {
  return (
    <div className="px-[15px] md:px-[50px] py-[150px] 2xl:my-[200px]">
      <img src={rankBanner} alt="Rising up the Ranks! TOP NFT" />
    </div>
  );
}

export default RankBanner;
