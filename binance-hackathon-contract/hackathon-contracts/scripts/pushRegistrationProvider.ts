import {ethers} from "hardhat";

async function main() {

  const BetelgeuseController = await ethers.getContractFactory("BetelgeuseController");


  let controller = await BetelgeuseController.attach("0x751fE29a4E4CFE5e548687A88CF0a45bd2C70180");

  const criteria = [
    {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 100},
    {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 200},
    {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 300},
  ];

  const ranks = [
    {id: 0, level: 1, xpFrom: 1, name: "asdaasjdasmdlkasmdsd"},
    {id: 0, level: 2, xpFrom: 4, name: "alsdmlaksmdlaksmdasd"},
    {id: 0, level: 3, xpFrom: 6, name: "asqweoiqjweoiqwjedasd"},
    {id: 0, level: 4, xpFrom: 7, name: "wqrrqwrqwr"},
    {id: 0, level: 5, xpFrom: 400, name: "asdasd"},
  ];

  await controller.registerProvider(
      "Test Provider",
      "Description of Test Provider",
      "http://testprovider.com",
      criteria,
      ranks
  );
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
