'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Notifications.controller.js',
  [
    'jquery.sap.global',
    'sap/sf/sef/Utils'
  ],
  function ($, Utils) {
    return sap.ui.controller('sap.sf.sef.eventConfiguration.Notifications', {
      onInit: function () {
        sap.sf.sef.eventDetail.eventBus.subscribe('sap.sf.sef.eventDetail', 'loadNotifications', this.onLoad, this);
      },

      onLoad: function (sChannelId, sEventName, oParams) {
        var oThis = this;
        this._eventType = oParams.eventType;
        this._eventName = oParams.eventName;

        this.getView().setLoading(true);

        if (!oParams.notClean) {
          this.getView().cleanView();
        }

        surj.util.DeferredUtil.createDeferred({
          type: 'ODataService',
          serviceName: 'getNotificationRegistry',
          urlParams: {
            results: [
              {
                key: 'sourceActionKey',
                value: '\'' + this._eventType + '\''
              }
            ]
          }
        }).done(function (oResponse) {
          var oView = oThis.getView();
          var rawData = oResponse.NotificationRegistry;

          if (rawData.moduleRegistryBeans) {
            var data = oThis.constructData(rawData);
            oThis.cloneRawData(data);
            oThis.initNotificationModel(data);
            oView.initView();
          } else {
            oView.showNoDataPage();
          }
        }).fail(function (errMsg) {
          Utils.showErrorDialog(errMsg);
        }).always(function () {
          oThis.getView().setLoading(false);
        });
      },

      constructData: function (oData) {
        var modules = [];
        var moduleMap = {};

        $.each(oData.moduleRegistryBeans.results, function (i, module) {
          var moduleType = module.moduleType;

          if (!moduleMap[moduleType]) {
            moduleMap[moduleType] = {};
            moduleMap[moduleType].isExisted = true;
          }

          var constructedModule = {
            moduleName: module.moduleName,
            moduleType: module.moduleType,
            notifications: []
          };

          $.each(module.registryItems.results, function (i, item) {
            constructedModule.notifications.push(item);
          });

          modules.push(constructedModule);
        });

        return {
          externalCode: oData.externalCode,
          sourceActionName: oData.sourceActionName,
          sourceActionType: oData.sourceActionType,
          modules: modules
        };
      },

      cloneRawData: function (oData) {
        this._data = $.extend(true, {}, oData);
      },

      createNotificationModel: function () {
        return new sap.ui.model.json.JSONModel();
      },

      initNotificationModel: function (oData) {
        if (!this._notificationModel) {
          this._notificationModel = this.createNotificationModel();
          this.getView().setModel(this._notificationModel);
        }
        oData.name = this._eventName;
        this._notificationModel.setData(oData);
      },

      restoreInitialModel: function () {
        var rawData = this._data;
        var oData = this.getView().getModel().getData();

        $.each(oData.modules, function (i, module) {
          $.each(module.notifications, function (j, item) {
            item.mobileEnabled = rawData.modules[i].notifications[j].mobileEnabled;
            item.webEnabled = rawData.modules[i].notifications[j].webEnabled;
          });
        });

        this.initNotificationModel(oData);
      },

      updateTemplateModel: function (oTemplates) {
        var modelData = {templates: []};

        if (!oTemplates) {
          return false;
        }

        $.each(oTemplates, function (i, template) {
          modelData.templates.push({
            template: template.templateContent
          });
        });
        this._templateModel.setData(modelData);
      },

      getTemplateModel: function () {
        if (!this._templateModel) {
          this._templateModel = new sap.ui.model.json.JSONModel();
        }
        return this._templateModel;
      },

      save: function (oData) {
        var oThis = this;
        var oView = this.getView();
        var postData = this.constructSaveData(oData);

        oView.setLoading(true);

        $.ajax({
          method: 'POST',
          url: '/odata/v2/restricted/upsert',
          contentType: 'application/json',
          data: JSON.stringify(postData),
          dataType: 'json'
        }).done(function (oResponse) {
          if (oResponse.d[0].status === 'ERROR') {
            Utils.showErrorDialog(oResponse.d[0].message);
          } else {
            oView.showSuccessNotification();
            sap.sf.sef.eventDetail.eventBus.publish('sap.sf.sef.eventDetail', 'loadNotifications', {
              eventType: oThis._eventType,
              eventName: oThis._eventName,
              notClean: true
            });
          }
        }).fail(function (jqXHR, textStatus) {
          var errCode = jqXHR.responseJSON.error.code;
          var errMsg = jqXHR.responseJSON.error.message.value;

          Utils.showErrorDialog(errCode + ' : ' + errMsg);
        }).always(function () {
          oView.setLoading(false);
        });
      },

      constructSaveData: function (oData) {
        var postData = [];

        $.each(oData.modules, function (i, module) {
          $.each(module.notifications, function (j, item) {
            var notificationType = item.notificationType;

            if (notificationType) {
              postData.push({
                __metadata: {
                  uri: 'NotificationRegistryV2GO(\'' + notificationType + '\')'
                },
                notificationType: notificationType,
                web: item.webEnabled || false,
                mobile: item.mobileEnabled || false
              });
            }
          });
        });

        return postData;
      }
    });
  });
