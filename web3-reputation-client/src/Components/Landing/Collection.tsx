import React from "react";
import card from "../../assets/card.png";
import BN from "./BN";

function Collection() {
  return (
    <div className="relative min-h-[150vh] mb-[200px] 2xl:mb-0">
      <BN className="absolute rotate-180 right-0 top-[-45%] 2xl:top-[-60%] hidden lg:block" />
      <div className="flex flex-wrap 2xl:flex-nowrap gap-[50px] md:px-[50px] mb-[50px]">
        <div className="px-[15px] text-[40px] max-w-[646px]">
          Start your collection now and enjoy the benefits of rising up the
          ranks!
        </div>
        <div className="flex px-[15px] lg:px-0 flex-col lg:flex-row gap-[30px] w-full lg:justify-end">
          {[1, 2, 3].map((item) => (
            <img src={card} key={item} className="block" />
          ))}
        </div>
      </div>
      <div className="flex flex-col lg:flex-row gap-[30px] px-[15px] lg:px-[50px] justify-end 2xl:justify-start 2xl:ml-[300px]">
        {[1, 2, 3].map((item) => (
          <img src={card} key={item} className="block" />
        ))}
      </div>
      <BN className="absolute top-[150px] hidden lg:block" />
    </div>
  );
}

export default Collection;
