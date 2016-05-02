/**
 * Created by freyjachang on 4/28/16.
 */
import { connect } from 'react-redux';
import SubConfig from '../components/subconfig';
import $ from "jquery";
import { handleSubConfigSwitchChanged, handleSubConfigTabClick } from '../actions/subConfigAction';
import {getInitialState} from '../reducers/subConfig'

const getEvents = (state) => {
    var eventList = []
    state.map( t =>
        eventList.push(t.name)
    )
    return eventList
}

const getList = (state, filter) => {
    var subslist = []
    state.forEach( t => {
            if(t.name === filter){
                subslist =  t.subs
            }
        })
    return subslist
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.subConfig)) {
        getInitialState(state.subConfig)
    }

    return {
        name: 'Subscriber Config',
        filter: state.subConfig.filter,
        events: getEvents(state.subConfig.list),
        subs: getList(state.subConfig.list, state.subConfig.filter)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onEventTabClicked: (e) => {
            console.log(e)
            $(e.target).addClass('selected')
            $(e.target).siblings().removeClass('selected')
            dispatch( handleSubConfigTabClick($(e.target).attr('name')))
        },
        onSwitchChanged: (e) => {
            console.log(e)
            dispatch(handleSubConfigSwitchChanged(e))
        }
    };
}

const SubConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(SubConfig)

export default SubConfigMap;