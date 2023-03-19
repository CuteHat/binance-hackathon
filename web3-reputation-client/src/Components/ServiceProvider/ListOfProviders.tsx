import React, { Fragment } from "react";
import ZButton from "../MyNftList/BuyNftBtn";
import { EllipsisVerticalIcon } from "@heroicons/react/24/solid";
import {
  useGetMetadataMutation,
  useGetServiceProvidersQuery,
} from "../../features/web3ReputationApi";
import { ethers } from "ethers";
import { betelgeuseController } from "../../utils/contract";

function ListOfProviders() {
  const { data, isLoading, error } = useGetServiceProvidersQuery();
  const [getMetadata] = useGetMetadataMutation();
  if (error) {
    console.error(error);
  }

  async function metadataHandler(x?: number) {
    try {
      const result = await getMetadata().unwrap();
      const url = `https://cloudflare-ipfs.com/ipfs/${result?.hash}`;

      const ethProvider = new ethers.providers.Web3Provider(
        (window as any).ethereum
      );
      const signer = ethProvider.getSigner();

      const contract = betelgeuseController(ethProvider);
      const contractWithSigner = await contract.connect(signer);
      const tx = await contractWithSigner.mint(x, url);
      console.log(tx);
    } catch (e) {
      console.error(e);
    }
  }

  return isLoading ? (
    <div className="py-12 text-center">Loading...</div>
  ) : (
    <table className="table w-full">
      <thead>
        <tr>
          <th className="text-left text-zoroGray pb-[20px]">Company Name</th>
          <th className="text-left text-zoroGray pb-[20px]">Description</th>
          <th className="text-left text-zoroGray pb-[20px]">WebsiteURL</th>
          <th />
        </tr>
      </thead>
      <tbody>
        {data?.map((item) => (
          <Fragment key={item.id}>
            <tr className="bg-gray">
              <td>
                <div className="pl-[40px]">{item?.name}</div>
              </td>
              <td className="py-12 w-1/3">
                <span className="tracking-[0.05em]">{item?.description}</span>
              </td>
              <td>
                <span className="tracking-[0.05em]">{item?.websiteURL}</span>
              </td>
              <td>
                <div className="flex gap-4 items-center">
                  <ZButton
                    label={"Join Provider"}
                    className="bg-black border border-rose"
                    onClick={() => metadataHandler(item.id)}
                  />
                  <EllipsisVerticalIcon className="h-10 cursor-pointer" />
                </div>
              </td>
            </tr>
            <tr>
              <td className="py-[23px]" />
            </tr>
          </Fragment>
        ))}
      </tbody>
    </table>
  );
}

export default ListOfProviders;
