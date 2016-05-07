import {Modal, Form, Select, Input, Button} from 'antd';
import React from "react";
import "./../../css/courses.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import './../../lib/jquery.form.js';
import './../../css/createBannerDialog.scss';
import './../../css/antd.css';

const FormItem = Form.Item;

var DialogForm = React.createClass({
    getInitialState: function() {
        return {
        formMethod: "post"
        };
    },
    onInputChange: function(e) {
        console.log($(e.target).attr('name'))
        this.setState({
            'input1': e.target.value
        })
    },

    render: function() {
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };
        return(
            <Form horizontal className="dialogForm" id="dialogForm" method={this.state.formMethod}>
                <FormItem {...formItemLayout}
                    label={"form item1"+"："}>
                    <p className="ant-form-text" id="input1" name="input1">new job</p>
                </FormItem>
                <FormItem {...formItemLayout}
                    label={"form item2"+"："}>
                    <Input id="input2" name="input2" />
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
                  title="dialog" onOk={this.props.onClickSave} onCancel={this.props.onClickCancel}
                  footer={[
                    <Button key="back" type="ghost" size="large" onClick={() => this.props.onClickCancel(this.props.dialog.id)}>Cancel</Button>,
                    <Button key="submit" type="primary" size="large" onClick={() => this.props.onClickSave(this.props.dialog.id)}>Save</Button>
                  ]}>
                  <DialogForm ref="dialogForm" {...this.props.dialog}/>
                </Modal>
            

        );
    }
}