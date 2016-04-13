/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Header from "./header.jsx";
import Footer from "./footer.jsx";
import TileGroup from "./tileGroup.jsx";

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
                <Header />

                <div className="tileContainer">
                    {this.props.tileGroups.map((tileGroup) => {
                        return (
                            <div className="tileGroup">
                                <TileGroup title={tileGroup.title} tileList={tileGroup.tileList}/>
                            </div>
                        );
                    })}
                </div>

                <Footer />
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
                url: ""
            },{
                title: "Finish your profile",
                type: "1",
                icon: "",
                url:""
            },{
                title: "Your courses",
                type: "1",
                icon: "",
                url: ""
            }]
        },
        {
            title: "Hot Jobs",
            tileList: [{
                title: "Hot Jobs",
                type: "2",
                icon: "",
                url: ""
            }]
        },
        {
            title: "Admin Center",
            tileList: [{
                title: "Employee config",
                type: "1",
                icon: "",
                url: ""
            },{
                title: "Position config",
                type: "1",
                icon: "",
                url: ""
            },{
                title: "Courses config",
                type: "1",
                icon: "",
                url: ""
            }]
        }
    ]
}

ReactDOM.render(
    <Homepage />,
    document.getElementById("homepage")
);