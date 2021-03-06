/**
 * Created by freyjachang on 4/12/16.
 */
import React from "react";
import "./../../css/tileGroup.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import TileMap from "./../containers/tileMap";

class TileGroup extends React.Component{

    constructor(props) {
        super(props);
        this.props = {
            title: props.title,
            tileList: props.tileList
        }
    }

    render() {
        return (
            <div className="tileGroupContent">
                <div className="tileGroupTitle">
                    <h1>{this.props.title}</h1>
                </div>
                <div className="tileGroupList">
                    {this.props.tileList.map((tile, index) => {
                        return (<TileMap key={index} {...tile}/>);
                    })}
                </div>
            </div>
        );
    }

}

TileGroup.propTypes = {
    title: React.PropTypes.string,
    tileList: React.PropTypes.array
}

TileGroup.defaultProps = {
    title: 'Employee',
    tileList: []
}

export default TileGroup;