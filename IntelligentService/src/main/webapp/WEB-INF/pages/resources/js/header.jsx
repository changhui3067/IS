/**
 * Created by freyjachang on 4/9/16.
 */

import React from "react";
import "./../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

class Header extends React.Component {
    constructor(props){
        super(props);
    }
    render(){
        let logoImage = new Image();
        logoImage.src = require("./../img/logo_color.gif");

        return (
            <div class="headerContent">
                <div class="headerLogo"><img src={logoImage.src} className="companyLogo"></img></div>
                <div class="menu"></div>
                <div class="useinfo"></div>
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
    menuList: ["homepage", "courses", "admin"],
    currId: 0,
    username: "freyja"
}

export default Header;