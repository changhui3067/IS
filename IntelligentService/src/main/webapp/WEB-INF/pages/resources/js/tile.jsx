/**
 * Created by freyjachang on 4/11/16.
 */
import React from "react";
import "./../css/tile.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

class Tile extends React.Component{
    constructor(props){
        super(props);
        this.props = {
            unreadCount: props.unreadCount,
            title: props.title,
            background: props.background,
            icon: props.icon,
            type: props.type
        };
    }

    handleTileClick(e){
        //this.setState({companyId: e.target.value});
        console.log("tile click: ", e);
    }

    _renderType0(){
        return (
            <div className="tileBlock" onPress={this.handleTileClick}>
                <div className="tileTitle">
                    <div className="title">
                        <h3>{this.props.title}</h3>
                    </div>
                    <div className="unreadCount">
                        {this.props.unreadCount}
                    </div>
                </div>
                <div className="helloMsg">
                    <p>hello, Freyja</p>
                </div>
            </div>
        );
    }

    _renderType1(){
        return (
            <div className="tileBlock" onPress={this.handleTileClick}>
                <div className="tileTitle">
                    <div className="title">
                        <h3>{this.props.title}</h3>
                    </div>
                    <div className="unreadCount">
                        {this.props.unreadCount}
                    </div>
                </div>
                <div className="helloMsg">
                    <p>hello, Freyja</p>
                </div>
            </div>
        );
    }

    _renderType2(){
        return (
            <div className="tileBlock" onPress={this.handleTileClick}>
                <div className="tileTitle">
                    <div className="title">
                    </div>
                    <div className="unreadCount">
                        {this.props.unreadCount}
                    </div>
                </div>
                <div className="helloMsg">
                    <p>hello, Freyja</p>
                </div>
            </div>
        );
    }
    render() {
        return (
            <div className="tileBlock" onPress={this.handleTileClick}>
                <div className="tileTitle">
                    <div className="title">
                        <h3>{this.props.title}</h3>
                    </div>
                    <div className="unreadCount">
                        {this.props.unreadCount}
                    </div>
                </div>
                <div className="helloMsg">
                    <p>hello, Freyja</p>
                </div>
            </div>
        );
    }


}

Tile.propTypes = {
    unreadCount: React.PropTypes.number,
    title: React.PropTypes.string,
    background: React.PropTypes.string,
    icon: React.PropTypes.string,
    size: React.PropTypes.number,
    type: React.PropTypes.string,
}

Tile.defaultProps = {
    unreadCount: 5,
    title: 'Meet your team',
    background: '#fff',
    icon: '',
    type: ''
}

export default Tile;