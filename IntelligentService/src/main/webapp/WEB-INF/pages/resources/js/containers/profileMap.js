/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import {getUserInfo} from '../reducers/employee'
import Profile from '../components/profile';
import $ from "jquery";
import { handleProfileSaveClick } from '../actions/headerAction';

const mapStateToProps = (state, ownProps) => {
    return {
        empInfo: getUserInfo(state)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickEdit: () => {
            $('#btnEdit').addClass('nodisplay')
            $('#btnSave').removeClass('nodisplay')
            $('#btnCancel').removeClass('nodisplay')
            $('#infoPanel').find('tr').each(function() {
                $(this).find('td').find('input').removeAttr('readOnly')
            })
        },
        onClickSave: () => {
            $('#btnEdit').removeClass('nodisplay')
            $('#btnSave').addClass('nodisplay')
            $('#btnCancel').addClass('nodisplay')
            $('#infoPanel').find('tr').each(function() {
                var ele = $(this).find('td').find('input')
                dispatch( handleProfileSaveClick( ele.attr('name'), ele.val()))
                ele.attr('readOnly', 'true')
            })
        },
        onClickCancel: () => {
            $('#btnEdit').removeClass('nodisplay')
            $('#btnSave').addClass('nodisplay')
            $('#btnCancel').addClass('nodisplay')
            $('#infoPanel').find('tr').each(function() {
                var ele = $(this).find('td').find('input')
                ele.val(ele.attr('value'))
                ele.attr('readOnly', 'true')
            })
        }
    };
}

const ProfileMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Profile)

export default ProfileMap;