/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import Hotjobs from '../components/hotjobs';
import $ from "jquery";
import { handleHotjobsTabClick, handleHotjobsApplyClick, handleHotjobsSetDialogShow, getHotjobs} from '../actions/hotjobsAction';

class HotjobsMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getHotjobs()
    }

    render() {
        return (
            <Hotjobs {...this.props}/>
        )
    }
}

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'open': return list.filter(t => !t.applied);
        case 'applied': return list.filter(t => t.applied);
        default: return list.filter( t => !t.applied);
    }
}

const getActions = (filter) => {
    if(filter === 'open') {
        return [{
            type: 'Apply',
            icon: 'gouhao'
        }]
    }
    return []
}

const mapStateToProps = (state, ownProps) => {
    return {
        name: 'HotJobs',
        filter: state.hotjobs.filter,
        dialog: state.hotjobs.dialog,
        tabs: state.hotjobs.tabs,
        list: getVisibleList(state.hotjobs.list, state.hotjobs.filter),
        actions: getActions(state.hotjobs.filter)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickTab: (e) => {
            console.log($(e.target).attr('name'))
            $(e.target).addClass('selected')
            $(e.target).siblings().removeClass('selected')
            dispatch(handleHotjobsTabClick($(e.target).attr('name')))
        },
        onClickPreview: (id) => {
            dispatch(handleHotjobsSetDialogShow(true, id))
        },
        onClickApply: (e, id) =>{
            e.stopPropagation()
            dispatch(handleHotjobsApplyClick(id))
        },
        onClickSave: (id) => {
            console.log('save clicked')
            $('#dialogForm').resetForm();
            dispatch(handleHotjobsSetDialogShow(false, id))
        },
        onClickCancel: (id) => {
            console.log('cancel clicked')
            $('#dialogForm').resetForm();
            dispatch(handleHotjobsSetDialogShow(false, id))
        }
    };
}

const HotjobsMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(HotjobsMapper)

export default HotjobsMap;