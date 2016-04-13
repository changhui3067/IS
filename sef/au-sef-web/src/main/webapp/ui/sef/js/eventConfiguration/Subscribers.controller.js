'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Subscribers.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ], function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.Subscribers', {
      onInit: function () {
        sap.sf.sef.eventDetail.eventBus.subscribe('sap.sf.sef.eventDetail', 'loadSubscribers', this.onLoad, this);
      },

      navigateExternalLink: function () {
          if (!this._externalLinkDialog) {
            this._externalLinkDialog = this.getView().createExternalLinkDialog();
          }
          this._externalLinkDialog.open();
      },
        
      onLoad: function (sChannelId, sEventName, oParams) {
        var oThis = this;
        var eventType = oParams.eventType;
        var category = oParams.category;

        this._eventType = eventType;
        this._category = category;

        this.getView().setLoading(true);

        if (!oParams.notClean) {
          this.getView().cleanView();
        }

        surj.util.DeferredUtil.createDeferred({
          type: 'ODataService',
          serviceName: 'getSubscriberConfiguration',
          urlParams: {
            results: [
              {
                key: 'event',
                value: '\'' + eventType + '\''
              },
              {
                key: 'category',
                value: category
              }
            ]
          }
        }).done(function (oResponse) {
          oThis.initSubscriberModel(oResponse.SEFSubscriberConfigurationList.events.results[0].categories.results, category);
          oThis.getView().initView();
        }).fail(function (errMsg) {
          Utils.showErrorDialog(errMsg);
        }).always(function () {
          oThis.getView().setLoading(false);
        });
      },

      createSubscriberModel: function () {
        return new sap.ui.model.json.JSONModel({});
      },

      initSubscriberModel: function (oData) {
        if (!this._subscriberModel) {
          this._subscriberModel = this.createSubscriberModel();
          this.getView().setModel(this._subscriberModel);
        }

        this._categoryData = oData[0].events.results;
        this.updateSubscriberModel();
      },

      updateSubscriberModel: function () {
        var modelData = {};

        $.each(this._categoryData, function (i, d) {
          if (!d.subscriberID && !d.subscriberName) {
            modelData.categoryID = d.categoryID;
            modelData.categoryName = d.categoryName;
            modelData.isPreProcessing = d.isPreProcessing;
            modelData.daysInAdvance = d.daysInAdvance;
            modelData.reDelivered = !!d.reDelivered;
            modelData.status = !!d.status;
            modelData.isValid = true;
            modelData.externalLink = d.externalLink;
            modelData.externalLinkTitle = d.externalLinkTitle;

            /* Fix for the ticket - http://jira.successfactors.com/browse/CRF-9272
             * On the PreProcessing module ( Absence -> Talent Management), also display the subscriber description
             * (even its subscriber id is null) and impact area name.
             */
            if (d.isPreProcessing && d.subscribingImpactArea.results.length > 0) {
              modelData.moduleSubscriber = [
                {
                  description: d.subscriberDescription,
                  impactAreas: d.subscribingImpactArea.results || []
                }
              ];
            }
          } else {
            if (!modelData.subscribers) {
              modelData.subscribers = [];
            }

            modelData.subscribers.push({
              daysInAdvance: d.daysInAdvance,
              reDelivered: !!d.reDelivered,
              status: !!d.status,
              subscriberID: d.subscriberID,
              subscriberName: d.subscriberName,
              subscriberDescription: d.subscriberDescription,       
              subscribingImpactArea: d.subscribingImpactArea.results || []
            });
          }
        });

        this.getView().getModel().setData(modelData);
        this.getView().getModel().refresh(true);
      },

      save: function (oData) {
        var oThis = this;
        var oView = this.getView();
        var category = oData.categoryID;
        var enable = oData.status;
        var redeliver = oData.reDelivered;
        var daysInAdvance = oData.daysInAdvance;
        var isPreprocessing = oData.isPreProcessing;
        var event = this._eventType;

        var urlParams = '?event=' + event;
        urlParams += '&category=' + category;
        urlParams += '&enable=' + enable;

        if (enable && isPreprocessing) {
          urlParams += '&redeliver=' + redeliver;

          if (redeliver) {
            urlParams += '&daysInAdvance=' + daysInAdvance;
          }
        }

        oView.setLoading(true);
        $.ajax({
          method: 'POST',
          url: '/odata/v2/restricted/setSubscriberConfiguration' + urlParams,
          dataType: 'json'
        }).done(function (oResponse) {
          oView.showSuccessNotification();
          sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'loadSubscribers', {
            eventType: oThis._eventType,
            category: oThis._category,
            notClean: true
          });
        }).fail(function (jqXHR, textStatus) {
          var errCode = jqXHR.responseJSON.error.code;
          var errMsg = jqXHR.responseJSON.error.message.value;

          Utils.showErrorDialog(errCode + ' : ' + errMsg);
        }).always(function () {
          oView.setLoading(false);
        });
      }
    });
  });
