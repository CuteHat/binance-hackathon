import {ethers} from "hardhat";

async function main() {
    const [owner] = await ethers.getSigners();
    const NFT = await ethers.getContractFactory("BetelgeuseRT");
    const nft = await NFT.deploy("BetelgeuseRT", "BRT", "test.com");

    const BetelgeuseController = await ethers.getContractFactory("BetelgeuseController");
    const controller = await BetelgeuseController.deploy(nft.address);
    await nft.setMinterRole(controller.address);
    await nft.setAdminRole(controller.address);


    console.log(`deployed to ${controller.address}, ${nft.address}`);
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
