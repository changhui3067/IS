import { connect } from 'react-redux';
import React, {Component} from 'react';
import { handleMenuClick } from '../actions/headerAction';
import Header from '../components/header';
import {getNotification} from '../actions/notificationAction'

class HeaderMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getNotification()
    }

    render() {
        return (
            <Header {...this.props}/>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.notification.list)) {
        getNotification()
    }

    return {
        unreadCount: state.notification.unreadcount,
        selectedMenu: (state.headerMenu.selectedMenu || "Homepage"),
        usertype: 1
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            dispatch(handleMenuClick(menuItem))
        }
    };
}

const HeaderMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(HeaderMapper)

export default HeaderMap;