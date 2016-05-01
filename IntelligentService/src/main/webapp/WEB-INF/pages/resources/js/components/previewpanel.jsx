import React from "react";
import "./../../css/previewpanel.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

export default class Previewpanel extends React.Component{

    render() {
        return (
            <div className='previewPanel'>
                <div className='preivewPic'>
                </div>
                <div className='previewInfo'>
                    <div className='title'>
                        this is {this.props.title}
                    </div>
                    <div className='info'>
                        {this.props.finished}
                    </div>
                    <div className='btns'>
                        this is btns
                    </div>
                </div>
            </div>
        );
    }
}