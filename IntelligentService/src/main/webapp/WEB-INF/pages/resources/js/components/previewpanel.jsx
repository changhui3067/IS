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
                    {this.props.parent.actions.map( (t, index) => {
                        var onClickFunction = 'onClick'+t.type
                        return (
                            <span key={index}
                                className={"iconfont icon-"+t.icon+" btnicon"}
                                onClick={() => this.props.parent[onClickFunction](this.props.id)}></span>
                        )
                    })}
                    </div>
                </div>
            </div>
        );
    }
}