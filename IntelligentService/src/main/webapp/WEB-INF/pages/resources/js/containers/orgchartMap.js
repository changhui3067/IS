/**
 * Created by freyjachang on 4/23/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import { handleMenuClick } from '../actions/headerAction';
import {getEmployeeInfo} from './../actions/empAction';
import Orgchart from '../components/orgchart';

class OrgchartMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getEmployeeInfo()
    }

    render() {
        return (
            <Orgchart {...this.props}/>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        manager: state.employee.manager,
        coworkers: state.employee.coworkers,
        subordinates:state.employee.subordinates
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            console.log("click dispatch: handleEmployeeClicked");
        }
    };
}

const OrgchartMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(OrgchartMapper)

export default OrgchartMap;
