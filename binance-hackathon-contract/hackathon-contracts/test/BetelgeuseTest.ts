import {fromWei} from "../script-utils/Converter";

const {expect} = require("chai");
const {ethers} = require("hardhat");

describe("BetelgeuseController", function () {

    async function deploy() {

        const [owner, otherAccount] = await ethers.getSigners();
        const NFT = await ethers.getContractFactory("BetelgeuseRT");
        const nft = await NFT.deploy("BetelgeuseRT", "BRT", "test.com");

        const BetelgeuseController = await ethers.getContractFactory("BetelgeuseController");
        const controller = await BetelgeuseController.deploy(nft.address);
        await nft.setMinterRole(controller.address);
        await nft.setAdminRole(controller.address);

        return {controller, nft, owner, otherAccount};
    }


    it("should revert rank size", async function () {
        const {controller} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 30},
        ];

        const ranks = [
            {id: 0, level: 0, xpFrom: 0, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 30, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 40, name: "asdasd"},
        ];


        await expect(controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        )).to.be.revertedWith(
            "rank size be 5"
        );

    });


    it("should revert 'error level'", async function () {
        const {controller} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 30},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 0, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 10, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 20, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 30, name: "asdasd"},
            {id: 0, level: 7, xpFrom: 40, name: "asdasd"},
        ];


        await expect(controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        )).to.be.revertedWith(
            "level error"
        );

    });


    it("should register a new provider", async function () {
        const {controller} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 30},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 0, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 10, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 20, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 30, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 40, name: "asdasd"},
        ];

        await controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        );

        const provider = await controller.serviceProviders(1);
        expect(provider.name).to.equal("Test Provider");

        const criteria1 = await controller.criterias(1, 1);
        expect(criteria1.name).to.equal("Criteria 1");

        const rank2 = await controller.ranks(1, 2);
        expect(rank2.xpFrom).to.equal(10);
    });

    it("should mint a token and emit a TokenPurchased event", async function () {
        const {controller, owner, otherAccount} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 30},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 0, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 10, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 20, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 30, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 40, name: "asdasd"},
        ];

        await controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        );

        const serviceProviderId = 1;

        // Call the mint function with the service provider ID
        await expect(controller.connect(otherAccount).mint(serviceProviderId, "asdasdasd"))
            .to.emit(controller, "TokenPurchased")
            .withArgs(otherAccount.address, 1, 1, 0, "asdasdasd");
    });

    it("owner ship after transfer", async function () {
        const {controller, nft, owner, otherAccount} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 1, min: 0, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 2, min: 0, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 3, min: 0, max: 30},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 0, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 10, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 20, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 30, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 40, name: "asdasd"},
        ];

        await controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        );

        const serviceProviderId = 1;

        // Call the mint function with the service provider ID
        await expect(controller.connect(otherAccount).mint(serviceProviderId, "Asdasd"))
            .to.emit(controller, "TokenPurchased")
            .withArgs(otherAccount.address, 1, 1, 0, "Asdasd");

        await nft.connect(otherAccount).transferFrom(otherAccount.address, owner.address, 1);
        await nft.setBaseURI("wwwwwww.coo");
        expect(await nft.ownerOf(1)).to.equal(owner.address);
    });

    it("should add XP for a valid token, criteria, and service provider", async function () {
        const {controller, nft, owner, otherAccount} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 2, min: 1, max: 10},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 5, min: 10, max: 20},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 7, min: 15, max: 30},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 50, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 100, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 200, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 300, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 400, name: "asdasd"},
        ];

        await controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        );

        const provider = await controller.serviceProviders(1);
        expect(provider.name).to.equal("Test Provider");

        await expect(controller.connect(otherAccount).mint(1, "asdasd"))
            .to.emit(controller, "TokenPurchased")
            .withArgs(otherAccount.address, 1, 1, 0, "asdasd");

        const tokenId = 1;
        const criteriaId = 1;
        const serviceProviderId = 1;
        const value = 5;


        const ownerBefore = await nft.ownerOf(tokenId);
        expect(ownerBefore).to.equal(otherAccount.address);

        await controller.pushXp(tokenId, serviceProviderId, criteriaId, value);

        const xp = await controller.xps(tokenId);
        expect(fromWei(xp.toString())).to.equal("0.888888888888888888");
    });


    it("levelUP", async function () {
        const {controller, nft, owner, otherAccount} = await deploy();

        const criteria = [
            {id: 0, name: "Criteria 1", description: "Description 1", weight: 2, min: 1, max: 100},
            {id: 0, name: "Criteria 2", description: "Description 2", weight: 5, min: 10, max: 200},
            {id: 0, name: "Criteria 3", description: "Description 3", weight: 7, min: 15, max: 300},
        ];

        const ranks = [
            {id: 0, level: 1, xpFrom: 1, name: "asdasd"},
            {id: 0, level: 2, xpFrom: 2, name: "asdasd"},
            {id: 0, level: 3, xpFrom: 3, name: "asdasd"},
            {id: 0, level: 4, xpFrom: 4, name: "asdasd"},
            {id: 0, level: 5, xpFrom: 5, name: "asdasd"},
        ];

        await controller.registerProvider(
            "Test Provider",
            "Description of Test Provider",
            "http://testprovider.com",
            criteria,
            ranks
        );

        const provider = await controller.serviceProviders(1);
        expect(provider.name).to.equal("Test Provider");

        await expect(controller.connect(otherAccount).mint(1, "asdasd"))
            .to.emit(controller, "TokenPurchased")
            .withArgs(otherAccount.address, 1, 1, 0, "asdasd");

        const tokenId = 1;
        const criteriaId = 1;
        const serviceProviderId = 1;
        const value = 50;


        const ownerBefore = await nft.ownerOf(tokenId);
        expect(ownerBefore).to.equal(otherAccount.address);

        await controller.pushXp(tokenId, serviceProviderId, criteriaId, value);

        const xp = await controller.xps(tokenId);
        console.log(xp);

        await controller.levelUp(1, 1, [1, 2, 3, 4, 5], "asdasd")

    });


});
