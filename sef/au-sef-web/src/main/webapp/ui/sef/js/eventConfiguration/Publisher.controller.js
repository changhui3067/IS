'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Publisher.controller.js',
  [
    'jquery.sap.global'
  ],
  function ($) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.Publisher', {
      onInit: function () {},

      showRulePopup: function () {
        if (!this._ruleDialog) {
          this._ruleDialog = this.getView().createRuleDialog();
        }

        this.getView().addDependent(this._ruleDialog);
        this._ruleDialog.open();
      },

      showEndPointPopup: function () {
        var url = '/xi/ui/alertgui/pages/alertSubscriptionMgmt.xhtml?';
        window.open(url, null, 'height=500,width=800')
      }
    });
  });
