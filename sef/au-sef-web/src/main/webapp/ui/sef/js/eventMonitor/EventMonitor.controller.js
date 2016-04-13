'use strict';

sap.ui.define('sap/sf/sef/eventMonitor/EventMonitor.controller.js', [
  'jquery.sap.global'
], function ($) {
  return sap.ui.controller('sap.sf.sef.eventMonitor.EventMonitor', {
    onInit: function () {
      this.router = sap.ui.core.UIComponent.getRouterFor(this);
    },

    toEventCenter: function () {
      this.router.navTo('eventCenter');
    }
  });
});
