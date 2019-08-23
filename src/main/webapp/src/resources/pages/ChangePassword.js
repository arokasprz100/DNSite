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
        //TODO: wysłać request do API
    }

    render(){
        return(
            <div className="changePasswordWrapper">
                <div className="inputsWrapper">
                    <div className="oldPasswordWrapper">
                        <input type="password" value={this.state.oldPassword} placeholder="Old password" onChange={this.handleChange} name="oldPassword"/>
                    </div>
                    <div className="newPasswordWrapper">
                        <input type="password" value={this.state.newPassword} placeholder="New password" onChange={this.handleChange} name="newPassword"/>
                        <input type="password" value={this.state.newPasswordConfirm} placeholder="Repeat password" onChange={this.handleChange} name="newPasswordConfirm"/>
                    </div>
                </div>
                <button onClick={this.onSubmit}>Change password</button>
            </div>
        )
    }
}

export default ChangePassword;
