'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Notifications.view.js',
  [
    'jquery.sap.global',
    'sap/ui/core/Icon',
    'sap/ui/layout/VerticalLayout',
    'sap/m/VBox',
    'sap/m/Page',
    'sap/m/Panel',
    'sap/m/List',
    'sap/m/CustomListItem',
    'sap/m/Table',
    'sap/m/Column',
    'sap/m/ColumnListItem',
    'sap/m/Toolbar',
    'sap/m/ObjectHeader',
    'sap/m/ObjectAttribute',
    'sap/m/Title',
    'sap/m/Button',
    'sap/m/Switch',
    'sap/m/Text',
    'sap/m/ResponsivePopover',
    'sap/m/MessageToast',
    'sap/m/MessagePage',
    'sap/sf/sef/Utils'
  ],
  function ($, Icon, VerticalLayout, VBox, Page, Panel, List, CustomListItem, Table, Column, ColumnListItem,
            Toolbar, ObjectHeader, ObjectAttribute, Title, Button, Switch, Text, ResponsivePopover, MessageToast,
            MessagePage, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.Notifications', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.Notifications';
      },

      createContent: function (oController) {
        var oThis = this;

        this._header = new ObjectHeader({
          backgroundDesign: sap.m.BackgroundDesign.Transparent,
          title: Utils.getText('SEF_EVENT_NOTIFICATIONS'),
          attributes: [
            new ObjectAttribute({
              text: Utils.getText('SEF_EVENT_NOTIFICATIONS_SUBTITLE').replace('{{name}}', '{/name}')
            })
          ]
        }).addStyleClass('sapUiNoMargin');

        this._notificationContainer = new VerticalLayout({width: '100%'});
        this._notificationContainer.bindAggregation('content', '/modules', function (sId, oContext) {
          var modulePanel = new Panel({
            headerToolbar: new Toolbar({
              content: [
                new Icon({
                  size: '1rem',
                  src: 'sap-icon://slim-arrow-right'
                }).addStyleClass('sapUiTinyMarginEnd'),
                new Title({text: oContext.getProperty('moduleName')})
              ]
            })
          }).addStyleClass('sefNoBorderContainer');

          modulePanel.bindAggregation('content', 'notifications', function (sInnerId, oInnerContext) {
            var notificationList = new List().addStyleClass('sapUiSmallMarginBottom');
            var item = oInnerContext.getProperty();

            if (item.toDoEnabled !== undefined && item.toDoEnabled !== null) {
              notificationList.addItem(new CustomListItem({
                content: [
                  new VBox({
                    items: [
                      new Toolbar({
                        content: [
                          new Title({
                            text: Utils.getText('SEF_EVENT_NOTIFICATIONS_TODO')
                          }).addStyleClass('sefNotificationItemTitle sapUiSmallMarginTopBottom sapUiMediumMarginEnd'),

                          new Switch({
                            enabled: false,
                            state: '{toDoEnabled}'
                          })
                        ]
                      }),
                      new Text({
                        text: item.notificationName,
                        visible: !!item.notificationName
                      }),
                      new Text({
                        text: item.toDoDescription,
                        visible: !!item.toDoDescription
                      }).addStyleClass('sefNotificationDesc')
                    ]
                  }).addStyleClass('sapThemeLightText')
                ]
              }).addStyleClass('sefNotificationItem'));
            }

            if (item.emailEnabled !== undefined && item.emailEnabled !== null) {
              notificationList.addItem(new CustomListItem({
                content: [
                  new VBox({
                    items: [
                      new Toolbar({
                        content: [
                          new Title({
                            design: sap.m.LabelDesign.Bold,
                            text: Utils.getText('SEF_EVENT_NOTIFICATIONS_EMAIL')
                          }).addStyleClass('sefNotificationItemTitle sapUiSmallMarginTopBottom sapUiMediumMarginEnd'),

                          new Switch({
                            enabled: false,
                            state: '{emailEnabled}'
                          })
                        ]
                      }),
                      new Text({
                        text: item.notificationName,
                        visible: !!item.notificationName
                      }),
                      new Text({
                        text: item.emailDescription,
                        visible: !!item.emailDescription
                      }).addStyleClass('sefNotificationDesc')
                    ]
                  }).addStyleClass('sapThemeLightText')
                ]
              }).addStyleClass('sefNotificationItem'));
            }

            if (item.mobileEnabled !== undefined && item.mobileEnabled !== null) {
              notificationList.addItem(new CustomListItem({
                content: [
                  new VBox({
                    items: [
                      new Toolbar({
                        content: [
                          new Title({
                            design: sap.m.LabelDesign.Bold,
                            text: Utils.getText('SEF_EVENT_NOTIFICATIONS_PUSH')
                          }).addStyleClass('sefNotificationItemTitle sapUiSmallMarginTopBottom'),

                          new Button({
                            type: sap.m.ButtonType.Transparent,
                            icon: 'sap-icon://sys-help',
                            tooltip: Utils.getText('SEF_EVENT_NOTIFICATIONS_SHOW_TEMPLATES'),
                            press: function (oEvent) {
                              oController.updateTemplateModel(item.templates.results);
                              oThis._templatePopover.openBy(oEvent.getSource());
                            }
                          }).addStyleClass('sapUiSmallMarginBeginEnd'),

                          new Switch({state: '{mobileEnabled}'})
                        ]
                      }),

                      new Text({
                        text: item.notificationName,
                        visible: !!item.notificationName
                      }),
                      new Text({
                        text: item.mobileDescription,
                        visible: !!item.mobileDescription
                      }).addStyleClass('sefNotificationDesc')
                    ]
                  }).addStyleClass('sapThemeLightText')
                ]
              }).addStyleClass('sefNotificationItem'));
            }

            if (item.webEnabled !== undefined && item.webEnabled !== null) {
              notificationList.addItem(new CustomListItem({
                content: [
                  new VBox({
                    width: '100%',
                    items: [
                      new Toolbar({
                        allowWrapping: true,
                        content: [
                          new Title({
                            design: sap.m.LabelDesign.Bold,
                            text: Utils.getText('SEF_EVENT_NOTIFICATIONS_WEB')
                          }).addStyleClass('sefNotificationItemTitle sapUiSmallMarginTopBottom'),

                          new Button({
                            type: sap.m.ButtonType.Transparent,
                            icon: 'sap-icon://sys-help',
                            tooltip: Utils.getText('SEF_EVENT_NOTIFICATIONS_SHOW_TEMPLATES'),
                            press: function (oEvent) {
                              oController.updateTemplateModel(item.templates.results);
                              oThis._templatePopover.openBy(oEvent.getSource());
                            }
                          }).addStyleClass('sapUiSmallMarginBeginEnd'),

                          new Switch({state: '{webEnabled}'})
                        ]
                      }),
                      new Text({
                        text: item.notificationName,
                        visible: !!item.notificationName
                      }),
                      new Text({
                        text: item.webDescription,
                        visible: !!item.webDescription
                      }).addStyleClass('sefNotificationDesc')
                    ]
                  }).addStyleClass('sapThemeLightText')
                ]
              }).addStyleClass('sefNotificationItem'));
            }

            return notificationList;
          });
          return modulePanel;
        });

        this._footer = new Toolbar({
          content: [
            new sap.m.ToolbarSpacer(),
            new Button({
              text: Utils.getText('COMMON_Cancel'),
              press: $.proxy(this.onCancel, this)
            }),
            new Button({
              type: sap.m.ButtonType.Emphasized,
              text: Utils.getText('COMMON_Save'),
              press: $.proxy(this.onSave, this)
            })
          ]
        });

        this._templateTable = new Table({
          columns: [
            new Column({
              vAlign: sap.ui.core.VerticalAlign.Middle,
              header: new Text({text: Utils.getText('SEF_EVENT_NOTIFICATIONS_TEMPLATE')})
            })
          ]
        });

        this._templateTable.bindAggregation('items', '/templates', function (sId, oContext) {
          return new ColumnListItem({
            cells: [
              new Text({text: oContext.getProperty('template')})
            ]
          });
        });

        this._templatePopover = new ResponsivePopover({
          contentWidth: '80%',
          showHeader: false,
          showCloseButton: false,
          placement: sap.m.PlacementType.Vertical,
          content: [
            this._templateTable
          ]
        }).addStyleClass('sapUiNoContentPadding').setModel(oController.getTemplateModel());

        this._page = new Page({
          showHeader: false,
          backgroundDesign: sap.m.PageBackgroundDesign.Solid,
          busyIndicatorDelay: 0
        });

        return this._page;
      },

      initView: function () {
        this._page.addContent(this._header);
        this._page.addContent(this._notificationContainer);
        this._page.setShowFooter(true);
        this._page.setFooter(this._footer);
      },

      cleanView: function () {
        this._page.setShowFooter(false);
        this._page.removeAllContent();
      },

      showNoDataPage: function () {
        this._page.addContent(new MessagePage({
          showHeader: false,
          text: Utils.getText('SEF_EVENT_NOTIFICATIONS_NODATA_MSG'),
          description: '',
          icon: 'sap-icon://notification'
        }));
      },

      setLoading: function (isLoading) {
        this._page.setBusy(isLoading);
      },

      onCancel: function () {
        this.getController().restoreInitialModel();
      },

      onSave: function () {
        var oController = this.getController();
        oController.save(this.getModel().getData());
      },

      showSuccessNotification: function () {
        MessageToast.show(Utils.getText('COMMON_CHANGES_SAVED'));
      }
    });
  });
