'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _PanelContent = require('./PanelContent');

var _PanelContent2 = _interopRequireDefault(_PanelContent);

var _rcAnimate = require('rc-animate');

var _rcAnimate2 = _interopRequireDefault(_rcAnimate);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var CollapsePanel = _react2["default"].createClass({
  displayName: 'CollapsePanel',

  propTypes: {
    children: _react.PropTypes.any,
    openAnimation: _react.PropTypes.object,
    prefixCls: _react.PropTypes.string,
    header: _react.PropTypes.oneOfType([_react.PropTypes.string, _react.PropTypes.number, _react.PropTypes.node]),
    isActive: _react.PropTypes.bool,
    onItemClick: _react.PropTypes.func
  },

  getDefaultProps: function getDefaultProps() {
    return {
      isActive: false,
      onItemClick: function onItemClick() {}
    };
  },
  handleItemClick: function handleItemClick() {
    this.props.onItemClick();
  },
  render: function render() {
    var _props = this.props;
    var prefixCls = _props.prefixCls;
    var header = _props.header;
    var children = _props.children;
    var isActive = _props.isActive;

    var headerCls = prefixCls + '-header';
    return _react2["default"].createElement(
      'div',
      { className: prefixCls + '-item' },
      _react2["default"].createElement(
        'div',
        {
          className: headerCls,
          onClick: this.handleItemClick,
          role: 'tab',
          'aria-expanded': isActive
        },
        _react2["default"].createElement('i', { className: 'arrow' }),
        header
      ),
      _react2["default"].createElement(
        _rcAnimate2["default"],
        {
          showProp: 'isActive',
          exclusive: true,
          component: '',
          animation: this.props.openAnimation
        },
        _react2["default"].createElement(
          _PanelContent2["default"],
          { prefixCls: prefixCls, isActive: isActive },
          children
        )
      )
    );
  }
});

exports["default"] = CollapsePanel;
module.exports = exports['default'];