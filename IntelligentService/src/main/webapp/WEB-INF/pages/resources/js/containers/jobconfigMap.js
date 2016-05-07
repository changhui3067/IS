/**
 * Created by freyjachang on 4/28/16.
 */
import { connect } from 'react-redux';
import JobConfig from '../components/jobconfig';
import $ from "jquery";
import { handleJobConfigTabClick, handleJobConfigDelete, handleJobConfigClose, handleJobConfigDialogShow } from '../actions/jobConfigAction';
import {getInitialState} from '../reducers/jobConfig'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'open': return list.filter(t => t.open);
        case 'closed': return list.filter(t => !t.open);
        default: return list;
    }
}

const getActions = (filter) => {
    if(filter === 'open'){
        return [{
            type: 'Close',
            icon: 'gouhao'
        },{
            type: 'Delete',
            icon: 'shanchu1'
        }]
    }
    return [{
        type: 'Delete',
        icon: 'shanchu1'
    }]
}

const getAddVisible = (filter) => {
    if(filter === 'open'){
        return 'true'
    }
    return 'false'
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.jobConfig)) {
        getInitialState(state.jobConfig)
    }
    
    return {
        name: 'Job Config',
        filter: state.jobConfig.filter,
        tabs: state.jobConfig.tabs,
        dialog: state.jobConfig.dialog,
        list: getVisibleList(state.jobConfig.list, state.jobConfig.filter),
        canadd: getAddVisible(state.jobConfig.filter),
        actions: getActions(state.jobConfig.filter)
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
        onClickDelete: (e, id) => {
            e.stopPropagation()
            dispatch(handleJobConfigDelete(id))
        },
        onClickClose: (e, id) => {
            e.stopPropagation()
            dispatch(handleJobConfigClose(id))
        },
        onClickAdd: () => {
            dispatch(handleJobConfigDialogShow(true, -1))
        },
        onClickPreview: (id) => {
            dispatch(handleJobConfigDialogShow(true, id))
        },
        onClickSave: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleJobConfigDialogShow(false, id))
        },
        onClickCancel: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleJobConfigDialogShow(false, id))
        }
    };
}

const JobConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(JobConfig)

export default JobConfigMap;