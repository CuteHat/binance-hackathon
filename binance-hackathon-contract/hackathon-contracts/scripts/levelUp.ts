import {ethers} from "hardhat";

async function main() {

  const BetelgeuseController = await ethers.getContractFactory("BetelgeuseController");
  const BetelgeuseRT = await ethers.getContractFactory("BetelgeuseRT");


  let controller = await BetelgeuseController.attach("0x480eE059E2409758eC3Afd3bb53199EF8D704636");
  let nft = await BetelgeuseRT.attach("0xa794f988C26b6ed381A9A615638FaA2797FCdc5e");

  const [owner] = await ethers.getSigners();

  await controller.connect(owner).levelUp(2, 2, [6, 7, 8, 9, 10], "https://cloudflare-ipfs.com/ipfs/QmX8kofUDGTkVhobqop9TeFuQYm1Kn1zA2eCuAFjS9ahUi");

}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
