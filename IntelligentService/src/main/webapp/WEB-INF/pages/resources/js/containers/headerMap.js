import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import Header from '../components/header';

const mapStateToProps = (state, ownProps) => {
    return {
        unreadCount: 1,
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