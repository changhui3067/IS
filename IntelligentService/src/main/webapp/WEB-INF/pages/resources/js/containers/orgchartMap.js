/**
 * Created by freyjachang on 4/23/16.
 */
import { connect } from 'react-redux';
import { handleMenuClick } from '../actions/headerAction';
import {getManagerInfo, getCoworkersInfoList, getSubordinatesInfoList} from '../reducers/employee'
import Orgchart from '../components/orgchart';

const mapStateToProps = (state, ownProps) => {
    console.log("map state to props: ", state);
    return {
        manager: getManagerInfo(state),
        coworkers: getCoworkersInfoList(state),
        subordinates:getSubordinatesInfoList(state)
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClick: (menuItem) => {
            console.log("click dispatch: handleEmployeeClicked");
        }
    };
}

const OrgchartMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(Orgchart)

export default OrgchartMap;
