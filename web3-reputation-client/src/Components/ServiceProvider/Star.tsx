import React from "react";

function Star() {
  return (
    <svg
      width="58"
      height="55"
      viewBox="0 0 58 55"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g filter="url(#filter0_i_1_212)">
        <path
          d="M29.2566 0.128406L35.9032 20.8865L57.6993 20.9798L40.011 33.7158L46.6577 54.4739L29.0791 41.587L11.3909 54.3229L18.215 33.6225L0.6364 20.7356L22.4325 20.8289L29.2566 0.128406Z"
          fill="#FFEF4A"
        />
      </g>
      <path
        d="M29.2496 1.74643L35.427 21.039L35.5378 21.385L35.9011 21.3865L56.1583 21.4732L39.7189 33.31L39.4241 33.5223L39.5349 33.8682L45.7122 53.1608L29.3747 41.1838L29.0818 40.969L28.787 41.1812L12.3475 53.018L18.6898 33.779L18.8036 33.434L18.5106 33.2192L2.17309 21.2422L22.4303 21.3289L22.7936 21.3304L22.9073 20.9854L29.2496 1.74643Z"
        stroke="#868588"
      />
      <defs>
        <filter
          id="filter0_i_1_212"
          x="0.63623"
          y="0.128403"
          width="57.063"
          height="58.3455"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="BackgroundImageFix"
            result="shape"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="4" />
          <feGaussianBlur stdDeviation="2" />
          <feComposite in2="hardAlpha" operator="arithmetic" k2="-1" k3="1" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"
          />
          <feBlend
            mode="normal"
            in2="shape"
            result="effect1_innerShadow_1_212"
          />
        </filter>
      </defs>
    </svg>
  );
}

export default Star;
