'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

var _radio = require('./radio');

var _radio2 = _interopRequireDefault(_radio);

var _radioButton = require('./radioButton');

var _radioButton2 = _interopRequireDefault(_radioButton);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function getCheckedValue(children) {
  var value = null;
  var matched = false;
  _react2.default.Children.forEach(children, function (radio) {
    if (radio && radio.props && radio.props.checked) {
      value = radio.props.value;
      matched = true;
    }
  });
  return matched ? { value: value } : undefined;
}

exports.default = _react2.default.createClass({
  displayName: 'group',
  getDefaultProps: function getDefaultProps() {
    return {
      prefixCls: 'ant-radio-group',
      disabled: false,
      onChange: function onChange() {}
    };
  },
  getInitialState: function getInitialState() {
    var props = this.props;
    var value = void 0;
    if ('value' in props) {
      value = props.value;
    } else if ('defaultValue' in props) {
      value = props.defaultValue;
    } else {
      var checkedValue = getCheckedValue(props.children);
      value = checkedValue && checkedValue.value;
    }
    return {
      value: value
    };
  },
  componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
    if ('value' in nextProps) {
      this.setState({
        value: nextProps.value
      });
    } else {
      var checkedValue = getCheckedValue(nextProps.children);
      if (checkedValue) {
        this.setState({
          value: checkedValue.value
        });
      }
    }
  },
  onRadioChange: function onRadioChange(ev) {
    if (!('value' in this.props)) {
      this.setState({
        value: ev.target.value
      });
    }
    this.props.onChange(ev);
  },
  render: function render() {
    var _this = this,
        _classNames;

    var props = this.props;
    var children = _react2.default.Children.map(props.children, function (radio) {
      if (radio && (radio.type === _radio2.default || radio.type === _radioButton2.default) && radio.props) {
        var keyProps = {};
        if (!('key' in radio) && typeof radio.props.value === 'string') {
          keyProps.key = radio.props.value;
        }
        return _react2.default.cloneElement(radio, _extends({}, keyProps, radio.props, {
          onChange: _this.onRadioChange,
          checked: _this.state.value === radio.props.value,
          disabled: radio.props.disabled || _this.props.disabled
        }));
      }
      return radio;
    });
    var classString = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, props.prefixCls, true), _defineProperty(_classNames, props.prefixCls + '-' + props.size, props.size), _classNames));
    return _react2.default.createElement(
      'div',
      { className: classString, style: props.style },
      children
    );
  }
});
module.exports = exports['default'];