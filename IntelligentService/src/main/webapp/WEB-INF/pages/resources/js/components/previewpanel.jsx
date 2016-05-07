import React from "react";
import "./../../css/previewpanel.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

export default class Previewpanel extends React.Component{

    render() {
        return (
            <div className='previewPanel' onClick={() => this.props.parent.onClickPreview(this.props.id)}>
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
                                title={t.type}
                                className={"iconfont icon-"+t.icon+" btnicon"}
                                onClick={(e) => this.props.parent[onClickFunction](e, this.props.id)}></span>
                        )
                    })}
                    </div>
                </div>
            </div>
        );
    }
}