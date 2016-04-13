//* Page level VC */
//! include /ui/juic/js/core/component-surj.js
//! include /ui/juic/js/surj/Overlay.js 
//! include /ui/surj/js/util/DeferredUtil.js
//! include /ui/surj/js/common/DeferredContent.js

//! include /ui/sef/js/i18n.js
//! include /ui/sef/js/Formatter.js
//! include /ui/sef/js/Utils.js

/* CSS Files */
//! include /ui/sef/css/sefHomepage.css

/**
 * This is the bootstrap javascript that will initialize the page, and have a
 * bunch of includes for various portlets.
 *
 * @name sefHomepage.js
 */
jQuery(function ($) {
  /*
   * The global page model
   *
   * TODO: This should probably be made common.
   *
   * Example: Getting companyId
   * sap.ui.getCore().getModel('pageHeader').getProperty('/companyId');
   */
  var oModel = sap.ui.getCore().getModel('pageHeader');
  if (!oModel) {
    oModel = new sap.ui.model.json.JSONModel();
    var pageHeaderJsonData = window.pageHeaderJsonData;
    if (pageHeaderJsonData) {
      oModel.setData(pageHeaderJsonData);
    }
    sap.ui.getCore().setModel(oModel, 'pageHeader');
  }
});
