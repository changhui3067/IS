import React from "react";
import "./../../css/hotjobs.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel"

export default class Hotjobs extends React.Component{

    render() {
        return (
            <div id="hotjobsContent">
                <Commonpanel type='tilelist' {...this.props} />
            </div>
        );
    }
}