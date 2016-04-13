'use strict';

sap.ui.define('sap/sf/sef/Formatter', [], function () {
  var formatter = {
    eventCountFormatter: function (count) {
      if(count == null) {
        return null;
      }
      var num = (count ||0).toString(), re = /\d{3}$/, result = '';
      while (re.test(num)) {
        result = RegExp.lastMatch + result;
        if (num !== RegExp.lastMatch) {
          result = ',' + result;
          num = RegExp.leftContext;
        } else {
          num = '';
          break;
        }
      }
      if (num) {
        result = num + result;
      }
      return result;
    }
  };

  return formatter;
});
