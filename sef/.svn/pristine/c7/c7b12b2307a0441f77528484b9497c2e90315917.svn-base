(function () {
  var queryString = window.location.search.slice(1).split('&');
  var queryStringMap = {};
  for (var i = 0, length = queryString.length; i < length; i++) {
    var qs = queryString[i].split('=');
    queryStringMap[qs[0]] = qs[1];
  }

  var selectedDate = queryStringMap.selectedDate;
  var sRuleId = queryStringMap.ruleId;
  var RULE_CONTROLLER = AjaxService.getMBeanInstance('ruleEditorController');
  var OOW_CONTROLLER = AjaxService.getMBeanInstance('OOWController');

  RULE_CONTROLLER.getRule(sRuleId, selectedDate, RE.createMBeanCallback(this, function (rule) {
    if (rule.ruleScenarioCode != null) {
      OOW_CONTROLLER.getBeans('com.successfactors.wing.api.RuleScenario', 'code', rule.ruleScenarioCode, {
        async: false,
        callback: function (scenarist) {
          if (scenarist[0] != null) {
            rule.scenarioDescription = scenarist[0].description;
          }
        },
        errorHandler: function (message, error) {

        }
      });
    }
    var html = juic.createRenderContext();
    var titleBar = "<h1 class='globalPageTitle'>" + rule.name + " (" + rule.code + ")" + "</h1>";

    var ruleEditor = new RERuleEditor(rule, {
      readOnly:true
    });

    html.push(titleBar);
    html.push(ruleEditor);
    html.renderInto('sefRuleEmbedContent');
  }, function () {
    //on error function
  }));
})();