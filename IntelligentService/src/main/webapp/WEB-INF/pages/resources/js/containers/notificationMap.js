/**
 * Created by freyjachang on 5/3/16.
 */
/**
 * Created by freyjachang on 4/28/16.
 */
import { connect } from 'react-redux';
import Notification from '../components/notification';
import $ from "jquery";
import { handleNotiDeleteClick, handleNotiReadClick } from '../actions/notificationAction';
import {getInitialState} from '../reducers/notification'

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.notification)) {
        getInitialState(state.notification)
    }
    console.log('mapstatetoprops', state.notification.list)
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
)(Notification)

export default NotificationMap;