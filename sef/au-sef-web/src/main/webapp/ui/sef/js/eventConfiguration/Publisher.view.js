//! include /ui/sef/js/eventConfiguration/data.js

'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Publisher.view.js',
  [
    'jquery.sap.global',
    'sap/ui/layout/VerticalLayout',
    'sap/m/Page',
    'sap/m/ObjectHeader',
    'sap/m/Button',
    'sap/m/Link',
    'sap/m/Dialog',
    'sap/m/Toolbar',
    'sap/m/ToolbarSpacer',
    'sap/sf/sef/Utils'
  ],
  function ($, VerticalLayout, Page, ObjectHeader, Button, Link, Dialog, Toolbar, ToolbarSpacer, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.Publisher', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.Publisher';
      },

      createContent: function (oController) {
        var page = new Page({
          backgroundDesign: sap.m.PageBackgroundDesign.Solid,
          showHeader: false
        });

        page.addContent(new ObjectHeader({
          backgroundDesign: sap.m.BackgroundDesign.Transparent,
          title: {
            parts: [
              {path: '/publisher'}
            ],
            formatter: function (publisher) {
              return Utils.getText('SEF_EVENT_PUBLISHER') + ': '+ Utils.getText(publisher);
            }
          }
        }).addStyleClass('sefNoPaddingHeader'));

        // Rule link
        page.addContent(new VerticalLayout({
          width: '100%',
          content: [
            new Link({
              visible: {
                parts: [
                  {path: '/ruleID'}
                ],
                formatter: function (ruleId) {
                  return !!ruleId;
                }
              },
              text: Utils.getText('SEF_PUBLISHER_DETAIL_VIEW_PUBLISHING_RULE_LABEL').replace('{0}', '{/name}'),
              press: function () {
                oController.showRulePopup();
              }
            }).addStyleClass('sapUiSmallMarginBottom'),

            new Link({
              visible: '{/externallyAllowed}',
              text: Utils.getText('SEF_PUBLISHER_DETAIL_CONFIG_EXTERNAL_ENDPOINT_LABEL'),
              press: function () {
                oController.showEndPointPopup();
              }
            })
          ]
        }).addStyleClass('sapUiContentPadding'));

        return page;
      },

      createRuleDialog: function () {
        var oThis = this;

        var header = new ObjectHeader().addStyleClass('sapUiNoMargin sefNoPaddingHeader');
        var ruleContainer = new VerticalLayout({width: '100%'});

        var dialog = new Dialog({
          busyIndicatorDelay: 0,
          contentWidth: '100%',
          contentHeight: '100%',
          customHeader: new Toolbar({
            content: [
              new ToolbarSpacer(),
              new Button({
                icon: 'sap-icon://decline',
                type: sap.m.ButtonType.Transparent,
                press: function () {
                  dialog.close();
                }
              })
            ]
          }),
          content: [header, ruleContainer],

          afterOpen: function () {
            var ruleId = this.getModel().getProperty('/ruleID');

            if (!ruleId) {
              return false;
            }

            var dialogContentDomId = $(this.getContent()[1].getDomRef()).attr('id');
            oThis.createRuleEngineView(ruleId, dialogContentDomId);
          }
        }).addStyleClass('sefDialog');

        return dialog;
      },

      createRuleEngineView: function (sRuleId, sDomId) {
        // Use current date as the rule effective date
        var selectedDate = sap.ui.core.format.DateFormat.getDateInstance({pattern: 'YYYY-MM-dd'}).format(new Date());

        $('<iframe>', {
          src: '/xi/ui/sef/pages/ruleEmbed.xhtml?ruleId=' + sRuleId + '&selectedDate=' + selectedDate,
          frameborder: 0,
          style: 'position:absolute;top:0;left:0;width:100%;height:100%;overflow:auto'
        }).appendTo('#' + sDomId);
      }
    });
  });
