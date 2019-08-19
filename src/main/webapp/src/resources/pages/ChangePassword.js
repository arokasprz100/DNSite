import React from "react";
import "../styles/ChangePassword.css";

class ChangePassword extends React.Component {
    state = {
        newPassword: "",
        newPasswordConfirm: "",
        oldPassword: "",
    }

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
        });
    }

    onSubmit = (e) => {
        //wysłać request do API
    }

    render(){
        return(
            <div className="changePasswordWrapper">
                <div className="inputsWrapper">
                    <div className="oldPasswordWrapper">
                        <p>Wprowadź stare hasło:</p>
                        <input value={this.state.oldPassword} placeholder="Stare hasło" onChange={this.handleChange} name="oldPassword"/>
                    </div>
                    <div className="newPasswordWrapper">
                        <p>Wprowadź nowe hasło:</p>
                        <input value={this.state.newPassword} placeholder="Nowe hasło" onChange={this.handleChange} name="newPassword"/>
                        <input value={this.state.newPasswordConfirm} placeholder="Powtórz hasło" onChange={this.handleChange} name="newPasswordConfirm"/>
                    </div>
                </div>
                <button onClick={this.onSubmit}>Zmień hasło</button>
            </div>
        )
    }
}

export default ChangePassword;
