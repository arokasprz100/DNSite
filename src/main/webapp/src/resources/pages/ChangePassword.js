import React from "react";
import "../styles/ChangePassword.css";

class ChangePassword extends React.Component {
    state = {
        newPassword: "",
        newPasswordConfirm: "",
        oldPassword: "",
        errorMessage: "",
        changeSuccess: false,
        errorOnServer: false,
        oldPasswordEmptyError: false,
        newPasswordEmptyError: false,
        newDifferentPasswordsError: false,
    }

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
        });
    }

    onSubmit = (e) => {
        let olsPasswordError = false;
        let newPasswordError = false;
        let newPasswordDifferentError = false;
        if(this.state.oldPassword === ""){
            olsPasswordError = true;
        }
        if (this.state.newPassword === "" || this.state.newPasswordConfirm === ""){
            newPasswordError = true;
        } else if (this.state.newPassword !== this.state.newPasswordConfirm){
            newPasswordDifferentError = true;
        }
        if(olsPasswordError || newPasswordError || newPasswordDifferentError) {
            this.setState({
                oldPasswordEmptyError: olsPasswordError,
                newPasswordEmptyError: newPasswordError,
                newDifferentPasswordsError: newPasswordDifferentError,
            })
        } else {
            return fetch('http://localhost:8001/changePasswd', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(this.state)
            }).then(response => {
                if (response.ok) {
                    return response;
                }
                throw Error(response.status);
            })
            //TODO Better showing information about changing password and clear forms
                .then(response => {
                    return response.json();
                }).then(data => {
                    if (data.changed === true) {
                        this.setState({
                            newPassword: "",
                            newPasswordConfirm: "",
                            oldPassword: "",
                            errorMessage: "",
                            changeSuccess: true,
                            errorOnServer: false,
                            oldPasswordEmptyError: false,
                            newPasswordEmptyError: false,
                            newDifferentPasswordsError: false,
                        })
                    } else if (data.changed === false) {
                        this.setState({
                            newPassword: "",
                            newPasswordConfirm: "",
                            oldPassword: "",
                            errorMessage: data.errorMessage,
                            changeSuccess: false,
                            errorOnServer: true,
                            oldPasswordEmptyError: false,
                            newPasswordEmptyError: false,
                            newDifferentPasswordsError: false,
                        })
                    }
                })
                .catch(error => console.error(error));
        }
    }

    render(){
        return(
            <div className="changePasswordWrapper">
                <div class="requestStatus">
                    {this.state.changeSuccess && <div className="requestSuccess">Your password has been changed</div>}
                    {this.state.errorOnServer && <div className="requestError">Error during changing password: {this.state.errorMessage}</div>}
                </div>
                <div className="inputsWrapper">
                    <div className="oldPasswordWrapper">
                        {this.state.oldPasswordEmptyError && <div className="subText">Field cannot be empty</div>}
                        <input type="password" value={this.state.oldPassword} placeholder="Old password" onChange={this.handleChange} name="oldPassword"/>
                    </div>
                    <div className="newPasswordWrapper">
                        {this.state.newPasswordEmptyError && <div className="subText">Fields cannot be empty</div>}
                        {this.state.newDifferentPasswordsError && <div className="subText">Entered passwords are different</div>}
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
