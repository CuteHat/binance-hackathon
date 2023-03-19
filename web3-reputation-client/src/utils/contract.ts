import { ethers } from "ethers";
import controllerAbi from "./Contract.json";

const controller_address = import.meta.env.VITE_CONTROLLER_ADDRESS as string;

export const betelgeuseController = (provider: any) =>
  new ethers.Contract(controller_address, controllerAbi, provider);
