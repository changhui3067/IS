'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/EventConfiguration.controller.js',
  [
    'jquery.sap.global',
    'sap/ui/core/EventBus',
    'sap/sf/sef/Utils'
  ],
  function ($, EventBus, Utils) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.EventConfiguration', {
      // Raw data from oData API
      _data: null,

      // Event configuration model
      _detailModel: null,

      // Current event type
      _eventType: null,

      // Event metadata is not loaded initially
      _isLoad: false,

      // Static menu items for the event detail page
      _staticMenu: [
        {
          name: 'MONITORING',
          text: Utils.getText('SEF_EVENT_MONITORING'),
          type: 'menu',
          selected: true
        },
        {
          name: 'NOTIFICATIONS',
          text: Utils.getText('SEF_EVENT_NOTIFICATIONS'),
          type: 'menu'
        },
        {
          name: 'PUBLISHER',
          text: Utils.getText('SEF_EVENT_PUBLISHER'),
          type: 'menu'
        }
      ],

      onInit: function () {
        // Create a event bus for event detail
        if (!sap.sf.sef.eventDetail) {
          sap.sf.sef.eventDetail = {};
        }
        sap.sf.sef.eventDetail.eventBus = new EventBus();

        sap.sf.sef.eventDetail.eventBus.subscribe('sap.sf.sef.eventDetail', 'changeEventType', this.onChangeEventType, this);
        sap.sf.sef.eventDetail.eventBus.subscribe('sap.sf.sef.eventDetail', 'navToDetail', this.onNavToDetail, this);

        // Get the router of component and attach a listener when router changed
        this.router = sap.ui.core.UIComponent.getRouterFor(this);
        this.router.attachRouteMatched(this.onRouteMatched, this);
      },

      onChangeEventType: function (sChannelId, sEventName, oParameters) {
        this._eventType = oParameters.eventType;
        this.updateDetailModel(this._eventType);
        this.updateMenuModel();
      },

      // Router change listener
      // It is noticed that when not only user redirect to here from event dashboard landing page
      // but also user changes the event type in the subheader, this event handler will be triggered.
      onRouteMatched: function (oEvent) {
        var oThis = this;
        var oView = this.getView();
        var routeName = oEvent.getParameter('name');
        var vArguments = oEvent.getParameter('arguments');

        // Every time the router changed, we should update current event type.
        if (routeName === 'eventDetail') {
          this._eventType = vArguments.eventType;

          if (!this._isLoad) {
            oView.setLoading(true);

            // Call oData API and get event meta data
            // After that, initialize event configuration model and create the app control
            surj.util.DeferredUtil.createDeferred({
              type: 'ODataService',
              serviceName: 'getEventMetaData'
            }).done(function (oResponse) {
              oThis._isLoad = true;
              oThis._data = oResponse.SEFEventsList.data.results;
              oView.createApp();
              sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'changeEventType', {
                eventType: oThis._eventType
              });
            }).fail(function (errMsg) {
              Utils.showErrorDialog(errMsg);
            }).always(function () {
              oView.setLoading(false);
            });
          } else {
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'changeEventType', {
              eventType: oThis._eventType
            });
            this.getView().restoreDefaultDetailView();
          }
        }
      },

      createDetailModel: function () {
        var detailModel = new sap.ui.model.json.JSONModel();
        this.getView().setModel(detailModel);
        return detailModel;
      },

      updateDetailModel: function (sEventName) {
        // Generate detail model for the event detail app
        if (!this._detailModel) {
          this._detailModel = this.createDetailModel();
        }

        var detailModel = this._detailModel;
        $.each(this._data, function (i, data) {
          if (data.type === sEventName) {
            detailModel.setData(data);
            return false;
          }
        });
      },

      createMenuModel: function () {
        return new sap.ui.model.json.JSONModel();
      },

      updateMenuModel: function () {
        if (!this._menuModel) {
          this._menuModel = this.createMenuModel();
        }

        var subscribers = this._detailModel.getProperty('/subscribers').results;
        var menuModelData = [];
        $.each(this._staticMenu, function (i, menu) {
          menuModelData.push(menu);
        });

        if (subscribers.length > 0) {
          menuModelData.push({
            text: Utils.getText('SEF_EVENT_SUBSCRIBERS'),
            type: 'group'
          });

          $.each(subscribers, function (i, subscriber) {
            menuModelData.push({
              name: subscriber,
              text: Utils.getText(subscriber),
              type: 'submenu'
            });
          });
        }

        this._menuModel.setData({
          type: this._eventType,
          menus: menuModelData
        });
      },

      getMenuModel: function () {
        if (!this._menuModel) {
          this._menuModel = this.createMenuModel();
        }

        return this._menuModel;
      },

      getRawData: function () {
        return this._data;
      },

      onNavToDetail: function (sChannelId, sEventName, oParams) {
        var view = this.getView();
        var app = view._app;

        switch (oParams.target) {
          case 'MONITORING':
            app.toDetail(view.getMonitoringPage().getId());
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'loadMonitoring', {
              eventType: this._eventType
            });
            break;

          case 'NOTIFICATIONS':
            app.toDetail(view.getNotificationsPage().getId());
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'loadNotifications', {
              eventType: this._eventType,
              eventName: this.getView().getModel().getProperty('/name')
            });
            break;

          case 'PUBLISHER':
            app.toDetail(view.getPublisherPage().getId());
            break;

          case 'SUBSCRIBERS':
          default:
            app.toDetail(view.getSubscribersPage().getId());
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'loadSubscribers', {
              eventType: this._eventType,
              category: oParams.category
            });
            break;
        }
      },

      // Back to event dashboard
      toEventCenter: function () {
        this.router.navTo('eventCenter');
      }
    });
  });
