/**
 * Created by freyjachang on 4/7/16.
 */

import React from "react";
import "./../../css/index.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import { Router, Route, Link, IndexRoute,hashHistory} from 'react-router';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import HeaderMap from "./../containers/headerMap";
import Footer from "./footer";
import Homepage from "./homepage";
import OrgchartMap from "./../containers/orgchartMap";
import ProfileMap from "./../containers/profileMap";
import CoursesMap from "./../containers/coursesMap";
import Hotjobs from "./hotjobs";
import EmpConfig from "./empconfig";
import JobConfig from "./jobconfig";
import CourseConfig from "./courseconfig";
import reducers from "./../reducers/reducers"

const store = createStore(reducers);

export class Index extends React.Component{

    constructor(props){
        super(props);
    }

    render() {
        return (
            <div>
                <HeaderMap />
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
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route name="app" path="/" component={Index} >
                <IndexRoute component={Homepage} />
                <Route path="/homepage" component={Homepage} />
                <Route path="/orgchart" component={OrgchartMap} />
                <Route path="/profile" component={ProfileMap} />
                <Route path="/courses" component={CoursesMap} />
                <Route path="/hotjobs" component={Hotjobs} />
                <Route path="/empconfig" component={EmpConfig} />
                <Route path="/jobconfig" component={JobConfig} />
                <Route path="/courseconfig" component={CourseConfig} />
            </Route>
        </Router>
    </Provider>,
    document.getElementById("mainpage")
);