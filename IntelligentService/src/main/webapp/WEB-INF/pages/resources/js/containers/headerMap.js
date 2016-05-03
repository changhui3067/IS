import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import Header from '../components/header';
import {getInitialState} from '../reducers/notification'

const mapStateToProps = (state, ownProps) => {

    if($.isEmptyObject(state.notification)) {
        getInitialState(state.notification)
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
)(Header)



export default HeaderMap;