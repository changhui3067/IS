import {Modal, Form, Select, Input, Button} from 'antd';
import React from "react";
import "./../../css/courses.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import './../../lib/jquery.form.js';
import './../../css/createBannerDialog.scss';

const FormItem = Form.Item;

var DialogForm = React.createClass({
    getInitialState: function() {
        return {
        formMethod: "post",
        };
    },
    onInputChange: function(e) {
        console.log($(e.target).attr('name'))
        this.setState({
            'input1': e.target.value
        })
    },

    render: function() {

        return(
            <Form horizontal className="dialogForm" id="dialogForm" method={this.state.formMethod}>
                <FormItem label="formitem">
                    <Input id="input1" name="input1" onChange={this.onInputChange}/>
                </FormItem>
            </Form>
        )
    }
});

export default class Dialog extends React.Component{


    handleOk() {
        this.resetDialog();
    }

    handleCancel() {
        this.resetDialog();
    }

    resetDialog() {
        console.log('resetDialog')
        $('#dialogForm').resetForm();
        
        
    }

    render() {
        console.log(this.props)
        return (
            
                <Modal ref="modal"
                  visible={this.props.dialog.visible}
                  title="dialog" onOk={this.handleOk} onCancel={this.handleCancel}
                  footer={[
                    <Button key="back" type="ghost" size="large" onClick={this.props.onClickCancel}>Cancel</Button>,
                    <Button key="submit" type="primary" size="large" onClick={this.props.onClickSave}>Save</Button>
                  ]}>
                  <DialogForm ref="dialogForm" {...this.props.dialog}/>
                </Modal>
            

        );
    }
}