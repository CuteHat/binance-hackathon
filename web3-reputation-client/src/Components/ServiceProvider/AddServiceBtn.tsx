import React from "react";
import { Link } from "react-router-dom";

function AddServiceBtn() {
  return (
    <Link
      to={"/add-service-provider"}
      className="bg-rose rounded-[41px] px-6 py-2 tracking-[0.05em] min-w-[142px]"
    >
      Add New SP
    </Link>
  );
}

export default AddServiceBtn;
