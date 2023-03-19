import {ethers} from "hardhat";

async function main() {

  const BetelgeuseController = await ethers.getContractFactory("BetelgeuseController");
  const BetelgeuseRT = await ethers.getContractFactory("BetelgeuseRT");


  let controller = await BetelgeuseController.attach("0x751fE29a4E4CFE5e548687A88CF0a45bd2C70180");
  let nft = await BetelgeuseRT.attach("0x78298900B8E4D595B3A718663795d2818D1AD9af");

  const [owner] = await ethers.getSigners();

  await controller.connect(owner).mint(1, "https://cloudflare-ipfs.com/ipfs/QmX8kofUDGTkVhobqop9TeFuQYm1Kn1zA2eCuAFjS9ahUi");

}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
