'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Monitoring.view.js',
  [
    'jquery.sap.global',
    'sap/m/Page',
    'sap/ui/core/Item',
    'sap/ui/layout/VerticalLayout',
    'sap/m/ObjectHeader',
    'sap/m/ObjectAttribute',
    'sap/m/Label',
    'sap/m/ComboBox',
    'sap/m/Text',
    'sap/m/MessagePage',
    'sap/viz/ui5/controls/VizFrame',
    'sap/viz/ui5/controls/Popover',
    'sap/viz/ui5/controls/common/feeds/FeedItem',
    'sap/viz/ui5/data/FlattenedDataset',
    'sap/sf/sef/Utils'
  ], function ($, Page, Item, VerticalLayout, ObjectHeader, ObjectAttribute, Label, ComboBox, Text, MessagePage,
               VizFrame, Popover, FeedItem, FlattenedDataset, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.Monitoring', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.Monitoring';
      },

      createContent: function (oController) {
        this._page = new Page({
          showHeader: false,
          backgroundDesign: sap.m.PageBackgroundDesign.Solid,
          width: '100%',
          content: [
            new ObjectHeader({
              backgroundDesign: sap.m.BackgroundDesign.Transparent,
              title: Utils.getText('SEF_EVENT_MONITORING_TITLE'),
              condensed: true,
              attributes: [
                new ObjectAttribute({text: Utils.getText('SEF_EVENT_RAISED_COUNT')})
              ]
            }).addStyleClass('sapUiNoMargin sefNoPaddingHeader'),

            new Label({
              width: '100%',
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
        }).addStyleClass('sapUiContentPadding');

        this._container = new VerticalLayout({width: '100%'});
        this._page.addContent(this._container);
        return this._page;
      },

      renderChart: function () {
        if (!this._chart) {
          var oPopover = new Popover();
          var oDataset = new FlattenedDataset({
            dimensions: [
              {
                axis: 1,
                name: 'Date',
                value: "{path:'time'}"
              }
            ],
            measures: [
              {
                name: 'Count',
                value: "{path:'count'}"
              }
            ],
            data: {
              path: '/events/'
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
                'uid': 'categoryAxis',
                'type': 'Dimension',
                'values': ['Date']
              }),
              new FeedItem({
                'uid': 'valueAxis',
                'type': 'Measure',
                'values': ['Count']
              })
            ],

            dataset: oDataset
          });

          oPopover.connect(this._chart.getVizUid());
        }
        this._chart.setModel(this.getModel());
        this._container.addContent(this._chart);
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
        this._page.setBusy(isLoading);
      }
    });
  });
