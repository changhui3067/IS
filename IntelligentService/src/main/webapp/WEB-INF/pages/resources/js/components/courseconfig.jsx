import React from "react";
import "./../../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";

export default class Courseconfig extends React.Component{

    render() {
        return (
            <div id="courseconfigContent">
                <Commonpanel type='tilelist' {...this.props} />
            </div>
        );
    }
}