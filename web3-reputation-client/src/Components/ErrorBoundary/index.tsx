import { useRouteError } from "react-router-dom";

export default function ErrorBoundary() {
  const error = useRouteError();
  console.error(error);
  return (
    <div className="w-full h-screen flex justify-center items-center font-size-20">
      Dang! Something Went Wrong!
    </div>
  );
}
