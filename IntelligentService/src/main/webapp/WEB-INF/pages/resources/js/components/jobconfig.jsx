import React from "react";
import "./../../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";

export default class JobConfig extends React.Component{

    render() {
        return (
            <div id="jobconfigContent">
                <Commonpanel type='tilelist' {...this.props} />
            </div>
        );
    }
}