'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _rcRadio = require('rc-radio');

var _rcRadio2 = _interopRequireDefault(_rcRadio);

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var AntRadio = _react2.default.createClass({
  displayName: 'AntRadio',
  getDefaultProps: function getDefaultProps() {
    return {
      prefixCls: 'ant-radio'
    };
  },
  render: function render() {
    var _classNames;

    var _props = this.props;
    var prefixCls = _props.prefixCls;
    var children = _props.children;
    var checked = _props.checked;
    var disabled = _props.disabled;
    var className = _props.className;
    var style = _props.style;

    var classString = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, prefixCls, true), _defineProperty(_classNames, prefixCls + '-checked', checked), _defineProperty(_classNames, prefixCls + '-disabled', disabled), _defineProperty(_classNames, className, !!className), _classNames));
    return _react2.default.createElement(
      'label',
      { className: classString, style: style },
      _react2.default.createElement(_rcRadio2.default, _extends({}, this.props, { style: null, children: null })),
      children
    );
  }
});

exports.default = AntRadio;
module.exports = exports['default'];