import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import Header from '../components/header';

const mapStateToProps = (state, ownProps) => {
    console.log("map state to props: ", state);
    return {
        selectedMenu: (state.selectedMenu || "Homepage"),
        usertype: 1
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            console.log("click dispatch: handleMenuClick");  
            dispatch(handleMenuClick(menuItem))
        }
    };
}

const HeaderMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Header)



export default HeaderMap;