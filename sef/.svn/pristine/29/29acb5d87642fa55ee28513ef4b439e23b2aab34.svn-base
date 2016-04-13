'use strict';

sap.ui.define('sap/sf/sef/eventDashboard/EventsTable.view.js', [
  'jquery.sap.global',
  'sap/m/List',
  'sap/m/StandardListItem',
  'sap/m/Table',
  'sap/m/Column',
  'sap/m/ColumnListItem',
  'sap/m/ObjectHeader',
  'sap/m/ObjectIdentifier',
  'sap/m/ObjectAttribute',
  'sap/m/Toolbar',
  'sap/m/ToolbarSpacer',
  'sap/m/Text',
  'sap/m/Link',
  'sap/m/Popover',
  'sap/sf/sef/Formatter',
  'sap/sf/sef/Utils'
], function ($, List, StandardListItem, Table, Column, ColumnListItem, ObjectHeader, ObjectIdentifier, ObjectAttribute,
             Toolbar, ToolbarSpacer, Text, Link, Popover, Formatter, Utils) {
  return sap.ui.jsview('sap.sf.sef.eventDashboard.EventsTable', {
    getControllerName: function () {
      return 'sap.sf.sef.eventDashboard.EventsTable';
    },

    createContent: function (oController) {
      var content = [];

      this._popover = this.createSubscriberPopover();
      this._popover.setModel(new sap.ui.model.json.JSONModel({subscribers: []}));

      content.push(this.createTable(oController));
      return content;
    },

    createTable: function (oController) {
      var oThis = this;
      var oTable = new Table({
        headerToolbar: new Toolbar({
          content: [
            new Text({text: Utils.getText('SEF_EVENT_LIST')}),
            new ToolbarSpacer(),
            new Link({
              text: Utils.getText('SEF_GO_TO_EVENT_MONITOR'),
              visible: {
                parts: [{path: '/status'}],
                formatter: function (status) {
                  return (status === 'SEF_STATUS_OK');
                }
              },
              press: function () {
                oController.toEventMonitor(oThis.getViewData());
              }
            })
          ]
        }),
        columns: [
          new Column({
            vAlign: sap.ui.core.VerticalAlign.Middle,
            header: new ObjectIdentifier({title: Utils.getText('SEF_EVENT_TYPE')})
          }),
          new Column({
            width: '160px',
            vAlign: sap.ui.core.VerticalAlign.Middle,
            hAlign: Util.isRTL() ? sap.ui.core.TextAlign.Left : sap.ui.core.TextAlign.Right,
            header: new ObjectIdentifier({title: Utils.getText('SEF_EVENT_SUBSCRIBERS')})
          }),
          new Column({
            width: '160px',
            vAlign: sap.ui.core.VerticalAlign.Middle,
            hAlign: Util.isRTL() ? sap.ui.core.TextAlign.Left : sap.ui.core.TextAlign.Right,
            header: new ObjectIdentifier({
              title: Utils.getText('SEF_EVENT_RAISED'),
              text: '(' + Utils.getText('SEF_EVENT_RAISED_TIMEFRAME_WEEK') + ')'
            })
          })
        ]
      });

      oTable.bindAggregation('items', '/data/results', function (sId, oContext) {
        var tr = new ColumnListItem({
          type: sap.m.ListType.Active,
          press: function () {
            oController.toEventDetail(oThis.getViewData(), oContext);
          }
        });

        var eventType = new ObjectHeader({
          backgroundDesign: sap.m.BackgroundDesign.Transparent,
          title: oContext.getProperty('name'),
          condensed: true,
          attributes: [
            new ObjectAttribute({
              width: '100%',
              text: oContext.getProperty('description') || Utils.getText('SEF_EVENT_NO_DESCRIPTION')
            })
          ]
        }).addStyleClass('sapUiNoMargin sefNoPaddingHeader');

        var subscriberControl = null;

        if (oContext.getProperty('subscriberCount') !== 0) {
          subscriberControl = new Link({
            text: Formatter.eventCountFormatter(oContext.getProperty('subscriberCount') || 0)
          });

          subscriberControl.detachBrowserEvent('mouseeenter');
          subscriberControl.detachBrowserEvent('mouseleave');

          subscriberControl.attachBrowserEvent('mouseenter', function () {
            oThis._popover.close();
            oThis._popover.getModel().setProperty('/subscribers', oContext.getProperty('subscribers/results'));
            oThis._popover.openBy(this);
          }).attachBrowserEvent('mouseleave', function () {
            oThis._popover.close();
          })
        }
        else {
          subscriberControl = new Text({
            text: Formatter.eventCountFormatter(oContext.getProperty('subscriberCount') || 0)
          });
        }
        subscriberControl.addStyleClass('eventTableNumber eventTableNumberSubscribers');

        var eventRaisedText = new Text({
          text: Formatter.eventCountFormatter(oContext.getProperty('eventsRaised'))
        }).addStyleClass('eventTableNumber eventTableNumberRaised');

        tr.addCell(eventType);
        tr.addCell(subscriberControl);
        tr.addCell(eventRaisedText);

        return tr;
      });

      this._table = oTable;

      return oTable.addStyleClass('eventsTable');
    },

    createSubscriberPopover: function () {
      var oPopover = new Popover({
        placement: sap.m.PlacementType.Left,
        showHeader: false
      });

      var oSubscribers = new List();
      oSubscribers.bindAggregation('items', '/subscribers', function (sId, oContext) {
        return new StandardListItem({
          title: Utils.getText(oContext.getProperty())
        });
      });

      oPopover.addContent(oSubscribers);
      return oPopover;
    }
  });
});
