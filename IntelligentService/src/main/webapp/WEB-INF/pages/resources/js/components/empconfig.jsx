import React from "react";
import "./../../css/empConfig.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel";

export default class EmpConfig extends React.Component{

    render() {
        console.log(this.props)
        return (
            <div id="empconfigContent">
                <Commonpanel type='list' {...this.props} />
            </div>
        );
    }
}