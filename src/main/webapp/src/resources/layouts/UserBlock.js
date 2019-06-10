import React, { Component } from "react";
import "../styles/UserBlock.css";

class UserBlock extends Component {
  state = { userName: "User" };
  render() {
    return (
      <div className="userBlock">
        <h4>
          Hello <span>{this.state.userName}</span>!
        </h4>
        <div className="userButtons">
          <button>Change password</button><button>Logout</button>
        </div>
      </div>
    );
  }
}

export default UserBlock;
