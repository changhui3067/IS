/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";

var Tile = React.createClass({
    getInitialState: function(){
        return { unreadCount: 5,
            title: 'Meet your team',
            background: '#fff',
            icon: '',
            size: 1,
            name: 'Freyja',
            type: ''
        };
    },

    handleTileClick: function(e){
        //this.setState({companyId: e.target.value});
        console.log("tile click: ", e);
    },


    render: function () {
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
});

ReactDOM.render(
    <Tile/>,
    document.getElementById("content")
);