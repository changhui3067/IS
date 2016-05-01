'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _objectAssign = require('object-assign');

var _objectAssign2 = _interopRequireDefault(_objectAssign);

var _en_US = require('gregorian-calendar/lib/locale/en_US');

var _en_US2 = _interopRequireDefault(_en_US);

var _en_US3 = require('rc-calendar/lib/locale/en_US');

var _en_US4 = _interopRequireDefault(_en_US3);

var _en_US5 = require('../../time-picker/locale/en_US');

var _en_US6 = _interopRequireDefault(_en_US5);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// 统一合并为完整的 Locale
var locale = (0, _objectAssign2.default)({}, _en_US2.default);
locale.lang = (0, _objectAssign2.default)({
  placeholder: 'Select date'
}, _en_US4.default);

locale.timePickerLocale = (0, _objectAssign2.default)({}, _en_US6.default);

// All settings at:
// https://github.com/ant-design/ant-design/issues/424

exports.default = locale;
module.exports = exports['default'];