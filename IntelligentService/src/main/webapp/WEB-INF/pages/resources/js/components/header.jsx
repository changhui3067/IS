/**
 * Created by freyjachang on 4/9/16.
 */

import React from "react";
import "./../../css/header.scss";
import $ from "jquery";
import {Link} from "react-router";

export default class Header extends React.Component {
    constructor(props){
        super(props);
        this.props = {
            menuList: props.menuList,
            currId: props.currId
        };
    }

    render(){
        let logoImage = new Image();
        logoImage.src = require("./../../img/logo_color.gif");

        return (
            <div className="headerContent">
                <div className="headerLogo"><img src={logoImage.src} className="companyLogo"></img></div>
                <div className="menu">
                    <div className="wrapper">
                        <div className="content">
                            <ul>
                                {this.props.menuList.map((item) => {
                                    return (
                                        <Link to={item.menulink}><li>{item.name}</li></Link>
                                    );
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
    menuList: [{
            name: "Homepage",
            menulink: "/homepage"
        },{
            name: "Orgchart",
            menulink: "/orgchart"
        },{
            name: "Profile",
            menulink: "/profile"
        },{
            name: "Hotjobs",
            menulink: "/hotjobs"
        },{
            name: "EmpConfig",
            menulink: "/empconfig"
        },{
            name: "JobConfig",
            menulink: "/jobconfig"
        },{
            name: "CourseConfig",
            menulink: "/courseconfig"
        }],
    currId: 0
}