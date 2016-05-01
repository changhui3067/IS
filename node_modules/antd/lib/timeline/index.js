'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var TimelineItem = _react2.default.createClass({
  displayName: 'TimelineItem',
  getDefaultProps: function getDefaultProps() {
    return {
      prefixCls: 'ant-timeline',
      color: 'blue',
      last: false,
      pending: false
    };
  },
  render: function render() {
    var _classNames;

    var _props = this.props;
    var prefixCls = _props.prefixCls;
    var color = _props.color;
    var last = _props.last;
    var children = _props.children;
    var pending = _props.pending;

    var itemClassName = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, prefixCls + '-item', true), _defineProperty(_classNames, prefixCls + '-item-last', last), _defineProperty(_classNames, prefixCls + '-item-pending', pending), _classNames));
    return _react2.default.createElement(
      'li',
      { className: itemClassName },
      _react2.default.createElement('div', { className: prefixCls + '-item-tail' }),
      _react2.default.createElement('div', { className: prefixCls + '-item-head ' + prefixCls + '-item-head-' + color }),
      _react2.default.createElement(
        'div',
        { className: prefixCls + '-item-content' },
        children
      )
    );
  }
});

var Timeline = _react2.default.createClass({
  displayName: 'Timeline',
  getDefaultProps: function getDefaultProps() {
    return {
      prefixCls: 'ant-timeline'
    };
  },
  render: function render() {
    var _classNames2;

    var _props2 = this.props;
    var prefixCls = _props2.prefixCls;
    var children = _props2.children;
    var pending = _props2.pending;

    var pendingNode = typeof pending === 'boolean' ? null : pending;
    var className = (0, _classnames2.default)((_classNames2 = {}, _defineProperty(_classNames2, prefixCls, true), _defineProperty(_classNames2, prefixCls + '-pending', !!pending), _classNames2));
    return _react2.default.createElement(
      'ul',
      { className: className },
      _react2.default.Children.map(children, function (ele, idx) {
        return _react2.default.cloneElement(ele, {
          last: idx === children.length - 1
        });
      }),
      !!pending ? _react2.default.createElement(
        TimelineItem,
        { pending: !!pending },
        pendingNode
      ) : null
    );
  }
});

Timeline.Item = TimelineItem;

exports.default = Timeline;
module.exports = exports['default'];