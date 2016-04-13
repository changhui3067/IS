'use strict';

sap.ui.define('sap/sf/sef/eventMonitor/EventsRaised.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ], function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventMonitor.EventsRaised', {
      onInit: function () {
        this.getEventRaised(['WEEK']);
      },

      changeDuration: function (oDuration) {
        this.getEventRaised([oDuration.getKey()]);
      },

      getEventRaised: function (params) {
        var oView = this.getView();
        oView.setLoading(true);

        surj.util.DeferredUtil.createDeferred({
          type: 'ajaxService',
          serviceName: 'sefXMController',
          serviceMethod: 'getEventStats',
          arguments: params
        }).done(function (oResponse) {
          if (oResponse.length === 0) {
            oView.renderNoDataIndicator();
          } else {
            oView.setModel(new sap.ui.model.json.JSONModel({events: oResponse}));
            oView.renderChart();
          }
        }).fail(function (errMsg) {
          Utils.showErrorDialog(errMsg);
        }).always(function () {
          oView.setLoading(false);
        });
      }
    });
  });
