/**
 * Created by freyjachang on 4/9/16.
 */

import React from "react";
import "./../../css/header.scss";
import {Link} from "react-router";
import "./../../bootstrap/css/bootstrap.css"
import Bootstrap from "Bootstrap"

export default class Header extends React.Component {
    constructor(props) {
        super(props);
        this.props = {

            menuList: props.menuList,
            currId: props.currId
        };
        this.state = {
            selected: "Homepage",
        }
    }

    handleMenuClick(e) {
        console.log("Route to ", e);
        this.setState({selected: e});
    }

    render() {
        let logoImage = new Image();
        logoImage.src = require("./../../img/logo_color.gif");

        return (
            <div className="headerContent">
                <div className="headerLogo"><img src={logoImage.src} className="companyLogo"></img></div>
                <div className="menu">
                    <nav className="navbar navbar-default" role="navigation">
                       <div>
                          <ul className="nav navbar-nav">
                            <li className="dropdown">
                                <a id="menuSelect" href="#" className="dropdown-toggle" data-toggle="dropdown">
                                   {this.state.selected} 
                                   <b className="caret"></b>
                                </a>
                                <ul className="dropdown-menu">
                                   {this.props.menuList.map((item) => {
                                        return (
                                            <li key={item.id} onClick={this.handleMenuClick.bind(this, item.name)}><Link to={item.menulink}>{item.name}</Link></li>
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