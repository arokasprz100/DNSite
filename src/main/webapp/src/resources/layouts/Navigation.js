import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import "../styles/Navigation.css";

const navList = [
  { name: "supermasters", path: "/supermasters" },
  { name: "zones", path: "/zones" },
  { name: "administration", path: "/administration" }
];

class Navigation extends Component {
  state = {
    navShown: false
  };
  navShow = () => {
    const nav = document.getElementsByClassName("menu")[0];
    if (!this.state.navShown) nav.classList.add("active");
    else nav.classList.remove("active");
    this.setState({
      navShown: !this.state.navShown
    });
  };

  render() {
    const menu = navList.map(item => (
      <li key={item.name}>
        <NavLink to={item.path} exact={item.exact ? item.exact : false}>
          {item.name}
        </NavLink>
      </li>
    ));
    return (
      <nav className="menu">
        <div className="menuContent">
          <ul>{menu}</ul>
        </div>
        <span className="toggle" onClick={this.navShow}>
          <i />
          <i />
          <i />
        </span>
      </nav>
    );
  }
}

export default Navigation;
