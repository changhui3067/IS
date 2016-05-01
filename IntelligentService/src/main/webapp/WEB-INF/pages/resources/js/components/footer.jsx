/**
 * Created by freyjachang on 4/9/16.
 */
import React from "react";
import "./../../css/footer.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

export default class Footer extends React.Component{
    render(){
        return (
            <div className="footerContent">
                <div className="copyright">
                    Copyright <a href="http://www.successfactors.com">2011 SuccessFactors, Inc.</a> All rights reserved.
                    These online services are SuccessFactors confidential and proprietary and for use by authorized SuccessFactors customers only.
                    Usage may be monitored.
                </div>
            </div>
        );
    }
}