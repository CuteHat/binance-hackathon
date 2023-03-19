import React from "react";
import MainBanner from "./MainBanner";
import MainBannerInfo from "./MainBannerInfo";
import RankBanner from "./RankBanner";

function Intro() {
  return (
    <div className="min-h-screen">
      <div className="relative flex">
        <MainBanner />
        <MainBannerInfo />
      </div>
      <RankBanner />
    </div>
  );
}

export default Intro;
