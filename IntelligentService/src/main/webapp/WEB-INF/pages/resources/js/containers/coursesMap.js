/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import Courses from '../components/courses';
import $ from "jquery";
import { handleCoursesTabClick, handleCoursesFinishedClick, handleCoursesSetDialogShow, getCourses} from '../actions/coursesAction';

class CoursesMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getCourses()
    }

    render() {
        return (
            <Courses {...this.props}/>
        )
    }
}

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
    return {
        name: 'Courses',
        filter: state.courses.filter,
        tabs: state.courses.tabs,
        dialog: state.courses.dialog,
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
        onClickFinished: (e, id) => {
            e.stopPropagation()
            dispatch(handleCoursesFinishedClick(id))
        },
        onClickPreview: (e, id) => {
            dispatch(handleCoursesSetDialogShow(true, id))
        },
        onClickSave: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleCoursesSetDialogShow(false, id))
        },
        onClickCancel: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleCoursesSetDialogShow(false, id))
        },
        onClickDelete: () =>{

        }
    };
}

const CoursesMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(CoursesMapper)

export default CoursesMap;