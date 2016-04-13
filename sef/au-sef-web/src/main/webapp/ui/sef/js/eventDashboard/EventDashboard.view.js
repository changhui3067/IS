'use strict';

sap.ui.define('sap/sf/sef/eventDashboard/EventDashboard.view.js',
  [
    'jquery.sap.global',
    'sap/m/BusyDialog',
    'sap/m/ScrollContainer',
    'sap/m/Panel',
    'sap/m/ObjectHeader',
    'sap/m/ObjectAttribute',
    'sap/m/Link',
    'sap/m/MessageStrip',
    'sap/sf/sef/Utils'
  ],
  function ($, BusyDialog, ScrollContainer, Panel, ObjectHeader, ObjectAttribute, Link, MessageStrip, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventDashboard.EventDashboard', {
      getControllerName: function () {
        return 'sap.sf.sef.eventDashboard.EventDashboard';
      },

      createContent: function (oController) {
        this._container = new ScrollContainer({
          height: '100%',
          width: '100%',
          vertical: true,
          horizontal: false
        });

        this._container.addContent(new Panel({
          content: [
            new Link({
              text: Utils.getText('COMMON_FORM_BACK', [Utils.getText('ADMIN_TOOLS')]),
              href: '/sf/admin'
            }),
            new ObjectHeader({
              backgroundDesign: sap.m.BackgroundDesign.Transparent,
              title: Utils.getText('SEF_EVENT_CENTER'),
              condensed: true,
              attributes: [
                new ObjectAttribute({
                  width: '100%',
                  text: Utils.getText('SEF_EVENT_CENTER_DETAIL_DESC')
                })
              ]
            }).addStyleClass('sapUiNoMargin sefNoPaddingHeader'),

            new MessageStrip({
              showIcon: true,
              type: sap.ui.core.MessageType.Error,
              text: Utils.getText('SEF_STATUS_NO_XM_PERMISSION'),
              visible: {
                parts: [{path: '/status'}],
                formatter: function (status) {
                  return (status === 'SEF_STATUS_NO_XM_PERMISSION');
                }
              }
            }).addStyleClass('sapUiSmallMarginTopBottom')
          ]
        }));

        return this._container;
      },

      renderTable: function () {
        this._container.addContent(new ScrollContainer({
          vertical: true,
          horizontal: false,
          width: '100%',
          height: '100%',
          content: sap.ui.view({
            type: sap.ui.core.mvc.ViewType.JS,
            viewName: 'sap.sf.sef.eventDashboard.EventsTable',
            models: this.getModel(),
            viewData: {
              rootController: this.getController()
            }
          })
        }));
      },

      setLoading: function (isLoading) {
        if (isLoading) {
          if (!this._loading) {
            this._loading = new BusyDialog();
          }
          this._loading.open();
        }
        else {
          this._loading.close();
        }
      }
    });
  });
