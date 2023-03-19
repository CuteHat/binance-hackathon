import {HardhatUserConfig} from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";

require('hardhat-abi-exporter');


const prKey = "b7e64053fca6e837a39ba932539d4f1474096e5b72e0b5dd022e75bbc2bf6568";
const infuraEndpoint = "https://polygon-mumbai.infura.io/v3/c9ffc30a4490470bb7fee9e7a8b4665b";
const bnbEndpoint = "https://data-seed-prebsc-1-s1.binance.org:8545/";
const bnb2 = "https://little-few-arm.bsc-testnet.quiknode.pro/06050e6710ef4d89044fa4e6787fbc190722d9ef/"

const config: HardhatUserConfig = {
    networks: {
        localhost: {
            url: "http://127.0.0.1:8545",
            gas: 12000000,
            blockGasLimit: 12000000,
        },
        mumbai: {
            url: infuraEndpoint,
            accounts: [prKey],
            chainId: 80001
        },
        bnb: {
            url: bnbEndpoint,
            accounts: [prKey],
            chainId: 97
        },
        bnb2: {
            url: bnbEndpoint,
            accounts: [prKey],
        },

    },

    solidity: {
        version: "0.8.17",
        settings: {
            optimizer: {
                enabled: true,
                runs: 200
            },
        },
    },
};

export default config;
