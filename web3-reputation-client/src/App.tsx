import {
  EthereumClient,
  w3mConnectors,
  w3mProvider,
} from "@web3modal/ethereum";
import { configureChains, createClient, WagmiConfig } from "wagmi";
import { arbitrum, bsc, bscTestnet, mainnet, polygon } from "wagmi/chains";
import { Web3Modal } from "@web3modal/react";
import { Outlet } from "react-router-dom";
import Header from "./Components/Header";
import Footer from "./Components/Footer";

const chains = [arbitrum, mainnet, polygon, bsc, bscTestnet];
const projectId = import.meta.env.VITE_WALLETCONNECT_PROJECT as string;

const { provider } = configureChains(chains, [w3mProvider({ projectId })]);

const wagmiClient = createClient({
  autoConnect: true,
  connectors: w3mConnectors({ projectId, version: 1, chains }),
  provider,
});

const ethereumClient = new EthereumClient(wagmiClient, chains);

function App() {
  return (
    <div className="min-h-screen">
      <WagmiConfig client={wagmiClient}>
        <Header />
        <div className="min-h-screen">
          <Outlet />
        </div>
        <Footer />
      </WagmiConfig>
      <Web3Modal projectId={projectId} ethereumClient={ethereumClient} />
    </div>
  );
}

export default App;
