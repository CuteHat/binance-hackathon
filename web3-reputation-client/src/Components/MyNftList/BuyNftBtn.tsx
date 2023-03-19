import React from "react";

function ZButton({
  className,
  onClick,
  label,
  disabled = false,
}: {
  className?: string;
  onClick?: () => void;
  label: string;
  disabled?: boolean;
}) {
  return (
    <button
      type={"button"}
      className={`rounded-[41px] px-6 py-2 tracking-[0.05em] ${className} ${
        disabled ? "opacity-50 cursor-not-allowed" : "hover:opacity-100"
      } `}
      disabled={disabled}
      onClick={(e) => {
        e.stopPropagation();
        onClick?.();
      }}
    >
      {label}
    </button>
  );
}

export default ZButton;
