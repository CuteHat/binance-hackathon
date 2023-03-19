import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  CriteriaSuggestion,
  InfuraUploadResponse,
  NftListItemResponse,
  ServiceProviderResponse,
} from "../model/web3ReputationAPI";

export const web3ReputationApi = createApi({
  reducerPath: "web3ReputationApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `${import.meta.env.VITE_ENDPOINT_BASE_URL}`,
  }),
  tagTypes: ["Recommend", "ServiceProviders", "MyNft"],
  endpoints: (build) => ({
    getSuggestion: build.query<
      CriteriaSuggestion[],
      { name: string; description: string }
    >({
      query: ({ name, description }) =>
        `/service-provider/recommend/criteria/${name}/${description}`,
      providesTags: ["Recommend"],
    }),
    getServiceProviders: build.query<ServiceProviderResponse[], void>({
      query: () => `/service-provider`,
      providesTags: ["ServiceProviders"],
    }),
    getMyNft: build.query<NftListItemResponse[], string>({
      query: (address) => `/user/${address}/nft`,
      providesTags: ["MyNft"],
    }),
    getMetadata: build.mutation<InfuraUploadResponse, void>({
      query: () => ({
        url: "/nft/metadata",
        method: "POST",
      }),
    }),
    getMetadataByTokenId: build.mutation<
      InfuraUploadResponse,
      { tokenId?: number; level?: number; rankName?: string; xp?: number }
    >({
      query: ({ tokenId, level, rankName, xp }) => ({
        url: `/nft/metadata/${tokenId}/${level}/${rankName}/${xp}`,
        method: "POST",
      }),
    }),
  }),
});

export const {
  useLazyGetSuggestionQuery,
  useGetServiceProvidersQuery,
  useLazyGetMyNftQuery,
  useGetMetadataMutation,
  useGetMetadataByTokenIdMutation,
} = web3ReputationApi;
