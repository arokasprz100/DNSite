import React, {Component} from "react";
import {NavLink} from "react-router-dom";
import "../styles/Navigation.css";

const navList = [
  { name: "supermasters", path: "/supermasters" },
  { name: "domains", path: "/domains" },
  { name: "records", path: "/records" },
  { name: "administration", path: "/administration" },
  { name: "history", path: "/history" }
];

class Navigation extends Component {
  state = {
      userPanelShow: false
  };
    userPanelShow = () => {
    const header = document.getElementsByTagName("header")[0];
    if (!this.state.userPanelShow) header.classList.add("deleteOverflow");
    else header.classList.remove("deleteOverflow");

    const icon = document.getElementsByClassName("toggle")[0];
    if (!this.state.userPanelShow) icon.classList.add("active");
    else icon.classList.remove("active");

    const userBlock = document.getElementsByClassName("userBlock")[0];
    if (!this.state.userPanelShow) userBlock.classList.add("showBlock");
    else userBlock.classList.remove("showBlock");

    this.setState({
        userPanelShow: !this.state.userPanelShow
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
        <>
            <nav className="menu">
                 <div className="menuContent">
                    <ul>{menu}</ul>
                 </div>
            </nav>
            <span className="toggle" onClick={this.userPanelShow}>
              <i className="fas fa-user"></i>
            </span>
        </>
    );
  }
}

export default Navigation;
