/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../..//css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Header from "./header";
import Footer from "./footer";
import TileGroup from "./tileGroup";

 class Homepage extends React.Component{
    constructor(props) {
        super(props);
        this.props = {
            tileGroups : props.tileGroups
        }
    }

    render() {
        return (
            <div id="HomepageContent">
                <div className="tileContainer">
                    {this.props.tileGroups.map((tileGroup, index) => {
                        return (
                            <div className="tileGroup" key={index}>
                                <TileGroup title={tileGroup.title} tileList={tileGroup.tileList} />
                            </div>
                        );
                    })}
                </div>
            </div>
        );
    }
}

Homepage.propType = {
    tileGroups: React.PropTypes.array
}

Homepage.defaultProps = {
    tileGroups: [
        {
            title: "Employee",
            tileList: [{
                title: "Hello",
                type: "0",
                icon: "",
                background: "userphoto.jpg",
                url: "/orgchart"
            },{
                title: "Finish your profile",
                type: "1",
                icon: "caigoufapiao",
                background: "",
                url:"/profile"
            },{
                title: "Your courses",
                type: "1",
                icon: "suoyouzhaobiaoxiangmu",
                background: "",
                url: "/courses"
            }]
        },
        {
            title: "Hot Jobs",
            tileList: [{
                title: "Hot Jobs",
                type: "2",
                icon: "",
                background: "hotjobs_tile.png",
                url: "/hotjobs"
            }]
        },
        {
            title: "Admin Center",
            tileList: [{
                title: "Employee config",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/empconfig"
            },{
                title: "Jobs config",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/jobconfig"
            },{
                title: "Courses config",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/courseconfig"
            }]
        }
    ]
}

export default Homepage;