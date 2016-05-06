/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import Courses from '../components/courses';
import $ from "jquery";
import { handleCoursesTabClick, handleCoursesFinishedClick } from '../actions/coursesAction';
import {getInitialState} from '../reducers/courses'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'todo': return list.filter(t => !t.finished);
        case 'finished': return list.filter(t => t.finished);
        default: return list.filter( t => !t.finished);
    }
}

const getActions = (filter) => {
    if(filter === 'todo'){
        return [{
            type: 'Finished',
            icon:'gouhao'
        }];
    }
    return [];
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.courses)) {
        getInitialState(state.courses)
    }
    
    return {
        name: 'Courses',
        filter: state.courses.filter,
        tabs: state.courses.tabs,
        list: getVisibleList(state.courses.list, state.courses.filter),
        actions: getActions(state.courses.filter)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickTab: (e) => {
            console.log($(e.target).attr('name'))
            $(e.target).addClass('selected')
            $(e.target).siblings().removeClass('selected')
            dispatch(handleCoursesTabClick($(e.target).attr('name')))
        },
        onClickFinished: (id) => {
            console.log("finsihed click")
            dispatch(handleCoursesFinishedClick(id))
        },
        onClickDelete: () =>{

        }
    };
}

const CoursesMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Courses)

export default CoursesMap;