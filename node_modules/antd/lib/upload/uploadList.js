'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _rcAnimate = require('rc-animate');

var _rcAnimate2 = _interopRequireDefault(_rcAnimate);

var _icon = require('../icon');

var _icon2 = _interopRequireDefault(_icon);

var _progress = require('../progress');

var _classnames = require('classnames');

var _classnames2 = _interopRequireDefault(_classnames);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var prefixCls = 'ant-upload';


// https://developer.mozilla.org/en-US/docs/Web/API/FileReader/readAsDataURL
var previewFile = function previewFile(file, callback) {
  var reader = new FileReader();
  reader.onloadend = function () {
    callback(reader.result);
  };
  reader.readAsDataURL(file);
};

exports.default = _react2.default.createClass({
  displayName: 'uploadList',
  getDefaultProps: function getDefaultProps() {
    return {
      listType: 'text', // or picture
      items: [],
      progressAttr: {
        strokeWidth: 3,
        showInfo: false
      }
    };
  },
  handleClose: function handleClose(file) {
    this.props.onRemove(file);
  },
  componentDidUpdate: function componentDidUpdate() {
    var _this = this;

    if (this.props.listType !== 'picture' && this.props.listType !== 'picture-card') {
      return;
    }
    this.props.items.forEach(function (file) {
      if (typeof document === 'undefined' || typeof window === 'undefined' || !window.FileReader || !window.File || !(file.originFileObj instanceof File) || file.thumbUrl !== undefined) {
        return;
      }
      /*eslint-disable */
      file.thumbUrl = '';
      /*eslint-enable */
      previewFile(file.originFileObj, function (previewDataUrl) {
        /*eslint-disable */
        file.thumbUrl = previewDataUrl;
        /*eslint-enable */
        _this.forceUpdate();
      });
    });
  },
  render: function render() {
    var _this2 = this,
        _classNames2;

    var list = this.props.items.map(function (file) {
      var _classNames;

      var progress = void 0;
      var icon = _react2.default.createElement(_icon2.default, { type: 'paper-clip' });

      if (_this2.props.listType === 'picture' || _this2.props.listType === 'picture-card') {
        if (file.status === 'uploading' || !file.thumbUrl && !file.url) {
          if (_this2.props.listType === 'picture-card') {
            icon = _react2.default.createElement(
              'div',
              { className: prefixCls + '-list-item-uploading-text' },
              '文件上传中'
            );
          } else {
            icon = _react2.default.createElement(_icon2.default, { className: prefixCls + '-list-item-thumbnail', type: 'picture' });
          }
        } else {
          icon = _react2.default.createElement(
            'a',
            { className: prefixCls + '-list-item-thumbnail',
              href: file.url,
              target: '_blank' },
            _react2.default.createElement('img', { src: file.thumbUrl || file.url, alt: file.name })
          );
        }
      }

      if (file.status === 'uploading') {
        progress = _react2.default.createElement(
          'div',
          { className: prefixCls + '-list-item-progress' },
          _react2.default.createElement(_progress.Line, _extends({}, _this2.props.progressAttr, { percent: file.percent }))
        );
      }
      var infoUploadingClass = (0, _classnames2.default)((_classNames = {}, _defineProperty(_classNames, prefixCls + '-list-item', true), _defineProperty(_classNames, prefixCls + '-list-item-' + file.status, true), _classNames));
      return _react2.default.createElement(
        'div',
        { className: infoUploadingClass, key: file.uid },
        _react2.default.createElement(
          'div',
          { className: prefixCls + '-list-item-info' },
          icon,
          file.url ? _react2.default.createElement(
            'a',
            { href: file.url, target: '_blank', className: prefixCls + '-list-item-name' },
            file.name
          ) : _react2.default.createElement(
            'span',
            { className: prefixCls + '-list-item-name' },
            file.name
          ),
          _this2.props.listType === 'picture-card' && file.status !== 'uploading' ? _react2.default.createElement(
            'span',
            null,
            _react2.default.createElement(
              'a',
              { href: file.url, target: '_blank', style: { pointerEvents: file.url ? '' : 'none' } },
              _react2.default.createElement(_icon2.default, { type: 'eye-o' })
            ),
            _react2.default.createElement(_icon2.default, { type: 'delete', onClick: _this2.handleClose.bind(_this2, file) })
          ) : _react2.default.createElement(_icon2.default, { type: 'cross', onClick: _this2.handleClose.bind(_this2, file) })
        ),
        progress
      );
    });
    var listClassNames = (0, _classnames2.default)((_classNames2 = {}, _defineProperty(_classNames2, prefixCls + '-list', true), _defineProperty(_classNames2, prefixCls + '-list-' + this.props.listType, true), _classNames2));
    return _react2.default.createElement(
      'div',
      { className: listClassNames },
      _react2.default.createElement(
        _rcAnimate2.default,
        { transitionName: prefixCls + '-margin-top' },
        list
      )
    );
  }
});
module.exports = exports['default'];