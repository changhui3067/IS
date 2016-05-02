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
import HotjobsMap from "./../containers/hotjobsMap";
import EmpConfigMap from "./../containers/empConfigMap";
import JobConfigMap from "./../containers/jobconfigMap";
import CourseConfigMap from "./../containers/courseconfigMap";
import SubConfigMap from "./../containers/subconfigMap";
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
                <Route path="/hotjobs" component={HotjobsMap} />
                <Route path="/empconfig" component={EmpConfigMap} />
                <Route path="/jobconfig" component={JobConfigMap} />
                <Route path="/courseconfig" component={CourseConfigMap} />
                <Route path="/subconfig" component={SubConfigMap} />
            </Route>
        </Router>
    </Provider>,
    document.getElementById("mainpage")
);