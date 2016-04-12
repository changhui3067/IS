/**
 * Created by freyjachang on 4/9/16.
 */

import React from "react";
import "./../css/header.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

class Header extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            menuList: props.menuList,
            currId: props.currId,
            username: props.username
        };
    }

    render(){
        let logoImage = new Image();
        logoImage.src = require("./../img/logo_color.gif");

        return (
            <div className="headerContent">
                <div className="headerLogo"><img src={logoImage.src} className="companyLogo"></img></div>
                <div className="menu">
                    <select className="menuList">
                        {this.state.menuList.forEach((item) => {
                            return (<option value="{item}">{item}</option>);
                        })}
                    </select>
                </div>
                <div className="userinfo">
                    <select className="menuList">
                        <option value="{this.state.username}">{this.state.username}</option>
                        <option value="Logout">Logout</option>
                    </select>
                </div>
            </div>
        );

    }
}

//component property validator
Header.propTypes = {
    menuList: React.PropTypes.array,
    currId: React.PropTypes.number,
    username: React.PropTypes.string
}

//component default property
Header.defaultProps = {
    menuList: ["Homepage", "Courses", "Positions","Admin"],
    currId: 0,
    username: "freyja"
}

export default Header;