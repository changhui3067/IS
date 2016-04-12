/**
 * Created by freyjachang on 4/12/16.
 */
import React from "react";
import "./../css/tileGroup.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

class TileGroup extends React.Component{


}

TileGroup.propTypes = {
    unreadCount: React.PropTypes.number,
    title: React.PropTypes.string,
    background: React.PropTypes.string,
    icon: React.PropTypes.string,
    size: React.PropTypes.number,
    name: React.PropTypes.string,
    type: React.PropTypes.string,
}

TileGroup.defaultProps = {
    unreadCount: 5,
    title: 'Meet your team',
    background: '#fff',
    icon: '',
    size: 1,
    name: 'Freyja',
    type: ''
}

export default TileGroup;