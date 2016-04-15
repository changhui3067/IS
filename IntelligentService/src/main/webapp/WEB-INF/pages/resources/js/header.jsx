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
                    <div className="wrapper">
                        <div className="content">
                            <ul>
                                {this.state.menuList.map((item) => {
                                    return (<a><li>{item}</li></a>);
                                })}
                            </ul>
                        </div>
                        <div className="parent">Homepage</div>
                    </div>
                </div>
                <div className="userinfo">
                    <div className="wrapper">
                        <div className="content">
                            <ul>
                                <a><li>Logout</li></a>
                            </ul>
                        </div>
                        <div className="parent">Freyja</div>
                    </div>
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