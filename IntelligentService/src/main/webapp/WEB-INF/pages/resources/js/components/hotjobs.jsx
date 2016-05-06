import React from "react";
import "./../../css/hotjobs.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Commonpanel from "./commonpanel"
import Dialog from "./dialog"

export default class Hotjobs extends React.Component{

    render() {
        return (
            <div id="hotjobsContent">
                <Dialog {...this.props} />
                <Commonpanel type='tilelist' canadd="false" {...this.props} />
            </div>
        );
    }
}