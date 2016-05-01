'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _gregorianCalendarFormat = require('gregorian-calendar-format');

var _gregorianCalendarFormat2 = _interopRequireDefault(_gregorianCalendarFormat);

var _TimePicker = require('rc-time-picker/lib/TimePicker');

var _TimePicker2 = _interopRequireDefault(_TimePicker);

var _objectAssign = require('object-assign');

var _objectAssign2 = _interopRequireDefault(_objectAssign);

var _zh_CN = require('./locale/zh_CN');

var _zh_CN2 = _interopRequireDefault(_zh_CN);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

var _gregorianCalendar = require('gregorian-calendar');

var _gregorianCalendar2 = _interopRequireDefault(_gregorianCalendar);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var AntTimePicker = _react2.default.createClass({
  displayName: 'AntTimePicker',
  getDefaultProps: function getDefaultProps() {
    return {
      format: 'HH:mm:ss',
      prefixCls: 'ant-time-picker',
      onChange: function onChange() {},

      locale: {},
      align: {
        offset: [0, -2]
      },
      disabled: false,
      disabledHours: undefined,
      disabledMinutes: undefined,
      disabledSeconds: undefined,
      hideDisabledOptions: false,
      placement: 'bottomLeft',
      transitionName: 'slide-up'
    };
  },
  getFormatter: function getFormatter() {
    return new _gregorianCalendarFormat2.default(this.props.format);
  },


  /**
   * 获得输入框的 className
   */
  getSizeClass: function getSizeClass() {
    var sizeClass = '';
    if (this.props.size === 'large') {
      sizeClass = ' ant-input-lg';
    } else if (this.props.size === 'small') {
      sizeClass = ' ant-input-sm';
    }
    return sizeClass;
  },


  /**
   * 获得输入框的默认值
   */
  parseTimeFromValue: function parseTimeFromValue(value) {
    if (value) {
      if (typeof value === 'string') {
        return this.getFormatter().parse(value, {
          locale: this.getLocale().calendar,
          obeyCount: true
        });
      } else if (value instanceof Date) {
        var date = new _gregorianCalendar2.default(this.getLocale().calendar);
        date.setTime(+value);
        return date;
      }
    }
    return value;
  },
  handleChange: function handleChange(value) {
    this.props.onChange(value ? new Date(value.getTime()) : null);
  },
  getLocale: function getLocale() {
    // 统一合并为完整的 Locale
    return (0, _objectAssign2.default)({}, _zh_CN2.default, this.props.locale);
  },
  render: function render() {
    var _classNames;

    var props = (0, _objectAssign2.default)({}, this.props);
    props.placeholder = 'placeholder' in this.props ? props.placeholder : this.getLocale().placeholder;
    if (props.defaultValue) {
      props.defaultValue = this.parseTimeFromValue(props.defaultValue);
    } else {
      delete props.defaultValue;
    }
    if (props.value) {
      props.value = this.parseTimeFromValue(props.value);
    }
    var className = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, props.className, !!props.className), _defineProperty(_classNames, props.prefixCls + '-' + props.size, !!props.size), _classNames));
    if (props.format.indexOf('ss') < 0) {
      props.showSecond = false;
    }
    if (props.format.indexOf('HH') < 0) {
      props.showHour = false;
    }

    return _react2.default.createElement(_TimePicker2.default, _extends({}, props, {
      className: className,
      locale: this.getLocale(),
      formatter: this.getFormatter(),
      onChange: this.handleChange
    }));
  }
});

exports.default = AntTimePicker;
module.exports = exports['default'];