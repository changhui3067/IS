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
        this.state = {
            unreadCount: props.unreadCount,
            title: props.title,
            background: props.background,
            icon: props.icon,
            size: props.size,
            name: props.name,
            type: props.type
        }
    }

    handleTileClick(e){
        //this.setState({companyId: e.target.value});
        console.log("tile click: ", e);
    }

    render() {
        return (
            <div className="tileBlock" onPress={this.handleTileClick}>
                <div className="tileTitle">
                    <div className="title">
                        <h3>{this.state.title}</h3>
                    </div>
                    <div className="unreadCount">
                        {this.state.unreadCount}
                    </div>
                </div>
                <div className="helloMsg">
                    <p>hello, {this.state.name}</p>
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
    name: React.PropTypes.string,
    type: React.PropTypes.string,
}

Tile.defaultProps = {
    unreadCount: 5,
    title: 'Meet your team',
    background: '#fff',
    icon: '',
    size: 1,
    name: 'Freyja',
    type: ''
}

export default Tile;