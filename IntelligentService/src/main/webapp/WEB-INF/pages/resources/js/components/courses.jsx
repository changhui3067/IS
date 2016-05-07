import React from "react";
import "./../../css/courses.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";
import Dialog from "./dialog"

export default class Courses extends React.Component{

    render() {
        return (
            <div id="coursesContent">
                <Dialog {...this.props} />
                <Commonpanel type='tilelist' canadd='false' {...this.props} />
            </div>

        );
    }
}