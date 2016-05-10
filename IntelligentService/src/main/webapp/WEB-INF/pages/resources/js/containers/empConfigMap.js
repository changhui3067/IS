/**
 * Created by freyjachang on 4/24/16.
 */
import { connect } from 'react-redux';
import React, {Component} from 'react';
import EmpConfig from '../components/empconfig';
import $ from "jquery";
import { handleEmpConfigAddClick, handleEmpConfigSetDialogShow, handleEmpConfigDelete, getEmpConfig} from '../actions/empConfigAction';

class EmpConfigMapper extends Component {
    constructor(props){
        super(props)
    }

    componentDidMount() {
        getEmpConfig()
    }

    render() {
        return (
            <EmpConfig {...this.props}/>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    if($.isEmptyObject(state.empConfig)) {
        getInitialState(state.empConfig)
    }
    console.log(state.empConfig.list)
    return {
        name: 'Employee Config',
        dialog: state.empConfig.dialog,
        list: state.empConfig.list
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onClickDelete: (id) =>{
            dispatch(handleEmpConfigDelete(id))
        },
        onClickEdit: (id) => {
            dispatch(handleEmpConfigSetDialogShow(true, id))
        },
        onClickAdd: () => {
            dispatch(handleEmpConfigSetDialogShow(true, -1))
        },
        onClickSave: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleEmpConfigSetDialogShow(false, id))
        },
        onClickCancel: (id) => {
            $('#dialogForm').resetForm();
            dispatch(handleEmpConfigSetDialogShow(false, id))
        }
    };
}

const empConfigMap = connect(
    mapStateToProps,
    mapDispatchToProps
)(EmpConfigMapper)

export default empConfigMap;