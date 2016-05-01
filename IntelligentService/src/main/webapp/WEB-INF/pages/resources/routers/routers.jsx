import { Router, Route, Link, IndexRoute,hashHistory} from 'react-router';
let React = require('react');
let Index = require('./../js/components/index');


var routes = (
    <Route name="app" path="/" component={Index} >
        <IndexRoute component={Index} />
        <Route nmae="homepage" path="/homepage" component={Homepage}></Route>
        <Route name="orgchart" path="/orgchart" component={Orgchart}></Route>
        <Route name="profile" path="/profile" component={Profile}></Route>
        <Route name="courses" path="/courses" component={Courses}></Route>
        <Route name="hotjobs" path="/hotjobs" component={Hotjobs}></Route>
        <Route name="empconfig" path="/empconfig" component={EmpConfig}></Route>
        <Route name="jobconfig" path="/jobconfig" component={JobConfig}></Route>
        <Route name="courseconfig" path="/courseconfig" component={CourseConfig}></Route>
    </Route>
);

module.exports = routes;