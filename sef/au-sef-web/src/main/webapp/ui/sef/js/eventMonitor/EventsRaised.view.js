'use strict';

sap.ui.define('sap/sf/sef/eventMonitor/EventsRaised.view.js',
  [
    'jquery.sap.global',
    'sap/ui/core/Item',
    'sap/ui/layout/VerticalLayout',
    'sap/m/BusyIndicator',
    'sap/m/ObjectHeader',
    'sap/m/ObjectAttribute',
    'sap/m/Label',
    'sap/m/ComboBox',
    'sap/m/MessagePage',
    'sap/m/Text',
    'sap/viz/ui5/controls/VizFrame',
    'sap/viz/ui5/controls/Popover',
    'sap/viz/ui5/controls/common/feeds/FeedItem',
    'sap/viz/ui5/data/FlattenedDataset',
    'sap/sf/sef/Utils'
  ],
  function ($, Item, VerticalLayout, BusyIndicator, ObjectHeader, ObjectAttribute, Label, ComboBox, MessagePage, Text,
            VizFrame, Popover, FeedItem, FlattenedDataset, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventMonitor.EventsRaised', {
      getControllerName: function () {
        return 'sap.sf.sef.eventMonitor.EventsRaised';
      },

      createContent: function (oController) {
        this._loading = new BusyIndicator();
        var container = new VerticalLayout({
          width: '100%',
          content: [
            new ObjectHeader({
              backgroundDesign: sap.m.BackgroundDesign.Transparent,
              title: Utils.getText('SEF_EVENT_RAISED'),
              condensed: true,
              attributes: [
                new ObjectAttribute({
                  text: Utils.getText('SEF_EVENT_RAISED_DESC')
                })
              ]
            }).addStyleClass('sapUiNoMargin sefNoPaddingHeader'),

            new Label({
              design: sap.m.LabelDesign.Bold,
              text: Utils.getText('SEF_EVENT_RAISED_TIMEFRAME')
            }).addStyleClass('sapUiMediumMarginTop'),

            new ComboBox({
              selectedKey: 'WEEK',
              items: [
                new Item({
                  text: Utils.getText('SEF_EVENT_RAISED_TIMEFRAME_DAY'),
                  key: 'DAY'
                }),
                new Item({
                  text: Utils.getText('SEF_EVENT_RAISED_TIMEFRAME_WEEK'),
                  key: 'WEEK'
                }),
                new Item({
                  text: Utils.getText('SEF_EVENT_RAISED_TIMEFRAME_MONTH'),
                  key: 'MONTH'
                })
              ],
              selectionChange: function (oControlEvent) {
                oController.changeDuration(oControlEvent.getParameters().selectedItem);
              }
            })
          ]
        });

        this._container = new VerticalLayout({width: '100%'});
        container.addContent(this._container);
        container.addContent(this.createNote());
        return container;
      },

      renderChart: function () {
        if (!this._chart) {
          var oPopover = new Popover();
          var oDataset = new FlattenedDataset({
            dimensions: [
              {
                axis: 1,
                name: 'Name',
                value: '{eventName}'
              }
            ],
            measures: [
              {
                name: Utils.getText('SEF_EVENT_RAISED'),
                value: '{eventsRaised}'
              }
            ],
            data: {
              path: '/events'
            }
          });

          this._chart = new VizFrame({
            width: '100%',
            height: '320px',
            vizType: 'column',
            uiConfig: {
              'applicationSet': 'fiori'
            },
            vizProperties: {
              title: {
                visible: false
              },
              legend: {
                visible: false
              },
              valueAxis: {
                title: {
                  visible: false
                },
                gridline: {
                  visible: true
                },

                scroll: {
                  enable: true
                }
              },
              categoryAxis: {
                title: {
                  visible: false
                }
              }
            },
            feeds: [
              new FeedItem({
                uid: 'categoryAxis',
                type: 'Dimension',
                values: ['Name']
              }),
              new FeedItem({
                uid: 'valueAxis',
                type: 'Measure',
                values: [Utils.getText('SEF_EVENT_RAISED')]
              })
            ],

            dataset: oDataset
          });

          oPopover.connect(this._chart.getVizUid());
        }
        this._chart.setModel(this.getModel());
        this._container.addContent(this._chart);
      },

      createNote: function () {
        return new Text({
          text: Utils.getText('SEF_EVENT_RAISED_NOTE')
        });
      },

      renderNoDataIndicator: function () {
        this._noDataControl = new MessagePage({
          showHeader: false,
          text: Utils.getText('SEF_EVENT_RAISED_NO_DATA'),
          description: '',
          icon: 'sap-icon://vertical-bar-chart'
        }).addStyleClass('sefNoDataMsgPage');

        this._container.addContent(this._noDataControl);
      },

      setLoading: function (isLoading) {
        if (isLoading) {
          this._container.removeAllContent();
          this._container.addContent(this._loading);
        }
        else {
          this._container.removeContent(this._loading);
        }
      }
    });
  });
