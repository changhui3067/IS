/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../css/homepage.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import Header from "./header.jsx";
import Footer from "./footer.jsx";
import Tile from "./tile.jsx";

var Homepage = React.createClass({
    render: function(){
        return (
            <div id="HomepageContent">
                <Header />
                <div className="tileContainer">
                    <div className="Employee">
                    </div>
                    <Tile />
                </div>
                <Footer />
            </div>
        );
    }
});

ReactDOM.render(
    <Homepage />,
    document.getElementById("homepage")
);