import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { web3ReputationApi } from "../features/web3ReputationApi";

const rootReducer = combineReducers({
  [web3ReputationApi.reducerPath]: web3ReputationApi.reducer,
});

export const store = configureStore({
  reducer: rootReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }).concat(
      ...[web3ReputationApi.middleware]
    ),
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
