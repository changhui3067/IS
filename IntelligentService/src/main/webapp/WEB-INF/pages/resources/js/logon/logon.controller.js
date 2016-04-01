/**
 * Created by freyjachang on 3/15/16.
 */
sap.ui.define([
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/json/JSONModel'
], function(Controller, JSONModel){
    'use strict';
    var logonController = Controller.extend('sap.ui.si.js.logon.logon',{

        onInit: function(){
            //setViewModel
            //var sPath = jQuery.sap.getModulePath('sap.ui.si.js.home', '/data.json');
            var oData = {
                "TileCollection" : [
                    {
                        "icon" : "hint",
                        "type" : "Monitor",
                        "title" : "Tiles: a modern UI design"
                    }
                ]
            };
            var oModel = new JSONModel(oData);
            //this.getView().setModel(oModel);
            this.getView().bindElement("/");

        },

        onLogin: function(){
            var _company = this.getView().byId("CompanyId").getValue();
            var _username = this.getView().byId("UserName").getValue();
            var _password = this.getView().byId("Password").getValue();

            var logonData = {
                company: _company,
                username: _username,
                password: _password
            };

            $.ajax({
                type: "POST",
                url: "/login",
                //dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(logonData),
                success: function(data){
                    console.log(data);
                },
                error: function(data){
                    console.log("ajax onLogin error:" + data);
                }
            });

        }
    });

    return logonController;
});
