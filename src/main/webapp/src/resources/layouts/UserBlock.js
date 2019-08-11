import React, { Component } from "react";
import "../styles/UserBlock.css";
import { Redirect } from 'react-router-dom'
import { Route, Switch } from "react-router-dom";

class UserBlock extends Component {
  state = {
      redirect: false
    }
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
        <h4>
          Hello <span>User</span>!
        </h4>
        <div className="userButtons">
          <button>Profile settings</button>
          <button>Change password</button>
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
