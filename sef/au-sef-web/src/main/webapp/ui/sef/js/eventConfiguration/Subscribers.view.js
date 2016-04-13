'use strict';

sap.ui.define('sap/sf/sef/eventConfiguration/Subscribers.view.js',
  [
    'jquery.sap.global',
    'sap/ui/layout/VerticalLayout',
    'sap/m/Page',
    'sap/m/Panel',
    'sap/m/ObjectHeader',
    'sap/m/ObjectIdentifier',
    'sap/m/Title',
    'sap/m/List',
    'sap/m/CustomListItem',
    'sap/m/Switch',
    'sap/m/Link',
    'sap/m/Input',
    'sap/m/Button',
    'sap/m/CheckBox',
    'sap/m/Toolbar',
    'sap/m/ToolbarSpacer',
    'sap/m/Label',
    'sap/m/Text',
    'sap/m/MessageToast',
    'sap/sf/sef/Utils'
  ],
  function ($, VerticalLayout, Page, Panel, ObjectHeader, ObjectIdentifier, Title, List, CustomListItem,
            Switch, Link, Input, Button, CheckBox, Toolbar, ToolbarSpacer, Label, Text, MessageToast, Utils) {
    return sap.ui.jsview('sap.sf.sef.eventConfiguration.Subscribers', {
      getControllerName: function () {
        return 'sap.sf.sef.eventConfiguration.Subscribers';
      },

      createContent: function (oController) {
        var oThis = this;

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
             enabled: {
                parts: [
                  {path: '/status'},
                  {path: '/reDelivered'},
                  {path: '/daysInAdvance'},
                  {path: '/isValid'}
                ],
                formatter: function (isEnabled, isChecked, daysValue, isValid) {
                  return (isEnabled && isChecked && ((!daysValue && daysValue !== 0) || !isValid)) ? false : true;
                }
              },
              press: $.proxy(this.onSave, this)
            })
          ]
        });

        var subController = this.getController();
        this._daysInput = new Input({
          width: '3.25rem',
          enabled: '{/reDelivered}',
          value: {
            path: '/daysInAdvance',
            type: new sap.ui.model.type.Integer({}, {
              minimum: 0,
              maximum: 999
            })
          },
          valueStateText: Utils.getText('SEF_EVENT_PREPROCESSING_BEHAVIOR_DAYS_VALIDATION_TEXT')
        });

        this._daysInput
          .attachParseError(function (oEvent) {
            oEvent.getSource().setValueState(sap.ui.core.ValueState.Error);
            oThis.getModel().setProperty('/isValid', false);
          })
          .attachValidationError(function (oEvent) {
            oEvent.getSource().setValueState(sap.ui.core.ValueState.Error);
            oThis.getModel().setProperty('/isValid', false);
          })
          .attachValidationSuccess(function (oEvent) {
            oEvent.getSource().setValueState(sap.ui.core.ValueState.Success);
            oThis.getModel().setProperty('/isValid', true);
          });

        var preprocessingCheckBox = new CheckBox({selected: '{/reDelivered}'});
        var preprocesingContainer = new Panel({
          visible: '{/isPreProcessing}',
          content: [
            new ObjectHeader({
              backgroundDesign: sap.m.BackgroundDesign.Transparent,
              title: Utils.getText('SEF_EVENT_PREPROCESSING_BEHAVIOR'),
              condensed: true
            }).addStyleClass('subscriberHeader'),

            new VerticalLayout({
                width: '100%',
                content: [
                  new Link({
                	  visible: {
                          parts: [
                            {path: '/externalLink'}
                          ],
                          formatter: function (externalLink) {
                            return !!externalLink;
                          }
                        },                	  
	                text: '{/externalLinkTitle}',
	                press: function () {
	                	oThis._externalLink = oThis.getModel().getProperty('/externalLink');
	                	subController.navigateExternalLink();
	                }
	              }).addStyleClass('sapUiSmallMarginBottom')
                ]
              	}).addStyleClass('sapUiContentPadding'),
              
            preprocessingCheckBox,
              
            new Label({
              text: Utils.getText('SEF_EVENT_PREPROCESSING_BEHAVIOR_LABEL_1')
            }).addStyleClass('sefTextVerticalAlignControl'),

            this._daysInput.addStyleClass('sapUiTinyMarginBeginEnd'),

            new Label({
              text: Utils.getText('SEF_EVENT_PREPROCESSING_BEHAVIOR_LABEL_2')
            }).addStyleClass('sefTextVerticalAlignControl'),

            
            new VerticalLayout({width: '100%'})
              .bindAggregation('content', '/moduleSubscriber', function (sId, oContext) {
                var impactAreasList = new List({
                  showNoData: false
                }).addStyleClass('sapUiTinyMarginTop');

                impactAreasList.bindAggregation('items', 'impactAreas', function (sInnerId, oInnerContext) {
                  return new CustomListItem({
                    content: [
                      new Title({
                        width: '100%',
                        text: Utils.getText(oInnerContext.getProperty())
                      }),
                      new Text({
                        text: oContext.getProperty('description')
                      }).addStyleClass('sapThemeLightText')
                    ]
                  }).addStyleClass('sefImpactAreaItem');
                });

                return new Panel({
                  content: [
                    new ObjectIdentifier({
                      title: Utils.getText('SEF_EVENT_IMPACT_AREAS')
                    }),
                    impactAreasList
                  ]
                }).addStyleClass('sefNoBorderContainer');
              })
          ]
        });

        var subscribersContainer = new VerticalLayout({
          visible: {
            parts: [
              {path: '/subscribers'}
            ],
            formatter: function (subscribers) {
              return !!(subscribers && subscribers.length > 0);
            }
          },
          width: '100%'
        }).addStyleClass('sapUiSmallMarginTopBottom');

        subscribersContainer.bindAggregation('content', '/subscribers', function (sId, oContext) {
          var impactAreasList = new List({
            showNoData: false
          }).addStyleClass('sapUiTinyMarginTop');

          impactAreasList.bindAggregation('items', 'subscribingImpactArea', function (sInnerId, oInnerContext) {
            return new CustomListItem({
              content: [
                new Title({
                  width: '100%',
                  text: Utils.getText(oInnerContext.getProperty())
                }),
                new Text({
                  text: oContext.getProperty('subscriberDescription')
                }).addStyleClass('sapThemeLightText')
              ]
            }).addStyleClass('sefImpactAreaItem');
          });

          return new Panel({
            headerToolbar: new Toolbar({
              content: [
                new Label({
                  design: sap.m.LabelDesign.Bold,
                  text: Utils.getText('SEF_EVENT_SUBSCRIBER') + ': ' + oContext.getProperty('subscriberName')
                })
              ]
            }),
            content: [
              new ObjectIdentifier({
                title: Utils.getText('SEF_EVENT_IMPACT_AREAS')
              }),
              impactAreasList
            ]
          }).addStyleClass('sefNoBorderContainer');
        });

        this._subscriberPanel = new Panel({
          headerToolbar: new Toolbar({
            content: [
              new Switch({
                state: '{/status}',
                change: $.proxy(this.switchSubscriber, this)
              })
            ]
          }),
          content: [preprocesingContainer, subscribersContainer]
        }).addStyleClass('subscriberDetailPanel');

        this._page = new Page({
          backgroundDesign: sap.m.PageBackgroundDesign.Solid,
          showHeader: false,
          busyIndicatorDelay: 0
        });

        return this._page;
      },

        
      createExternalLinkDialog: function () {
    	  var oThis = this;
    	  var oDialog = new sap.m.Dialog({
              title : Utils.getText('SEF_EXTERNALLINK_ALERT_DIALOG'),
              content : [ new sap.m.Text({
              text : Utils.getText('SEF_EXTERNALLINK_ALERT_DESC')
              })],
              beginButton : new sap.m.Button({
                  text : Utils.getText('COMMON_Cancel'),
                  press : function() {
                      oDialog.close();
                  }
              }),
              endButton : new sap.m.Button({
                  text : Utils.getText('COMMON_BTN_Continue'),
                  press : function() {
                	  
                	  if(oThis._externalLink) {
                		  window.setLocation(oThis._externalLink);
                	  }
                  }
              })
          });
    	  oDialog.open();
      },
      
      
      initView: function () {
        this._page.setShowFooter(true);
        this._page.setFooter(this._footer);
        this._page.addContent(this._subscriberPanel);
        this.setPanelEnabled(this.getModel().getProperty('/status'));
      },

      cleanView: function () {
        this.cleanValidationStatus();
        this._page.setShowFooter(false);
        this._page.removeAllContent();
      },

      onCancel: function () {
        this.cleanValidationStatus();
        this.getController().updateSubscriberModel();
        this.setPanelEnabled(this.getModel().getProperty('/status'));
      },

      onSave: function () {
        this.cleanValidationStatus();
        this.getController().save(this.getModel().getData());
      },

      switchSubscriber: function () {
        this._subscriberPanel.toggleStyleClass('subscriberPanelDisabled');
      },

      setLoading: function (isLoading) {
        this._page.setBusy(isLoading);
      },

      showSuccessNotification: function () {
        MessageToast.show(Utils.getText('COMMON_CHANGES_SAVED'));
      },

      setPanelEnabled: function (isEnabled) {
        if (isEnabled) {
          this._subscriberPanel.removeStyleClass('subscriberPanelDisabled');
        } else {
          this._subscriberPanel.addStyleClass('subscriberPanelDisabled');
        }
      },

      cleanValidationStatus: function () {
        this._daysInput.setValueState(sap.ui.core.ValueState.None);
      }
    });
  });