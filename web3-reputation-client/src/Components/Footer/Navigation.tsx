import React from "react";
import NavItem from "./NavItem";

export type FooterNavs = {
  id: number;
  title: string;
  items: string[];
};
function Navigation() {
  const [navs] = React.useState<Array<FooterNavs>>([
    {
      id: 1,
      title: "About",
      items: [
        "Our Story",
        "Team",
        "Our Mission",
        "Careers",
        "Partners",
        "Contact Us",
      ],
    },
    {
      id: 2,
      title: "Privacy",
      items: ["White Paper", "Your Choices", "Terms of use"],
    },
    {
      id: 3,
      title: "Social",
      items: ["Instagram", "Discord", "LinkedIn"],
    },
  ]);
  return (
    <div className="flex gap-[80px]">
      {navs.map((nav) => (
        <NavItem key={nav.id.toString()} nav={nav} />
      ))}
    </div>
  );
}

export default Navigation;
