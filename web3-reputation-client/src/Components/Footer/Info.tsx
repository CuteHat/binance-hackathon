import React from "react";
import discord from "../../assets/discord.png";

function Info() {
  return (
    <div className="flex flex-col items-start gap-[20px]">
      <h1 className="font-black text-[24px] md:text-[20px] tracking-[0.05em] uppercase">
        Betelgeuse
      </h1>
      <div className="px-4 py-2 border rounded-[200px] flex gap-2 items-center">
        <img
          src={discord}
          alt="Join use on discord"
          className="object-contain"
        />
        <span>Join us on discord</span>
      </div>
      <p className="max-w-[490px] leading-[30px]">
        Collect unique NFTs and benefit from our innovative XP system, designed
        to help you grow your rank and unlock new features. Each star you
        collect brings you closer to the top of the ranking ladder, where you
        can claim exclusive rewards and recognition. Join our community on the
        Binance Smart Chain and experience the benefits of being a part of the
        NFT revolution.
      </p>
      <span>Copyright © 2023 BETELGEUSE.</span>
    </div>
  );
}

export default Info;
