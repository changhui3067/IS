'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Modal = require('./Modal');

var _Modal2 = _interopRequireDefault(_Modal);

var _confirm = require('./confirm');

var _confirm2 = _interopRequireDefault(_confirm);

var _objectAssign = require('object-assign');

var _objectAssign2 = _interopRequireDefault(_objectAssign);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_Modal2.default.info = function (props) {
  var config = (0, _objectAssign2.default)({}, props, {
    iconClassName: 'info-circle',
    okCancel: false
  });
  return (0, _confirm2.default)(config);
};

_Modal2.default.success = function (props) {
  var config = (0, _objectAssign2.default)({}, props, {
    iconClassName: 'check-circle',
    okCancel: false
  });
  return (0, _confirm2.default)(config);
};

_Modal2.default.error = function (props) {
  var config = (0, _objectAssign2.default)({}, props, {
    iconClassName: 'cross-circle',
    okCancel: false
  });
  return (0, _confirm2.default)(config);
};

_Modal2.default.confirm = function (props) {
  var config = (0, _objectAssign2.default)({}, props, {
    okCancel: true
  });
  return (0, _confirm2.default)(config);
};

exports.default = _Modal2.default;
module.exports = exports['default'];