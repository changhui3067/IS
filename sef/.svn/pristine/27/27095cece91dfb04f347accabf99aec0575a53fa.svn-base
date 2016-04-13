'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/EventDetailMenu.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ],
  function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.EventDetailMenu', {
      onInit: function () {
        this._eventBus = sap.sf.sef.eventDetail.eventBus;
        this._eventBus.subscribe('sap.sf.sef.eventDetail', 'changeEventType', this.restoreMenuStatus, this);
      },

      restoreMenuStatus: function () {
        var menuModel = this.getView().getModel().getProperty('/menus');
        $.each(menuModel, function (i, menu) {
          menu.selected = (i === 0);
        });

        this.getView().getModel().setProperty('/menus', menuModel);
      },

      toDetail: function (menu) {
        switch (menu) {
          case 'MONITORING':
            this._eventBus.publish('sap.sf.sef.eventDetail', 'navToDetail', {target: 'MONITORING'});
            break;

          case 'NOTIFICATIONS':
            this._eventBus.publish('sap.sf.sef.eventDetail', 'navToDetail', {target: 'NOTIFICATIONS'});
            break;

          case 'PUBLISHER':
            this._eventBus.publish('sap.sf.sef.eventDetail', 'navToDetail', {target: 'PUBLISHER'});
            break;

          default:
            this._eventBus.publish('sap.sf.sef.eventDetail', 'navToDetail', {
              target: 'SUBSCRIBERS',
              category: menu
            });
            break;
        }
      }
    });
  });