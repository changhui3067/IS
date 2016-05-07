import React from "react";
import "./../../css/commonpanel.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import "./../../css/iconfont.css";
import "./../../bootstrap/css/bootstrap.css";
import Bootstrap from "Bootstrap";
import Previewpanel from './previewpanel';

class TilePanel extends React.Component{
    render() {
        let noDisplay = {
            display: "none"
        }
        return (
            <div>
                <div className="tabsPart">
                    {this.props.tabs.map((tab, index) => {
                        let cssclass = 'tab ' + ((this.props.filter === tab.filter) ? 'selected' : '')
                        return (<div key={index} className={cssclass} onClick={this.props.onClickTab} name={tab.filter}>{tab.name}</div>)
                    })}
                </div>
                <div className="contentPart">
                    <div className="toolPanel" style={ (this.props.canadd === 'false' ? noDisplay : {})}>
                        <span className={"iconfont icon-anonymous-iconfont1 toolIconSize"} onClick={this.props.onClickAdd}></span>
                    </div>
                    {this.props.list.map(( preview, index) => {
                        return (<Previewpanel key={index} parent={this.props} {...preview}/>)
                    })}
                </div>
            </div>
        )
    }
}

class ListPanel extends React.Component{
    render() {
        return (
            <div className="contentPart">
            <div className="toolPanel">
                <span className={"iconfont icon-anonymous-iconfont1 toolIconSize"} onClick={this.props.onClickAdd}></span>
            </div>
            <table className="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Photo</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {this.props.list.map((item, index) => {
                        return (
                            <tr key={index}>
                                <td>{item.userid}</td>
                                <td>{item.username}</td>
                                <td>{item.userphoto}</td>
                                <td>
                                    <a title="Edit">
                                        <span className={"iconfont icon-bianji toolIconSize"}
                                            onClick={() => this.props.onClickEdit(item.userid)}></span>
                                    </a>
                                    <a title="Delete">
                                        <span className={"iconfont icon-shanchu1 toolIconSize"}
                                            onClick={() => this.props.onClickDelete(item.userid)}></span>
                                    </a>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
            </div>
        )
    }
}

export default class Commonpanel extends React.Component{

    render() {
        return (
            <div>
                <div className="titlePart">
                    <span>{this.props.name}</span>
                </div>
                {(() => {
                    switch (this.props.type) {
                        case 'tilelist': return <TilePanel {...this.props}/>;
                        case 'list': return <ListPanel {...this.props}/>;
                        default: return <div></div>;
                    }
                })()}
            </div>
        );
    }
}