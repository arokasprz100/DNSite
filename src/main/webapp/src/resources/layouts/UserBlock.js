import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import { Redirect } from 'react-router-dom'
import { Route, Switch } from "react-router-dom";
import "../styles/UserBlock.css";

class UserBlock extends Component {
  state = {
    userName: "Admin", //TODO: fetch from server
    redirect: false
  };

  setRedirect = () => {
    this.setState({
      redirect: true
    })
  }
  renderRedirect = () => {
    if (this.state.redirect) {
      return <Redirect to='/privacy-policy' />
    }
  }

  render() {
    return (
      <div className="userBlock">
        <h3>
          Welcome <span>{this.state.userName}</span>!
        </h3>
        <div className="userButtons">
          <NavLink to="/changePassword">Change password</NavLink>|
          {this.renderRedirect()}
          <button onClick={this.setRedirect}>Logout</button>
          <Route path = '/privacy-policy' component={() => {
            window.location.href = 'http://localhost:8001/logout';
            return null;
          }}/>
        </div>
      </div>
    );
  }
}

export default UserBlock;
