import React from "react";
import "./../../css/subConfig.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import "./../../bootstrap/css/bootstrap.css"
import Bootstrap from "Bootstrap"
import { Switch } from 'antd';

export default class SubConfig extends React.Component{

    render() {
        console.log(this.props)

        return (
            <div id="subconfigContent">
                <div className="titlePart">
                    <span>{this.props.name}</span>
                </div>
                <div className="contentPart">
                <div className="left">
                    <ul>
                    {this.props.events.map((t, index) => {
                        let leftEvent = 'events' + ((this.props.filter === t) ? ' selected' : '')
                        return (<li key={index} className={leftEvent} onClick={this.props.onEventTabClicked} name={t}>{t}</li>)
                        })}
                    </ul>
                </div>
                <div className="right">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Subscriber</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                    {this.props.subs.map((item, index) => {
                        return (
                            <tr key={index}>
                                <td><div className="subName">{item.name}</div>
                                    <div className="subDesc">{item.desc}</div>
                                </td>
                                <td>
                                    <Switch
                                        checkedChildren="On"
                                        unCheckedChildren="Off"
                                        checked={item.ison}
                                        onChange={() => this.props.onSwitchChanged(item.name)}
                                    />
                                </td>
                            </tr>
                        )
                    })}
                        </tbody>
                    </table>
                </div>
            </div>
            </div>
        )
    }
}