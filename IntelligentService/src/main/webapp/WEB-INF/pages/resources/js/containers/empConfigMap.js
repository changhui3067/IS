/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import EmpConfig from '../components/empconfig';
import $ from "jquery";
import { handleEmpConfigAddClick } from '../actions/empConfigAction';
import {getInitialState} from '../reducers/empConfig'

const getVisibleList = (list, filter) => {
    switch (filter) {
        case 'todo': return list.filter(t => !t.finished);
        case 'finished': return list.filter(t => t.finished);
        default: return list.filter( t => !t.finished);
    }
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.empConfig)) {
        getInitialState(state.empConfig)
    }
    
    return {
        name: 'Employee Config',
        list: state.empConfig.list
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickAdd: () => {
            dispatch(handleEmpConfigTabClick())
        },
        onClickDelete: () =>{

        },
        onClickSave: () => {
            
        },
        onClickCancel: () => {
            
        }
    };
}

const empConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(EmpConfig)

export default empConfigMap;