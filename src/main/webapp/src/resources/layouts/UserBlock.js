import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import "../styles/UserBlock.css";

class UserBlock extends Component {
  state = { userName: "Admin" };
  render() {
    return (
      <div className="userBlock">
        <h3>
          Witaj <span>{this.state.userName}</span>!
        </h3>
        <div className="userButtons">
            <NavLink to="/changePassword">Zmiana hasła</NavLink>|<button>Wyloguj</button>
        </div>
      </div>
    );
  }
}

export default UserBlock;
