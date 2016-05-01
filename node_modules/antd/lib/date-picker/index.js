'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _rcCalendar = require('rc-calendar');

var _rcCalendar2 = _interopRequireDefault(_rcCalendar);

var _MonthCalendar = require('rc-calendar/lib/MonthCalendar');

var _MonthCalendar2 = _interopRequireDefault(_MonthCalendar);

var _Picker = require('rc-calendar/lib/Picker');

var _Picker2 = _interopRequireDefault(_Picker);

var _gregorianCalendar = require('gregorian-calendar');

var _gregorianCalendar2 = _interopRequireDefault(_gregorianCalendar);

var _zh_CN = require('rc-calendar/lib/locale/zh_CN');

var _zh_CN2 = _interopRequireDefault(_zh_CN);

var _RangePicker = require('./RangePicker');

var _RangePicker2 = _interopRequireDefault(_RangePicker);

var _PickerMixin = require('./PickerMixin');

var _PickerMixin2 = _interopRequireDefault(_PickerMixin);

var _rcTimePicker = require('rc-time-picker');

var _rcTimePicker2 = _interopRequireDefault(_rcTimePicker);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function createPicker(TheCalendar, defaultFormat) {
  return _react2.default.createClass({
    getDefaultProps: function getDefaultProps() {
      return {
        format: defaultFormat || 'yyyy-MM-dd',
        transitionName: 'slide-up',
        popupStyle: {},
        onChange: function onChange() {},
        onOk: function onOk() {},

        locale: {},
        align: {
          offset: [0, -9]
        },
        open: false
      };
    },
    getInitialState: function getInitialState() {
      return {
        value: this.parseDateFromValue(this.props.value || this.props.defaultValue)
      };
    },

    mixins: [_PickerMixin2.default],
    componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
      if ('value' in nextProps) {
        this.setState({
          value: this.parseDateFromValue(nextProps.value)
        });
      }
    },
    handleChange: function handleChange(value) {
      if (!('value' in this.props)) {
        this.setState({ value: value });
      }
      var timeValue = value ? new Date(value.getTime()) : null;
      this.props.onChange(timeValue);
    },
    render: function render() {
      var _classNames,
          _this = this;

      var locale = this.getLocale();
      // 以下两行代码
      // 给没有初始值的日期选择框提供本地化信息
      // 否则会以周日开始排
      var defaultCalendarValue = new _gregorianCalendar2.default(locale);
      defaultCalendarValue.setTime(Date.now());

      var placeholder = 'placeholder' in this.props ? this.props.placeholder : locale.lang.placeholder;

      var timePicker = this.props.showTime ? _react2.default.createElement(_rcTimePicker2.default, {
        prefixCls: 'ant-time-picker',
        placeholder: locale.timePickerLocale.placeholder,
        locale: locale.timePickerLocale,
        transitionName: 'slide-up' }) : null;

      var disabledTime = this.props.showTime ? this.props.disabledTime : null;

      var calendarClassName = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, 'ant-calendar-time', this.props.showTime), _defineProperty(_classNames, 'ant-calendar-month', _MonthCalendar2.default === TheCalendar), _classNames));

      var pickerChangeHandler = {
        onChange: this.handleChange
      };

      var calendarHandler = {
        onOk: this.handleChange
      };

      if (this.props.showTime) {
        pickerChangeHandler.onChange = function (value) {
          // Click clear button
          if (value === null) {
            _this.handleChange(value);
          }
        };
      } else {
        calendarHandler = {};
      }

      var calendar = _react2.default.createElement(TheCalendar, _extends({
        formatter: this.getFormatter(),
        disabledDate: this.props.disabledDate,
        disabledTime: disabledTime,
        locale: locale.lang,
        timePicker: timePicker,
        defaultValue: defaultCalendarValue,
        dateInputPlaceholder: placeholder,
        prefixCls: 'ant-calendar',
        className: calendarClassName
      }, calendarHandler));

      var sizeClass = '';
      if (this.props.size === 'large') {
        sizeClass = ' ant-input-lg';
      } else if (this.props.size === 'small') {
        sizeClass = ' ant-input-sm';
      }

      var pickerClass = 'ant-calendar-picker';
      if (this.state.open) {
        pickerClass += ' ant-calendar-picker-open';
      }

      // default width for showTime
      var pickerStyle = {};
      if (this.props.showTime) {
        pickerStyle.width = 180;
      }

      return _react2.default.createElement(
        'span',
        { className: pickerClass, style: _extends({}, pickerStyle, this.props.style) },
        _react2.default.createElement(
          _Picker2.default,
          _extends({
            transitionName: this.props.transitionName,
            disabled: this.props.disabled,
            calendar: calendar,
            value: this.state.value,
            prefixCls: 'ant-calendar-picker-container',
            style: this.props.popupStyle,
            align: this.props.align,
            getCalendarContainer: this.props.getCalendarContainer,
            onOpen: this.toggleOpen,
            onClose: this.toggleOpen
          }, pickerChangeHandler),
          function (_ref) {
            var value = _ref.value;

            return _react2.default.createElement(
              'span',
              null,
              _react2.default.createElement('input', {
                disabled: _this.props.disabled,
                onChange: _this.handleInputChange,
                value: value && _this.getFormatter().format(value),
                placeholder: placeholder,
                className: 'ant-calendar-picker-input ant-input' + sizeClass }),
              _react2.default.createElement('span', { className: 'ant-calendar-picker-icon' })
            );
          }
        )
      );
    }
  });
}

var AntDatePicker = createPicker(_rcCalendar2.default);
var AntMonthPicker = createPicker(_MonthCalendar2.default, 'yyyy-MM');

var AntCalendar = _react2.default.createClass({
  displayName: 'AntCalendar',
  getDefaultProps: function getDefaultProps() {
    return {
      locale: _zh_CN2.default,
      prefixCls: 'ant-calendar'
    };
  },
  render: function render() {
    return _react2.default.createElement(_rcCalendar2.default, this.props);
  }
});

AntDatePicker.Calendar = AntCalendar;
AntDatePicker.RangePicker = _RangePicker2.default;
AntDatePicker.MonthPicker = AntMonthPicker;

exports.default = AntDatePicker;
module.exports = exports['default'];