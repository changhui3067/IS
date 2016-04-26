import React from "react";
import "./../../css/commonpanel.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Previewpanel from './previewpanel'

export default class Commonpanel extends React.Component{

    render() {
        return (
            <div>
                <div className="titlePart">
                    <span>Courses</span>
                </div>
                <div className="tabsPart">
                    <div className="tab selected" onClick={this.props.onClickTab} name='todo'>ToDo</div>
                    <div className="tab" onClick={this.props.onClickTab} name='finished'>Finished</div>
                </div>
                <div className="contentPart">
                    {this.props.list.map(( preview) => {
                        return (<Previewpanel {...preview}/>)
                    })}
                </div>
            </div>
        );
    }
}