'use strict';

sap.ui.define('sap/sf/sef/Utils', [], function () {
  var bizxResourceBundle = new surj.core.BizXResourceBundle();

  var utils = {};

  utils.getText = function () {
    return bizxResourceBundle.getText.apply(bizxResourceBundle, arguments);
  };

  utils.showErrorDialog = function (errMsg) {
    var dialog = new sap.m.Dialog({
      title: utils.getText('ERROR'),
      type: 'Message',
      state: 'Error',
      content: new sap.m.Text({
        text: errMsg
      }),
      beginButton: new sap.m.Button({
        text: utils.getText('COMMON_Close'),
        press: function () {
          dialog.close();
        }
      }),
      afterClose: function () {
        dialog.destroy();
      }
    });

    dialog.open();
  };

  return utils;
});
