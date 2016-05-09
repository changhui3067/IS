import React from "react";
import "./../../css/empConfig.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";
import Dialog from "./dialog"

export default class EmpConfig extends React.Component{

    render() {
        return (
            <div id="empconfigContent">
                <Dialog {...this.props} />
                <Commonpanel type='list' {...this.props} />
            </div>
        );
    }
}