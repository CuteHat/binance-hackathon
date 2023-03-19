import { defineConfig } from "orval";

export default defineConfig({
  web3ReputationAPI: {
    output: {
      mode: "tags-split",
      schemas: "src/model/web3ReputationAPI",
    },
    input: {
      target: "https://web3-reputation-api.herokuapp.com/v3/api-docs",
    },
    hooks: {
      afterAllFilesWrite: "prettier --write",
    },
  },
});
