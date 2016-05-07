import React from "react";
import "./../../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";
import Dialog from "./dialog"

export default class Courseconfig extends React.Component{

    render() {
        return (
            <div id="courseconfigContent">
                <Dialog {...this.props} />
                <Commonpanel type='tilelist' {...this.props} />
            </div>
        );
    }
}