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

    //employee info tile
    _renderType0(){
        return (
            <div className="tileBlock smallsize" onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="tileTitle title"><h3>{this.props.title}</h3></div>
                    <div className="helloMsg"><p>hello, Freyja</p></div>
                </div>
            </div>


        );
    }

    //common tile
    _renderType1(){
        return (
            <div className="tileBlock smallsize" onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="tileTitle title"><h3>{this.props.title}</h3></div>
                </div>
            </div>
        );
    }

    //size two tile
    _renderType2(){
        return (
            <div className="tileBlock bigsize" onPress={this.handleTileClick}>
                <div className="unreadCount">{this.props.unreadCount}</div>
                <div className="tileContent">
                    <div className="tileTitle title"><h3>{this.props.title}</h3></div>
                    <div className="helloMsg"><p>hello, Freyja</p></div>
                </div>
            </div>
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
    size: React.PropTypes.number,
    type: React.PropTypes.string
}

Tile.defaultProps = {
    unreadCount: 5,
    title: 'Meet your team',
    background: '#fff',
    icon: '',
    type: ''
}

export default Tile;