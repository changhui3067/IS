import React from "react";
import "./../../css/logon.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

var LoginForm = React.createClass({
    getInitialState: function(){
        return { companyId: '',
                 userName: '',
                 passWord: ''
        };
    },

    handleCompanyIdChange: function(e){
        this.setState({companyId: e.target.value});
    },

    handleUsernameChange: function(e){
        this.setState({userName: e.target.value});
    },

    handlePasswordChange: function(e){
        this.setState({passWord: e.target.value});
    },

    handleSubmit: function(e){
        e.preventDefault();
        var company = this.state.companyId.trim();
        var username = this.state.userName.trim();
        var password = this.state.passWord.trim();

        if( !company || !username || !password){
            return;
        }

        var logonData = {
            company: company,
            username: username,
            password: password
        }

        $.ajax({
            url: "/api/login",
            type: "POST",
            //dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(logonData),
            success: function(data){
                console.log("logon: "+data);
                window.location.href = "homepage";
            }.bind(this),
            error: function(xhr, status, err){
                console.log(status, err.toString());
            }.bind(this)
        });
    },

    render: function () {
        let logoImage = new Image();
        logoImage.src = require("./../../img/SF_LoginLogo.png");
        return (
            <div className="logonform">
                <div>
                    <img src={logoImage.src} className="companyLogo"></img>
                </div>
                <form onSubmit={this.handleSubmit}>
                    <div className="bottomborder"><input
                        type="text"
                        placeholder="Company Id"
                        value={this.state.companyId}
                        onChange={this.handleCompanyIdChange}
                    /></div>
                    <div className="bottomborder"><input
                        type="text"
                        placeholder="User Name"
                        value={this.state.username}
                        onChange={this.handleUsernameChange}
                    /></div>
                    <div className="bottomborder"><input
                        placeholder="Password"
                        type="password"
                        value={this.state.passWord}
                        onChange={this.handlePasswordChange}
                    /></div>
                    <input type="submit" value="Logon" className="submitBtn"/>
                </form>
            </div>
        );
    }
});
ReactDOM.render(
    <LoginForm/>,
    document.getElementById("content")
);