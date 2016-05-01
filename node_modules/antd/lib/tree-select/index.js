'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _rcTreeSelect = require('rc-tree-select');

var _rcTreeSelect2 = _interopRequireDefault(_rcTreeSelect);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var AntTreeSelect = _react2.default.createClass({
  displayName: 'AntTreeSelect',
  getDefaultProps: function getDefaultProps() {
    return {
      prefixCls: 'ant-select',
      transitionName: 'slide-up',
      choiceTransitionName: 'zoom',
      showSearch: false
    };
  },
  render: function render() {
    var _classNames;

    var props = this.props;
    var _props = this.props;
    var size = _props.size;
    var className = _props.className;
    var combobox = _props.combobox;
    var notFoundContent = _props.notFoundContent;
    var prefixCls = _props.prefixCls;


    var cls = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, prefixCls + '-lg', size === 'large'), _defineProperty(_classNames, prefixCls + '-sm', size === 'small'), _defineProperty(_classNames, className, !!className), _classNames));

    if (combobox) {
      notFoundContent = null;
    }

    var checkable = props.treeCheckable;
    if (checkable) {
      checkable = _react2.default.createElement('span', { className: prefixCls + '-tree-checkbox-inner' });
    }

    return _react2.default.createElement(_rcTreeSelect2.default, _extends({}, this.props, {
      treeCheckable: checkable,
      className: cls,
      notFoundContent: notFoundContent }));
  }
});

AntTreeSelect.TreeNode = _rcTreeSelect.TreeNode;
AntTreeSelect.SHOW_ALL = _rcTreeSelect.SHOW_ALL;
AntTreeSelect.SHOW_PARENT = _rcTreeSelect.SHOW_PARENT;
AntTreeSelect.SHOW_CHILD = _rcTreeSelect.SHOW_CHILD;
exports.default = AntTreeSelect;
module.exports = exports['default'];