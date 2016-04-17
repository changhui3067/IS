/**
 * Created by freyjachang on 4/11/16.
 */
import React from "react";
import "./../../css/tile.scss";
import "./../../css/iconfont.css";
import ReactDOM from "react-dom";
import $ from "jquery";
import {Link} from "react-router";

export default class Tile extends React.Component{
    constructor(props){
        console.log("aaaaaa");
        super(props);
        this.props = {
            title: props.title,
            background: props.background,
            icon: props.icon,
            type: props.type,
            url: props.url
        };
    }

    handleTileClick(e){
        //this.setState({companyId: e.target.value});
        console.log("tile click: ", e);
    }

    //employee info tile
    _renderType0(){
        let logoImage = new Image();
        logoImage.src = require("./../../img/" + this.props.background);
        var background = {
            backgroundImage: 'url("'+ logoImage.src+'")'
        }

        return (
            <Link to={this.props.url}>
            <div className="tileBlock smallsize" style={background} onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="helloMsg"><p>Hello, Freyja</p></div>
                </div>
            </div>
            </Link>
        );
    }

    //common tile
    _renderType1(){
        return (
            <Link to={this.props.url}>
            <div className="tileBlock smallsize" onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="tileTitle title"><h3>{this.props.title}</h3></div>
                    <div className="tileIcon">
                        <span className={"iconfont icon-"+ this.props.icon +" tileIconSize"}></span>
                    </div>
                </div>
            </div>
            </Link>
        );
    }

    //size two tile
    _renderType2(){
        let logoImage = new Image();
        logoImage.src = require("./../../img/" + this.props.background);
        var background = {
            backgroundImage: 'url("'+ logoImage.src+'")'
        }
        return (
            <Link to={this.props.url}>
            <div className="tileBlock bigsize" style={background} onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="helloMsg"><p>Hot Jobs In Greate China</p></div>
                </div>
            </div>
            </Link>
        );
    }
    render() {
        switch ( this.props.type){
        case '0': return (this._renderType0());
        case '1': return (this._renderType1());
        case '2': return (this._renderType2());
        default: return (this._renderType1());
        }
    }


}

Tile.propTypes = {
    unreadCount: React.PropTypes.number,
    title: React.PropTypes.string,
    background: React.PropTypes.string,
    icon: React.PropTypes.string,
    type: React.PropTypes.string,
    url: React.PropTypes.string
}

Tile.defaultProps = {
    unreadCount: 5,
    title: 'Meet your team',
    background: '#fff',
    icon: '',
    type: '0',
    url: ''
}