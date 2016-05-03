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
                name: 'Orgchart',
                type: "0",
                icon: "",
                background: "userphoto.jpg",
                url: "/orgchart"
            },{
                title: "Finish your profile",
                name: 'Profile',
                type: "1",
                icon: "caigoufapiao",
                background: "",
                url:"/profile"
            },{
                title: "Your courses",
                name: 'Course',
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
                name: "Hotjobs",
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
                name: "EmpConfig",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/empconfig"
            },{
                title: "Jobs config",
                name: "JobConfig",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/jobconfig"
            },{
                title: "Courses config",
                name: "CourseConfig",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/courseconfig"
            },{
                title: "Subscriber config",
                name: "SubscriberConfig",
                type: "1",
                icon: "shezhi",
                background: "",
                url: "/subconfig"
            }]
        }
    ]
}

export default Homepage;