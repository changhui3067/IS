import React from "react";
import "./../../css/notification.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import "./../../bootstrap/css/bootstrap.css"
import Bootstrap from "Bootstrap"

export default class Notification extends React.Component{

    render() {
        return (
            <div id="notiContent">
                <div className="titlePart">
                    <span>{this.props.name}</span>
                </div>
                <div className="contentPart">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Notifications</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                    {this.props.list.map((item, index) => {
                        return (
                            <tr key={index} className={item.isread ? 'read' : 'unread'}>
                                <td>
                                    <div className="notiName">{item.title}</div>
                                </td>
                                <td>
                                    <a title="Mark as Read" onClick={() => this.props.onClickRead(index)}><span className={"iconfont icon-gouhao iconread"}></span></a>
                                    <a title="Delete" onClick={() => this.props.onClickDelete(index)}><span className={"iconfont icon-shanchu1 icondelete"}></span></a>
                                </td>
                            </tr>
                        )
                    })}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}