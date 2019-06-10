import React, { Component } from "react";
import "../styles/UserBlock.css";

class UserBlock extends Component {
  state = { userName: "Example" };
  render() {
    return (
      <div className="userBlock">
        <h3>
          Witaj <span>{this.state.userName}</span>!
        </h3>
        <div className="userButtons">
          <button onclick="location.href = 'localhost:8001/login';">Ustawienia konta</button>|<button>Wyloguj</button>
        </div>
      </div>
    );
  }
}

export default UserBlock;
