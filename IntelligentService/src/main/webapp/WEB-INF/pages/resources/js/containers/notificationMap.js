/**
 * Created by freyjachang on 4/28/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import Notification from '../components/notification';
import $ from "jquery";
import { handleNotiDeleteClick, handleNotiReadClick, getNotification } from '../actions/notificationAction';
import {getInitialState} from '../reducers/notification'

class NotificationMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getNotification()
    }

    render() {
        return (
            <Notification {...this.props}/>
        )
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        name: 'Notification Center',
        unreadcount: state.notification.unreadcount,
        list: state.notification.list
    }
}



const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickDelete: (itemid) => {
            console.log('delete', itemid)
            dispatch(handleNotiDeleteClick(itemid))
        },
        onClickRead: (itemid) => {
            console.log('read', itemid)
            dispatch(handleNotiReadClick(itemid))
        }
    };
}

const NotificationMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(NotificationMapper)

export default NotificationMap;