import React, { Fragment, useEffect } from "react";
import listCard from "../../assets/listCard.png";
import Star from "../ServiceProvider/Star";
import ZButton from "./BuyNftBtn";
import {
  useGetMetadataByTokenIdMutation,
  useLazyGetMyNftQuery,
} from "../../features/web3ReputationApi";
import { useAccount } from "wagmi";
import { NftListItemResponse } from "../../model/web3ReputationAPI";
import { ethers } from "ethers";
import { betelgeuseController } from "../../utils/contract";

function NftList() {
  const [collapsed, setCollapsed] = React.useState<number[]>([]);
  const { address } = useAccount();
  const [getNft, { data, isLoading }] = useLazyGetMyNftQuery();
  const [getMetadata] = useGetMetadataByTokenIdMutation();

  async function claimHandler(item: NftListItemResponse) {
    if (!item?.claimable) return;

    try {
      const result = await getMetadata({
        tokenId: item?.tokenId,
        level: (item?.rankLevel ?? 0) + 1,
        rankName: "Silver",
        xp: (item?.xp ?? 0) + 10,
      }).unwrap();
      const url = `https://cloudflare-ipfs.com/ipfs/${result?.hash}`;
      const ethProvider = new ethers.providers.Web3Provider(
        (window as any).ethereum
      );
      const signer = ethProvider.getSigner();
      const ranksId = item?.serviceProvider?.ranks
        ?.map((x) => x?.rankIdentifier)
        .sort((a, b) => (a ?? 0) - (b ?? 0));

      const contract = betelgeuseController(ethProvider);
      const contractWithSigner = await contract.connect(signer);
      console.log(item?.tokenId, item?.serviceProvider?.id, ranksId, url);

      const tx = await contractWithSigner.levelUp(
        item?.tokenId,
        item?.serviceProvider?.id,
        ranksId,
        url
      );
      console.log(tx);
    } catch (e) {
      console.error(e);
    }
  }

  useEffect(() => {
    if (!address) return;
    getNft(address);
  }, [address]);

  return isLoading ? (
    <div className="text-center">Loading...</div>
  ) : (
    <table className="table w-full">
      <thead>
        <tr>
          <th className="text-left text-zoroGray pb-[20px]">#</th>
          <th className="text-left text-zoroGray pb-[20px]">Ranks Name</th>
          <th className="text-left text-zoroGray pb-[20px]">Ranks Count</th>
          <th className="text-left text-zoroGray pb-[20px]">Company</th>
          <th className="text-left text-zoroGray pb-[20px]">XP</th>
          <th className="text-left text-zoroGray pb-[20px]">Claim Status</th>
        </tr>
      </thead>
      <tbody>
        {data?.map((item) => (
          <Fragment key={item?.tokenId}>
            <tr
              className="bg-gray cursor-pointer"
              onClick={() =>
                setCollapsed((x) =>
                  x.includes(item?.tokenId ?? 0)
                    ? x.filter((y) => y !== item?.tokenId)
                    : [...x, item?.tokenId ?? 0]
                )
              }
            >
              <td className="p-4">
                <img src={listCard} />
              </td>
              <td>
                <span className="tracking-[0.05em]">{item?.rankName}</span>
              </td>
              <td>
                <div className="flex">
                  {Array.from({ length: item?.rankLevel ?? 0 }).length > 0
                    ? Array.from({ length: item?.rankLevel ?? 0 }).map(
                        (_, i) => <Star key={`${i}-rank`} />
                      )
                    : "-"}
                </div>
              </td>
              <td>
                <button className="py-2 px-4 border rounded-[41px]">
                  {item?.serviceProvider?.name}
                </button>
              </td>
              <td>
                <span className="tracking-[0.05em]">{item?.xp ?? 0}</span>
              </td>
              <td>
                <div className="flex gap-4">
                  <ZButton
                    label={"Claim Now"}
                    className="bg-rose "
                    disabled={!item?.claimable}
                    onClick={() => claimHandler(item)}
                  />
                </div>
              </td>
            </tr>
            <tr
              className={`bg-gray select-none ${
                collapsed.includes(item?.tokenId ?? 0) ? "" : "collapse"
              }`}
            >
              <td className="p-4" colSpan={6}>
                <table className="w-full">
                  <thead>
                    <tr>
                      <th className="text-left text-zoroGray pb-[20px]">
                        Criteria Name
                      </th>
                      <th className="text-left text-zoroGray pb-[20px]">
                        Criteria Desciption
                      </th>
                      <th className="text-left text-zoroGray pb-[20px]">
                        Criteria Min Value
                      </th>
                      <th className="text-left text-zoroGray pb-[20px]">
                        Criteria Max Value
                      </th>
                      <th className="text-left text-zoroGray pb-[20px]">
                        Important us Weight
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {item?.serviceProvider?.criterias?.map((c) => (
                      <Fragment key={c?.id}>
                        <tr className="bg-buzGray">
                          <td className="py-[24px] pl-[41px]">
                            <span className="tracking-[0.05em]">{c?.name}</span>
                          </td>
                          <td className="py-[24px]">
                            <span className="tracking-[0.05em]">
                              {c?.description}
                            </span>
                          </td>
                          <td className="py-[24px]">
                            <span className="tracking-[0.05em]">{c?.min}</span>
                          </td>
                          <td className="py-[24px]">
                            <span className="tracking-[0.05em]">{c?.max}</span>
                          </td>
                          <td className="py-[24px] pr-[41px]">
                            <span className="tracking-[0.05em]">
                              {c?.weight}
                            </span>
                          </td>
                        </tr>
                        <tr>
                          <td className="py-[10px]" />
                        </tr>
                      </Fragment>
                    ))}
                  </tbody>
                </table>
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

export default NftList;
