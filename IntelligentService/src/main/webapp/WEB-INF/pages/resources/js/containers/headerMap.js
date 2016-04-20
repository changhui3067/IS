import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import Header from '../components/header';

const mapStateToProps = (state, ownProps) => {
    return {
        selected: state.selectedMenu,
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