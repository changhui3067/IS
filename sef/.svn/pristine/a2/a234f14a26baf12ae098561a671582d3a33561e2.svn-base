'use strict';

sap.ui.define('sap/sf/sef/eventDashboard/Component',
  [
    'jquery.sap.global',
    'sap/m/App'
  ], function ($, App) {
    return sap.ui.core.UIComponent.extend('sap.sf.sef.eventDashboard.Component', {
      metadata: {
        routing: {
          config: {
            viewType: 'JS',
            targetControl: 'EventCenter',
            targetAggregation: 'pages',
            clearTarget: true
          },

          routes: [
            {
              name: 'eventCenter',
              pattern: '',
              viewName: 'sap.sf.sef.eventDashboard.EventDashboard'
            },
            {
              name: 'eventMonitor',
              pattern: 'eventMonitor',
              viewName: 'sap.sf.sef.eventMonitor.EventMonitor'
            },
            {
              name: 'eventDetail',
              pattern: 'eventDetail/{eventType}',
              viewName: 'sap.sf.sef.eventConfiguration.EventConfiguration'
            }
          ]
        }
      },

      init: function () {
        sap.ui.core.UIComponent.prototype.init.apply(this, arguments);
        this.getRouter().initialize();
      },

      createContent: function () {
        return new App('EventCenter', {
          width: '100%'
        }).addStyleClass('sefEventCenter');
      }
    });
  });
