import React from "react";
import "./../../css/courses.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel"

export default class Courses extends React.Component{

    render() {
        return (
            <div id="coursesContent">
                <Commonpanel type='tilelist' {...this.props} />
            </div>

        );
    }
}