/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../../css/index.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import { Router, Route, Link, IndexRoute,hashHistory} from 'react-router';
import Header from "./header";
import Footer from "./footer";
import Homepage from "./homepage";
import Orgchart from "./orgchart";
import Profile from "./profile";
import Courses from "./courses";
import Hotjobs from "./hotjobs";
import EmpConfig from "./empconfig";
import JobConfig from "./jobconfig";
import CourseConfig from "./courseconfig";

export class Index extends React.Component{

    constructor(props){
        super(props);
    }

    render() {
        return (
            <div>
                <Header />
                <div className="indexcontener">
                    <div className="wrap">
                        <div id="content">{this.props.children}</div>
                    </div>
                </div>
                <Footer />
            </div>
        );
    }
}

ReactDOM.render(
    <Router history={hashHistory}>
        <Route name="app" path="/" component={Index} >
            <IndexRoute component={Homepage} />
            <Route path="/homepage" component={Homepage} />
            <Route path="/orgchart" component={Orgchart} />
            <Route path="/profile" component={Profile} />
            <Route path="/courses" component={Courses} />
            <Route path="/hotjobs" component={Hotjobs} />
            <Route path="/empconfig" component={EmpConfig} />
            <Route path="/jobconfig" component={JobConfig} />
            <Route path="/courseconfig" component={CourseConfig} />
        </Route>
    </Router>,
    document.getElementById("mainpage")
);