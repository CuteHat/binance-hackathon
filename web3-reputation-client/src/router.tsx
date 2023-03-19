import { createBrowserRouter } from "react-router-dom";
import Landing from "./Components/Landing";
import App from "./App";
import Product from "./Components/Product";
import Partnership from "./Components/Parnership";
import ErrorBoundary from "./Components/ErrorBoundary";
import ServiceProvider from "./Components/ServiceProvider";
import MyNftList from "./Components/MyNftList";
import AddServiceProvider from "./Components/ServiceProvider/AddServiceProvider";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorBoundary />,
    children: [
      { index: true, element: <Landing /> },
      { path: "product", element: <Product /> },
      { path: "partnership", element: <Partnership /> },
      { path: "service-provider", element: <ServiceProvider /> },
      { path: "add-service-provider", element: <AddServiceProvider /> },
      { path: "my-nft", element: <MyNftList /> },
    ],
  },
]);

export default router;
