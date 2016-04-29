/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import Hotjobs from '../components/hotjobs';
import $ from "jquery";
import { handleHotjobsTabClick, handleHotjobsSetDialogShow } from '../actions/hotjobsAction';
import {getInitialState} from '../reducers/hotjobs'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'open': return list.filter(t => !t.applied);
        case 'applied': return list.filter(t => t.applied);
        default: return list.filter( t => !t.applied);
    }
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.hotjobs)) {
        getInitialState(state.hotjobs)
    }
    
    return {
        name: 'HotJobs',
        filter: state.hotjobs.filter,
        dialog: state.hotjobs.dialog,
        tabs: state.hotjobs.tabs,
        list: getVisibleList(state.hotjobs.list, state.hotjobs.filter)
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
        onClickAdd: () => {
            dispatch(handleHotjobsSetDialogShow(true))
        },
        onClickDelete: () =>{

        },
        onClickSave: () => {
            console.log('save clicked')
            $('#dialogForm').resetForm();
            dispatch(handleHotjobsSetDialogShow(false))
        },
        onClickCancel: () => {
            console.log('cancel clicked')
            $('#dialogForm').resetForm();
            dispatch(handleHotjobsSetDialogShow(false))
        }
    };
}

const HotjobsMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Hotjobs)

export default HotjobsMap;