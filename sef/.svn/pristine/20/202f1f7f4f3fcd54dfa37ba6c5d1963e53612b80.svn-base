'use strict';

sap.ui.define('sap/sf/sef/eventDashboard/EventDashboard.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ],
  function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventDashboard.EventDashboard', {
      onInit: function () {
        var oView = this.getView();

        this.router = sap.ui.core.UIComponent.getRouterFor(this);
        oView.setLoading(true);
        surj.util.DeferredUtil.createDeferred({
          type: 'ODataService',
          serviceName: 'getEventMetaData'
        }).done(function (oResponse) {
          oView.setModel(new sap.ui.model.json.JSONModel(oResponse.SEFEventsList));
          oView.renderTable();
        }).fail(function (errMsg) {
          Utils.showErrorDialog(errMsg);
        }).always(function () {
          oView.setLoading(false);
        });
      },
      
      toEventMonitor: function () {
        this.router.navTo('eventMonitor');
      },

      toEventDetail: function (eventType) {
        this.router.navTo('eventDetail', {eventType: eventType});
      }
    });
  });
