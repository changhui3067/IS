/**
 * Created by freyjachang on 3/15/16.
 */
sap.ui.define([
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/json/JSONModel'
], function(Controller, JSONModel){
    'use strict';
    var homepageController = Controller.extend('sap.ui.si.js.home.homepage',{
        onShowHello : function () {
            alert('Hello World');
        },

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
            this.getView().setModel(oModel);
        }
    });

    return homepageController;
});
