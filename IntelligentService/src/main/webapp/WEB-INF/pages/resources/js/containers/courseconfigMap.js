/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import CourseConfig from '../components/courseconfig';
import $ from "jquery";
import { handleCourseConfigTabClick, handleCourseConfigDelete, handleCourseConfigSetDialogShow} from '../actions/courseConfigAction';
import {getInitialState} from '../reducers/courseConfig'

class CourseConfigMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getCourseConfig()
    }

    render() {
        return (
            <CourseConfig {...this.props}/>
        )
    }
}

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
    return {
        name: 'Course Config',
        filter: state.courseConfig.filter,
        tabs: state.courseConfig.tabs,
        dialog: state.courseConfig.dialog,
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
        onClickDelete: (e, id) => {
            e.stopPropagation()
            dispatch(handleCourseConfigDelete(id))
        },
        onClickAdd: () => {
            dispatch(handleCourseConfigSetDialogShow(true, -1))
        },
        onClickPreview: (id) => {
            console.log(id)
            dispatch(handleCourseConfigSetDialogShow(true, id))
        },
        onClickSave: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleCourseConfigSetDialogShow(false, id))
        },
        onClickCancel: (id) => {
            console.log(id)
            $('#dialogForm').resetForm();
            dispatch(handleCourseConfigSetDialogShow(false, id))
        }
    };
}

const CourseConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(CourseConfigMapper)

export default CourseConfigMap;