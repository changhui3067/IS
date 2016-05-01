/**
 * Created by freyjachang on 4/28/16.
 */
import { connect } from 'react-redux';
import JobConfig from '../components/jobconfig';
import $ from "jquery";
import { handleJobConfigTabClick } from '../actions/jobConfigAction';
import {getInitialState} from '../reducers/jobConfig'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'open': return list.filter(t => t.open);
        case 'closed': return list.filter(t => !t.open);
        default: return list;
    }
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.jobConfig)) {
        getInitialState(state.jobConfig)
    }
    
    return {
        name: 'Job Config',
        filter: state.jobConfig.filter,
        tabs: state.jobConfig.tabs,
        list: getVisibleList(state.jobConfig.list, state.jobConfig.filter)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickTab: (e) => {
            console.log($(e.target).attr('name'))
            $(e.target).addClass('selected')
            $(e.target).siblings().removeClass('selected')
            dispatch(handleJobConfigTabClick($(e.target).attr('name')))
        },
        onClickAdd: () => {

        },
        onClickDelete: () =>{

        },
        onClickSave: () => {
            
        },
        onClickCancel: () => {
            
        }
    };
}

const JobConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(JobConfig)

export default JobConfigMap;