import React, {Component} from "react";
import "../styles/UserBlock.css";
import { withRouter } from 'react-router-dom';
import { Button, Card, CardBody, CardGroup, Col, Container, Input, InputGroup, InputGroupAddon, InputGroupText, Row, NavLink  } from 'reactstrap';

class UserBlock extends Component {
    constuctor() {
        this.routeChange = this.routeChange.bind(this);
    }

    routeChange() {
        let path = `localhost:8001/login`;
        this.props.history.push(path);
    }

    state = {userName: "Example"};

    render() {
        return (
            <div className="userBlock">
                <h3>
                    Witaj <span>{this.state.userName}</span>!
                </h3>
                    <div className="userButtons">
                    <button>Ustawienia konta</button>
                    <button onClick={this.routeChange}>Wyloguj</button>
                </div>
            </div>
        );
    }
}

export default UserBlock;
