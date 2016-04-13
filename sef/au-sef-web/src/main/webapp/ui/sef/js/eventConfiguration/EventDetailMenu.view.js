'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/EventDetailMenu.view.js',
  [
    'jquery.sap.global',
    'sap/m/Page',
    'sap/m/List',
    'sap/m/StandardListItem',
    'sap/m/GroupHeaderListItem'
  ], function ($, Page, List, StandardListItem, GroupHeaderListItem) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.EventDetailMenu', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.EventDetailMenu'
      },

      createContent: function (oController) {
        var menuList = new List({
          rememberSelections: false,
          mode: sap.m.ListMode.SingleSelectMaster
        }).attachSelectionChange(this.toDetail, this)
          .addStyleClass('sefEventDetailMenu');

        menuList.bindAggregation('items', '/menus', function (sId, oContext) {
          var menuItem;

          if (oContext.getProperty('type') === 'group') {
            menuItem = new GroupHeaderListItem({
              title: oContext.getProperty('text'),
              upperCase: false
            }).addStyleClass('sefCustomMenuGroupHeader');

            if (sap.ui.Device.browser.msie && sap.ui.Device.browser.version === 9) {
              menuItem.addStyleClass('sapMLIBNoFlex');
            }
          } else {
            menuItem = new StandardListItem({
              title: oContext.getProperty('text'),
              selected: '{selected}',
              customData: [
                {
                  key: 'name',
                  value: oContext.getProperty('name')
                }
              ]
            });

            if (oContext.getProperty('type') === 'submenu') {
              menuItem.addStyleClass('sefEventDetailSubMenu');
            }
          }
          return menuItem;
        });

        return new Page({
          showHeader: false,
          content: [menuList]
        });
      },

      toDetail: function (oEvent) {
        var oController = this.getController();
        var menu = oEvent.getParameters().listItem.getCustomData()[0].getValue();

        oController.toDetail(menu);
      }
    });
  });
