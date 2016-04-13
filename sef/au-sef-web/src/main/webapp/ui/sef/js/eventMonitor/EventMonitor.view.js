'use strict';

sap.ui.define('sap/sf/sef/eventMonitor/EventMonitor.view.js',
  [
    'jquery.sap.global',
    'sap/ui/layout/FixFlex',
    'sap/m/SplitApp',
    'sap/m/Page',
    'sap/m/List',
    'sap/m/StandardListItem',
    'sap/m/Panel',
    'sap/m/ObjectHeader',
    'sap/m/Link',
    'sap/sf/sef/Utils'
  ],
  function ($, FixFlex, SplitApp, Page, List, StandardListItem, Panel, ObjectHeader, Link, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventMonitor.EventMonitor', {
      getControllerName: function () {
        return 'sap.sf.sef.eventMonitor.EventMonitor';
      },

      createContent: function (oController) {
        var container = new FixFlex({
          fixContent: [
            new Panel({
              content: [
                new Link({
                  text: Utils.getText('COMMON_FORM_BACK', [Utils.getText('SEF_EVENT_CENTER')]),
                  press: function () {
                    oController.toEventCenter();
                  }
                }),
                new ObjectHeader({
                  backgroundDesign: sap.m.BackgroundDesign.Transparent,
                  title: Utils.getText('SEF_EVENT_MONITOR')
                }).addStyleClass('sapUiNoMargin sefNoPaddingHeader')
              ]
            })
          ]
        });

        this._app = new SplitApp({
          mode: sap.m.SplitAppMode.StretchCompressMode
        });
        this._app.addMasterPage(this.createMasterView());
        this._app.addDetailPage(this.createEventRaisedView());
        container.setFlexContent(this._app);

        if (!sap.ui.Device.browser.msie) {
          container.addStyleClass('flexFixForNotIE');
        }

        return new Page({
          showHeader: false,
          content: [container]
        }).addStyleClass('sefEventMonitor');
      },

      createMasterView: function () {
        return new Page({
          showHeader: false,
          content: [
            new List({
              mode: sap.m.ListMode.SingleSelectMaster,
              items: [
                new StandardListItem({
                  selected: true,
                  title: Utils.getText('SEF_EVENT_RAISED')
                })
              ]
            })
          ]
        });
      },

      createEventRaisedView: function () {
        return new Page({
          backgroundDesign: sap.m.PageBackgroundDesign.Solid,
          showHeader: false,
          content: [
            sap.ui.view({
              type: sap.ui.core.mvc.ViewType.JS,
              viewName: 'sap.sf.sef.eventMonitor.EventsRaised'
            })
          ]
        }).addStyleClass('sapUiContentPadding');
      }
    });
  });
