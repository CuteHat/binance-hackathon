import {ethers} from "hardhat";
import {BigNumber} from "@ethersproject/bignumber";


export function toWei(a: string): BigNumber {
    return ethers.utils.parseUnits(a, "ether");
}

export function fromWei(a: string): string {
    return ethers.utils.formatEther(a);
}