/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import {getUserInfo} from '../reducers/employee'
import Profile from '../components/profile';

const mapStateToProps = (state, ownProps) => {
    console.log("map state to props: ", state);
    return {
        empInfo: getUserInfo(state)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickEdit: () => {
            //make form editable, disable edit btn, enable save&cancel btn
            $('#btnEdit').addClass('nodisplay')
            $('#btnSave').removeClass('nodisplay')
            $("#btnCancel").removeClass("nodisplay")
        },
        onClickSave: () => {
            //save data to state, save to backend, disable save&cancel btn, enable edit btn
            $('#btnEdit').removeClass('nodisplay')
            $("#btnSave").addClass("nodisplay")
            $("#btnCancel").addClass("nodisplay")
        },
        onClickCancel: () => {
            //disable save&cancel btn, enable edit btn
            $('#btnEdit').removeClass('nodisplay')
            $("#btnSave").addClass("nodisplay")
            $("#btnCancel").addClass("nodisplay")
        }
    };
}

const ProfileMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Profile)

export default ProfileMap;