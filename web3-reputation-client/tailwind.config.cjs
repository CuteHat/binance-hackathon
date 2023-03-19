/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                secondary: "#100328",
                rose: "#6113EF",
                "rose-light": "#A13BFF",
                gray: '#151515',
                pso: '#172B5D',
                zGray: "#272729",
                greySecondary: '#3A3A3C',
                zoroGray: "#686868",
                buzGray: "#1C1C1C"
            }
        },
    },
    plugins: [],
}