'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Monitoring.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ], function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.Monitoring', {
      onInit: function () {
        sap.sf.sef.eventDetail.eventBus.subscribe('sap.sf.sef.eventDetail', 'loadMonitoring', this.onLoad, this);
      },

      onLoad: function (sChannelId, sEventName, oParams) {
        this.eventType = oParams.eventType;
        this.getView().getContent()[0].getContent()[2].setSelectedKey("WEEK");
        this.getEventRaised([this.eventType, 'WEEK']);
      },

      changeDuration: function (oDuration) {
        this.getEventRaised([this.eventType, oDuration.getKey()]);
      },

      getEventRaised: function (params) {
        var oView = this.getView();
        oView.setLoading(true);

        surj.util.DeferredUtil.createDeferred({
          type: 'ajaxService',
          serviceName: 'sefXMController',
          serviceMethod: 'getEventGraphStats',
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