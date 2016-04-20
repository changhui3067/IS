/**
 * Created by freyjachang on 4/9/16.
 */

import React, { PropTypes } from "react";
import "./../../css/header.scss";
import {Link} from "react-router";
import "./../../bootstrap/css/bootstrap.css"
import Bootstrap from "Bootstrap"

const Header = (selectedMenu, onClick) => {
    let logoImage = new Image();
    logoImage.src = require("./../../img/logo_color.gif");

    let menuList = [{
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
        }];

    return (
            <div className="headerContent">
                <div className="headerLogo"><img src={logoImage.src} className="companyLogo"></img></div>
                <div className="menu">
                    <nav className="navbar navbar-default" role="navigation">
                       <div>
                          <ul className="nav navbar-nav">
                            <li className="dropdown">
                                <a id="menuSelect" href="#" className="dropdown-toggle" data-toggle="dropdown">
                                   {selectedMenu}
                                   <b className="caret"></b>
                                </a>
                                <ul className="dropdown-menu">
                                   {menuList.map((item) => {
                                        return (
                                            <li key={item.id}
                                                onClick={e => {
                                                    e.preventDefault()
                                                    onClick(item.name)
                                                }}>
                                                <Link to={item.menulink}>{item.name}</Link>
                                            </li>
                                        );
                                    })}
                                </ul>
                            </li>
                          </ul>
                       </div>
                    </nav>
                </div>
                <div className="userinfo">
                    <nav className="navbar navbar-default" role="navigation">
                       <div>
                          <ul className="nav navbar-nav">
                            <li className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown">
                                   Freyja 
                                   <b className="caret"></b>
                                </a>
                                <ul className="dropdown-menu">
                                   <li><a href="#">Logout</a></li>
                                </ul>
                            </li>
                          </ul>
                       </div>
                    </nav>
                </div>
            </div>
    );
}

//component property validator
Header.propTypes = {
    selectedMenu: PropTypes.string,
    onClick: PropTypes.func
}

//component default property
Header.defaultProps = {
    selectedMenu: "Homepage"
}

export default Header;