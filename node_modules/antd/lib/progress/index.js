'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _rcProgress = require('rc-progress');

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _objectAssign = require('object-assign');

var _objectAssign2 = _interopRequireDefault(_objectAssign);

var _warning = require('warning');

var _warning2 = _interopRequireDefault(_warning);

var _icon = require('../icon');

var _icon2 = _interopRequireDefault(_icon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var prefixCls = 'ant-progress';

var statusColorMap = {
  normal: '#2db7f5',
  exception: '#ff5500',
  success: '#87d068'
};

var Line = _react2.default.createClass({
  displayName: 'Line',

  propTypes: {
    status: _react2.default.PropTypes.oneOf(['normal', 'exception', 'active', 'success']),
    showInfo: _react2.default.PropTypes.bool,
    percent: _react2.default.PropTypes.number,
    strokeWidth: _react2.default.PropTypes.number,
    trailColor: _react2.default.PropTypes.string,
    format: _react2.default.PropTypes.oneOfType([_react2.default.PropTypes.node, _react2.default.PropTypes.string, _react2.default.PropTypes.func])
  },
  getDefaultProps: function getDefaultProps() {
    return {
      percent: 0,
      strokeWidth: 10,
      status: 'normal', // exception active
      showInfo: true,
      trailColor: '#f3f3f3'
    };
  },
  render: function render() {
    var props = (0, _objectAssign2.default)({}, this.props);

    if (parseInt(props.percent, 10) === 100) {
      props.status = 'success';
    }

    var progressInfo = void 0;
    var fullCls = '';

    if (props.format) {
      (0, _warning2.default)(typeof props.format === 'function', 'antd.Progress props.format type is function, change format={xxx} to format={() => xxx}');
    }

    var text = props.format || props.percent + '%';
    if (typeof props.format === 'string') {
      // 向下兼容原来的字符串替换方式
      text = props.format.replace('${percent}', props.percent);
    } else if (typeof props.format === 'function') {
      text = props.format(props.percent);
    }

    if (props.showInfo) {
      if (props.status === 'exception') {
        progressInfo = _react2.default.createElement(
          'span',
          { className: prefixCls + '-line-text' },
          props.format ? text : _react2.default.createElement(_icon2.default, { type: 'cross-circle' })
        );
      } else if (props.status === 'success') {
        progressInfo = _react2.default.createElement(
          'span',
          { className: prefixCls + '-line-text' },
          props.format ? text : _react2.default.createElement(_icon2.default, { type: 'check-circle' })
        );
      } else {
        progressInfo = _react2.default.createElement(
          'span',
          { className: prefixCls + '-line-text' },
          text
        );
      }
    } else {
      fullCls = ' ' + prefixCls + '-line-wrap-full';
    }
    var percentStyle = {
      width: props.percent + '%',
      height: props.strokeWidth
    };

    return _react2.default.createElement(
      'div',
      { className: prefixCls + '-line-wrap clearfix status-' + props.status + fullCls, style: props.style },
      _react2.default.createElement(
        'div',
        { className: prefixCls + '-line-outer' },
        _react2.default.createElement(
          'div',
          { className: prefixCls + '-line-inner' },
          _react2.default.createElement('div', { className: prefixCls + '-line-bg', style: percentStyle })
        )
      ),
      progressInfo
    );
  }
});

var Circle = _react2.default.createClass({
  displayName: 'Circle',

  propTypes: {
    status: _react2.default.PropTypes.oneOf(['normal', 'exception', 'success']),
    percent: _react2.default.PropTypes.number,
    strokeWidth: _react2.default.PropTypes.number,
    width: _react2.default.PropTypes.number,
    trailColor: _react2.default.PropTypes.string,
    format: _react2.default.PropTypes.oneOfType([_react2.default.PropTypes.node, _react2.default.PropTypes.string, _react2.default.PropTypes.func])
  },
  getDefaultProps: function getDefaultProps() {
    return {
      width: 132,
      percent: 0,
      strokeWidth: 6,
      status: 'normal', // exception
      trailColor: '#f3f3f3'
    };
  },
  render: function render() {
    var props = (0, _objectAssign2.default)({}, this.props);

    if (parseInt(props.percent, 10) === 100) {
      props.status = 'success';
    }

    var style = {
      width: props.width,
      height: props.width,
      fontSize: props.width * 0.16 + 6
    };
    var progressInfo = void 0;
    var text = props.format || props.percent + '%';

    if (props.format) {
      (0, _warning2.default)(typeof props.format === 'function', 'antd.Progress props.format type is function, change format={xxx} to format={() => xxx}');
    }

    if (typeof props.format === 'string') {
      // 向下兼容原来的字符串替换方式
      text = props.format.replace('${percent}', props.percent);
    } else if (typeof props.format === 'function') {
      text = props.format(props.percent);
    }

    if (props.status === 'exception') {
      progressInfo = _react2.default.createElement(
        'span',
        { className: prefixCls + '-circle-text' },
        props.format ? text : _react2.default.createElement(_icon2.default, { type: 'exclamation' })
      );
    } else if (props.status === 'success') {
      progressInfo = _react2.default.createElement(
        'span',
        { className: prefixCls + '-circle-text' },
        props.format ? text : _react2.default.createElement(_icon2.default, { type: 'check' })
      );
    } else {
      progressInfo = _react2.default.createElement(
        'span',
        { className: prefixCls + '-circle-text' },
        text
      );
    }

    return _react2.default.createElement(
      'div',
      { className: prefixCls + '-circle-wrap status-' + props.status, style: props.style },
      _react2.default.createElement(
        'div',
        { className: prefixCls + '-circle-inner', style: style },
        _react2.default.createElement(_rcProgress.Circle, { percent: props.percent, strokeWidth: props.strokeWidth,
          strokeColor: statusColorMap[props.status], trailColor: props.trailColor }),
        progressInfo
      )
    );
  }
});

exports.default = {
  Line: Line,
  Circle: Circle
};
module.exports = exports['default'];