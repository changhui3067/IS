'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _rcFormValidation = require('rc-form-validation');

var _rcFormValidation2 = _interopRequireDefault(_rcFormValidation);

var _warning = require('warning');

var _warning2 = _interopRequireDefault(_warning);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var AntValidation = function (_React$Component) {
  _inherits(AntValidation, _React$Component);

  function AntValidation() {
    _classCallCheck(this, AntValidation);

    return _possibleConstructorReturn(this, Object.getPrototypeOf(AntValidation).apply(this, arguments));
  }

  _createClass(AntValidation, [{
    key: 'validate',
    value: function validate(callback) {
      this.refs.validation.validate(callback);
    }
  }, {
    key: 'reset',
    value: function reset() {
      this.refs.validation.reset();
    }
  }, {
    key: 'forceValidate',
    value: function forceValidate(fields, callback) {
      this.refs.validation.forceValidate(fields, callback);
    }
  }, {
    key: 'render',
    value: function render() {
      (0, _warning2.default)(false, '`Validation` is deprecated, please use `Form` which has supported validation after antd@0.12.0.');
      return _react2.default.createElement(_rcFormValidation2.default, _extends({}, this.props, { ref: 'validation' }));
    }
  }]);

  return AntValidation;
}(_react2.default.Component);

exports.default = AntValidation;


AntValidation.Validator = _rcFormValidation2.default.Validator;
AntValidation.FieldMixin = _rcFormValidation2.default.FieldMixin;
module.exports = exports['default'];