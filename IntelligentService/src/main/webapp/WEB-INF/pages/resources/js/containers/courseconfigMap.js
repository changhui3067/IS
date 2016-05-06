/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import CourseConfig from '../components/courseconfig';
import $ from "jquery";
import { handleCourseConfigTabClick, handleCourseConfigDelete} from '../actions/courseConfigAction';
import {getInitialState} from '../reducers/courseConfig'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'default': return list.filter(t => t.default);
        case 'custom': return list.filter(t => !t.default);
        default: return list;
    }
}

const getActions = (fitler) => {
    return [{
        type: 'Delete',
        icon: 'shanchu1'
    }]
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.courseConfig)) {
        getInitialState(state.courseConfig)
    }
    
    return {
        name: 'Course Config',
        filter: state.courseConfig.filter,
        tabs: state.courseConfig.tabs,
        list: getVisibleList(state.courseConfig.list, state.courseConfig.filter),
        actions: getActions(state.courseConfig.filter)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickTab: (e) => {
            console.log($(e.target).attr('name'))
            $(e.target).addClass('selected')
            $(e.target).siblings().removeClass('selected')
            dispatch(handleCourseConfigTabClick($(e.target).attr('name')))
        },
        onClickAdd: () => {

        },
        onClickDelete: (id) =>{
            dispatch(handleCourseConfigDelete(id))
        },
        onClickSave: () => {
            
        },
        onClickCancel: () => {
            
        }
    };
}

const CourseConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(CourseConfig)

export default CourseConfigMap;