import React from "react";
import "./../../css/profile.scss";
import ReactDOM from "react-dom";
import $ from "jquery";
import "./../../bootstrap/css/bootstrap.css"
import Bootstrap from "Bootstrap"

export default class Profile extends React.Component{

    render() {
        return (
            <div id="profileContent">
                <div id="controlPanel">
                    <button id="btnEdit" className="btn btn-primary btn-user" onClick={this.props.onClickEdit}>Edit</button>
                    <button id="btnSave" className="btn btn-primary btn-user nodisplay" onClick={this.props.onClickSave}>Save</button>
                    <button id="btnCancel" className="btn btn-primary btn-user nodisplay" onClick={this.props.onClickCancel}>Cancel</button>
                </div>
                <div id="infoPanel">
                    <table>
                        <tbody>
                            <tr>
                                <td className="alignright"><label>Fullname:</label></td>
                                <td><input type="text" name='name' defaultValue={this.props.empInfo.name} readOnly='true'/></td>
                            </tr>
                            <tr>
                                <td className="alignright"><label>Email:</label></td>
                                <td><input type="text" name='email' value={this.props.empInfo.email} readOnly='true'/></td>
                            </tr>
                            <tr>
                                <td className="alignright"><label>Phone number:</label></td>
                                <td><input type="text" name='phonenumber' value={this.props.empInfo.phonenumber} readOnly='true'/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}