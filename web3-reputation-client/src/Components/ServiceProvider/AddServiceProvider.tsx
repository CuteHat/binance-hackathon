import React, { useState } from "react";
import Star from "./Star";
import { PlusIcon } from "@heroicons/react/24/outline";
import Nav from "./Nav";
import AddServiceBtn from "./AddServiceBtn";
import { ethers } from "ethers";
import { betelgeuseController } from "../../utils/contract";
import { useNavigate } from "react-router-dom";
import ZButton from "../MyNftList/BuyNftBtn";
import { useLazyGetSuggestionQuery } from "../../features/web3ReputationApi";

export type CriteriaRequest = {
  id: number;
  name: string;
  description: string;
  min: number;
  max: number;
  weight: number;
};

export type RankRequest = {
  id: number;
  name: string;
  xpFrom: number;
  level: number;
};

export type ServiceProviderRequest = {
  name: string;
  description: string;
  websiteURL: string;
  criteria: CriteriaRequest[];
  ranks: RankRequest[];
};

function AddServiceProvider() {
  const [getSuggestions, { isLoading }] = useLazyGetSuggestionQuery();

  const [provider, setProvider] = useState<ServiceProviderRequest>({
    name: "",
    description: "",
    websiteURL: "",
    criteria: [
      {
        id: 1,
        name: "",
        description: "",
        min: 0,
        max: 0,
        weight: 0,
      },
    ],
    ranks: Array.from({ length: 5 }, (_, i) => ({
      id: i + 1,
      name: "",
      xpFrom: 0,
      level: i + 1,
    })),
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const disabled =
    provider.name === "" ||
    provider.description === "" ||
    provider.websiteURL === "" ||
    provider.criteria.some(
      (x) =>
        x.name === "" ||
        x.description === "" ||
        x.min === 0 ||
        x.max === 0 ||
        x.weight === 0
    ) ||
    provider.ranks.some((x) => x.name === "" || x.xpFrom === 0);

  const setProviderInfo = (key: keyof ServiceProviderRequest, value: string) =>
    setProvider((x) => ({ ...x, [key]: value }));

  const addCriteriaForm = () => {
    setProvider((x) => ({
      ...x,
      criteria: [
        ...x.criteria,
        {
          id: x.criteria.reduce((a, b) => (a.id > b.id ? a : b)).id + 1,
          name: "",
          description: "",
          min: 0,
          max: 0,
          weight: 0,
        },
      ],
    }));
  };

  const suggestionHandler = async () => {
    if (!provider.name || !provider.description) return;
    try {
      const response = await getSuggestions({
        name: provider.name,
        description: provider.description,
      }).unwrap();
      if (response.length) {
        setProvider((x) => ({
          ...x,
          criteria: [
            {
              ...x.criteria[0],
              description: response?.[0]?.criteriaDescription ?? "",
              min: response?.[0]?.minValue ?? 0,
              max: response?.[0]?.maxValue ?? 0,
              weight: response?.[0]?.weight ?? 0,
            },
          ],
        }));
      }
      console.log(response);
    } catch (e) {
      console.error(e);
    }
  };

  async function saveProvider() {
    setLoading(true);

    try {
      const ethProvider = new ethers.providers.Web3Provider(
        (window as any).ethereum
      );
      const signer = ethProvider.getSigner();

      const contract = betelgeuseController(ethProvider);
      const contractWithSigner = await contract.connect(signer);

      const transaction = await contractWithSigner.registerProvider(
        provider.name,
        provider.description,
        provider.websiteURL,
        provider.criteria,
        provider.ranks
      );

      await transaction.wait();

      setLoading(false);
      console.log(transaction);
      navigate("/service-provider");
    } catch (e) {
      setLoading(false);
      console.error(e);
    }
  }

  return (
    <div className="w-full px-[15px] md:px-[50px] my-[121px]">
      <Nav>
        <AddServiceBtn />
      </Nav>
      <div className="border border-gray w-full mt-[60px] rounded-[22px] p-[33px] flex flex-col gap-8">
        <div className="flex flex-col gap-[24px]">
          <h3 className="text-[20px]">Register Service Provider</h3>
          <div className="bg-gray rounded-[10px] p-[50px] flex gap-[50px]">
            <input
              className="border border-white leading-[44px] bg-black box-border px-6 text-white rounded-[10px]"
              placeholder="Name"
              value={provider.name}
              onChange={(e) => setProviderInfo("name", e.target.value)}
            />
            <input
              className="border border-white leading-[44px] bg-black box-border px-6 text-white rounded-[10px]"
              placeholder="Description"
              value={provider.description}
              onChange={(e) => setProviderInfo("description", e.target.value)}
            />
            <input
              className="border border-white leading-[44px] bg-black box-border px-6 text-white rounded-[10px]"
              placeholder="websiteURL"
              value={provider.websiteURL}
              onChange={(e) => setProviderInfo("websiteURL", e.target.value)}
            />
          </div>
        </div>

        <div className="flex flex-col gap-[24px]">
          <div className="w-full flex justify-between items-center">
            <h3 className="text-[20px]">Register Criteria</h3>
            <div className="flex gap-4">
              <div
                className="w-[40px] h-[40px] rounded-[100%] bg-rose flex items-center justify-center cursor-pointer"
                onClick={addCriteriaForm}
              >
                <PlusIcon className="w-[24px] h-[24px] text-white mx-auto my-auto" />
              </div>
              <ZButton
                label="Suggestion"
                className="bg-rose"
                onClick={suggestionHandler}
                disabled={isLoading || !provider.name || !provider.description}
              />
            </div>
          </div>
          <div className="bg-gray rounded-[10px] p-[50px] flex flex-col gap-[50px]">
            {provider.criteria.map((criteria) => (
              <div className="flex gap-[50px]" key={criteria.id.toString()}>
                <input
                  key={criteria.id.toString()}
                  className="border border-white leading-[44px] bg-black box-border px-6 w-full text-white rounded-[10px]"
                  value={criteria.name}
                  onChange={(e) =>
                    setProvider((x) => ({
                      ...x,
                      criteria: x.criteria.map((c) =>
                        c.id === criteria.id
                          ? { ...c, name: e.target.value }
                          : c
                      ),
                    }))
                  }
                  placeholder="Name"
                />
                <input
                  className="border border-white leading-[44px] bg-black box-border px-6 w-full text-white rounded-[10px]"
                  placeholder="Description"
                  onChange={(e) =>
                    setProvider((x) => ({
                      ...x,
                      criteria: x.criteria.map((c) =>
                        c.id === criteria.id
                          ? { ...c, description: e.target.value }
                          : c
                      ),
                    }))
                  }
                  value={criteria.description}
                />
                <input
                  className="border border-white leading-[44px] bg-black box-border px-6 w-full text-white rounded-[10px]"
                  value={criteria.min.toString()}
                  placeholder="Min Value"
                  onChange={(e) =>
                    setProvider((x) => ({
                      ...x,
                      criteria: x.criteria.map((c) =>
                        c.id === criteria.id
                          ? { ...c, min: +e.target.value }
                          : c
                      ),
                    }))
                  }
                />
                <input
                  className="border border-white leading-[44px] bg-black box-border px-6 w-full text-white rounded-[10px]"
                  placeholder="Max Value"
                  value={criteria.max.toString()}
                  onChange={(e) =>
                    setProvider((x) => ({
                      ...x,
                      criteria: x.criteria.map((c) =>
                        c.id === criteria.id
                          ? { ...c, max: +e.target.value }
                          : c
                      ),
                    }))
                  }
                />
                <input
                  className="border border-white leading-[44px] bg-black box-border px-6 w-full text-white rounded-[10px]"
                  placeholder="Importants Weight"
                  value={criteria.weight.toString()}
                  onChange={(e) =>
                    setProvider((x) => ({
                      ...x,
                      criteria: x.criteria.map((c) =>
                        c.id === criteria.id
                          ? { ...c, weight: +e.target.value }
                          : c
                      ),
                    }))
                  }
                />
              </div>
            ))}
          </div>
        </div>

        <div className="flex flex-col gap-[24px]">
          <h3 className="text-[20px]">Register Rank</h3>
          <div className="bg-gray rounded-[10px] p-[50px] flex gap-[50px]">
            <table>
              <tbody>
                {provider.ranks.map((rank) => (
                  <tr key={rank.id.toString()}>
                    <td>
                      <div className="flex gap-2 mr-[54px] mb-[54px]">
                        {Array.from({ length: rank.id }).map((_, index) => (
                          <Star key={`${index}-star`} />
                        ))}
                      </div>
                    </td>
                    <td>
                      <input
                        className="border border-white leading-[44px] bg-black box-border px-6 text-white rounded-[10px] mr-[54px] mb-[54px]"
                        value={rank.name}
                        onChange={(e) => {
                          setProvider((x) => ({
                            ...x,
                            ranks: x.ranks.map((r) =>
                              r.id === rank.id
                                ? { ...r, name: e.target.value }
                                : r
                            ),
                          }));
                        }}
                        placeholder="Name"
                      />
                    </td>
                    <td>
                      <input
                        className="border border-white leading-[44px] bg-black box-border px-6 text-white rounded-[10px] mb-[54px]"
                        value={rank.xpFrom.toString()}
                        onChange={(e) => {
                          setProvider((x) => ({
                            ...x,
                            ranks: x.ranks.map((r) =>
                              r.id === rank.id
                                ? { ...r, xpFrom: +e.target.value }
                                : r
                            ),
                          }));
                        }}
                        placeholder="XP"
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <button
          className={`bg-rose p-4 rounded-[41px] self-end ${
            disabled || loading ? "opacity-50" : "opacity-100"
          }`}
          disabled={disabled || loading}
          onClick={saveProvider}
        >
          Save Results
        </button>
      </div>
    </div>
  );
}

export default AddServiceProvider;
