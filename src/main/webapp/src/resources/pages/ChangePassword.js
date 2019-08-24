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
        console.log(JSON.stringify(this.state));
        return fetch('http://localhost:8001/changePasswd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.state)
        }).then(response => {
            if (response.ok) { return response; }
            throw Error(response.status);
        })
            .then(response => console.log(response))
            .catch(error => console.error(error));

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
