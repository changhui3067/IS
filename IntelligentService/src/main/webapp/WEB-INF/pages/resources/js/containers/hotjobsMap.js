/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import Hotjobs from '../components/hotjobs';
import $ from "jquery";
import { handleHotjobsTabClick } from '../actions/hotjobsAction';
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
    console.log(state)
    return {
        name: 'HotJobs',
        filter: state.hotjobs.filter,
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

        },
        onClickDelete: () =>{

        },
        onClickSave: () => {
            
        },
        onClickCancel: () => {
            
        }
    };
}

const HotjobsMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Hotjobs)

export default HotjobsMap;