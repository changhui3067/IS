'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/EventConfiguration.view.js',
  [
    'jquery.sap.global',
    'sap/ui/layout/FixFlex',
    'sap/m/Panel',
    'sap/m/Link',
    'sap/m/ObjectHeader',
    'sap/m/ObjectAttribute',
    'sap/m/Page',
    'sap/m/SplitApp',
    'sap/m/List',
    'sap/m/StandardListItem',
    'sap/m/Popover',
    'sap/m/BusyDialog',
    'sap/sf/sef/Utils'
  ],
  function ($, FixFlex, Panel, Link, ObjectHeader, ObjectAttribute, Page, SplitApp, List,
            StandardListItem, Popover, BusyDialog, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.EventConfiguration', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.EventConfiguration';
      },

      createContent: function (oController) {
        var _this = this;

        this._layout = new FixFlex({
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
                  title: Utils.getText('SEF_EVENT_DETAIL')
                }).addStyleClass('sapUiNoMargin sefNoPaddingHeader')
              ]
            }),

            new ObjectHeader({
              backgroundDesign: sap.m.BackgroundDesign.Transparent,
              title: '{/name}',
              showTitleSelector: true,
              attributes: [
                new ObjectAttribute({
                  text: '{/description}'
                })
              ],
              titleSelectorPress: function (oEvent) {
                // For when we create the popover control, the async response may not ready yet
                // At that time, we cannot get the model.
                // So I move the data binding of popover into the event handler and binding
                // the model at the first clicking
                if (!_this._popover) {
                  _this._popover = _this.createEventTypePopover(oController);
                }
                _this._popover.openBy(oEvent.getParameter('domRef'));
              }
            }).addStyleClass('sefEventDetailName sefNoPaddingHeader')
          ]
        });

        if (!sap.ui.Device.browser.msie) {
          this._layout.addStyleClass('flexFixForNotIE');
        }

        return new Page({
          showHeader: false,
          content: [this._layout]
        }).addStyleClass('sefEventDetail');
      },

      // When model is ready, we will call createApp method to render the view
      createApp: function () {
        this._app = new SplitApp({
          mode: sap.m.SplitAppMode.StretchCompressMode
        });
        this._app.addMasterPage(this.createMasterView());
        this.restoreDefaultDetailView();

        this._layout.setFlexContent(this._app);
      },

      // Master view of the event configuration app
      createMasterView: function () {
        this._menuView = sap.ui.view({
          type: sap.ui.core.mvc.ViewType.JS,
          viewName: 'sap.sf.sef.eventConfiguration.EventDetailMenu',
          models: this.getController().getMenuModel()
        });

        return this._menuView;
      },

      getMonitoringPage: function () {
        if (!this._monitoringPage) {
          this._monitoringPage = sap.ui.view({
            type: sap.ui.core.mvc.ViewType.JS,
            viewName: 'sap.sf.sef.eventConfiguration.Monitoring'
          });
          this._app.addDetailPage(this._monitoringPage);
        }
        return this._monitoringPage;
      },

      // Notifications detail view manager
      // In the initial state, the notifications detail page does not exist,
      // so we should create a new page view for it.
      // And meanwhile, we need bind the model contains current event to it.
      //
      // If the detail view already exists, just returns the page control
      getNotificationsPage: function () {
        if (!this._notificationsPage) {
          this._notificationsPage = sap.ui.view({
            type: sap.ui.core.mvc.ViewType.JS,
            viewName: 'sap.sf.sef.eventConfiguration.Notifications'
          });
          this._app.addDetailPage(this._notificationsPage);
        }
        return this._notificationsPage;
      },

      // Publisher detail view manager
      getPublisherPage: function () {
        if (!this._publisherPage) {
          this._publisherPage = sap.ui.view({
            type: sap.ui.core.mvc.ViewType.JS,
            viewName: 'sap.sf.sef.eventConfiguration.Publisher'
          });
          this._app.addDetailPage(this._publisherPage);
        }
        return this._publisherPage;
      },

      // Subscribers detail view manager
      getSubscribersPage: function () {
        if (!this._subscribersPage) {
          this._subscribersPage = sap.ui.view({
            type: sap.ui.core.mvc.ViewType.JS,
            viewName: 'sap.sf.sef.eventConfiguration.Subscribers'
          });
          this._app.addDetailPage(this._subscribersPage);
        }
        return this._subscribersPage;
      },

      restoreDefaultDetailView: function () {
        this._app.toDetail(this.getMonitoringPage().getId());
        sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'navToDetail', {target: 'MONITORING'});
      },

      createEventTypePopover: function (oController) {
        var oPopover = new Popover({
          placement: sap.m.PlacementType.Bottom,
          title: Utils.getText('SEF_EVENT_LIST')
        });

        var oEventTypeList = new List({
          mode: sap.m.ListMode.SingleSelectMaster,
          rememberSelections: false,
          selectionChange: function (oEvent) {
            var eventType = oEvent.getParameters().listItem.getCustomData()[0].getValue();

            oPopover.close();

            // Fire a event to notify that the event type has been changed
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'changeEventType', {
              eventType: eventType
            });
            oController.router.navTo('eventDetail', {eventType: eventType});
          }
        });

        var rawData = oController.getRawData();

        $.each(rawData, function (i, event) {
          oEventTypeList.addItem(new StandardListItem({
            title: event.name,
            tooltip: event.name,
            customData: [
              {
                key: 'eventType',
                value: event.type
              }
            ]
          }));
        });

        oPopover.addContent(oEventTypeList);
        return oPopover;
      },

      setLoading: function (isLoading) {
        if (!this._loadingDialog) {
          this._loadingDialog = new BusyDialog();
        }

        if (isLoading) {
          this._loadingDialog.open();
        } else {
          this._loadingDialog.close();
        }
      }
    });
  });
