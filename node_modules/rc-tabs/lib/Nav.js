'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _classnames3 = require('classnames');

var _classnames4 = _interopRequireDefault(_classnames3);

var _InkBarMixin = require('./InkBarMixin');

var _InkBarMixin2 = _interopRequireDefault(_InkBarMixin);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var tabBarExtraContentStyle = {
  float: 'right'
};

function noop() {}

var Nav = _react2.default.createClass({
  displayName: 'Nav',

  propTypes: {
    tabPosition: _react.PropTypes.string,
    tabBarExtraContent: _react.PropTypes.any,
    onTabClick: _react.PropTypes.func,
    onKeyDown: _react.PropTypes.func
  },

  mixins: [_InkBarMixin2.default],

  getInitialState: function getInitialState() {
    return {
      next: false,
      offset: 0,
      prev: false
    };
  },
  componentDidMount: function componentDidMount() {
    this.componentDidUpdate();
  },
  componentDidUpdate: function componentDidUpdate(prevProps) {
    var props = this.props;
    if (prevProps && prevProps.tabPosition !== props.tabPosition) {
      this.setOffset(0);
      return;
    }
    var navNode = this.refs.nav;
    var navNodeWH = this.getOffsetWH(navNode);
    var navWrapNode = this.refs.navWrap;
    var navWrapNodeWH = this.getOffsetWH(navWrapNode);
    var state = this.state;
    var offset = state.offset;
    var minOffset = navWrapNodeWH - navNodeWH;
    var _state = this.state;
    var next = _state.next;
    var prev = _state.prev;

    if (minOffset >= 0) {
      next = false;
      this.setOffset(0);
      offset = 0;
    } else if (minOffset < offset) {
      next = true;
    } else {
      next = false;
      this.setOffset(minOffset);
      offset = minOffset;
    }

    if (offset < 0) {
      prev = true;
    } else {
      prev = false;
    }

    this.setNext(next);
    this.setPrev(prev);

    var nextPrev = {
      next: next,
      prev: prev
    };
    // wait next,prev show hide
    if (this.isNextPrevShown(state) !== this.isNextPrevShown(nextPrev)) {
      this.setNextPrev({}, this.scrollToActiveTab);
    } else {
      // can not use props.activeKey
      if (!prevProps || props.activeKey !== prevProps.activeKey) {
        this.scrollToActiveTab();
      }
    }
  },
  onTabClick: function onTabClick(key) {
    this.props.onTabClick(key);
  },


  // work around eslint warning
  setNextPrev: function setNextPrev(nextPrev, callback) {
    this.setState(nextPrev, callback);
  },
  getTabs: function getTabs() {
    var _this = this;

    var props = this.props;
    var children = props.panels;
    var activeKey = props.activeKey;
    var rst = [];
    var prefixCls = props.prefixCls;

    _react2.default.Children.forEach(children, function (child) {
      var key = child.key;
      var cls = activeKey === key ? prefixCls + '-tab-active' : '';
      cls += ' ' + prefixCls + '-tab';
      var events = {};
      if (child.props.disabled) {
        cls += ' ' + prefixCls + '-tab-disabled';
      } else {
        events = {
          onClick: _this.onTabClick.bind(_this, key)
        };
      }
      var ref = {};
      if (activeKey === key) {
        ref.ref = 'activeTab';
      }
      rst.push(_react2.default.createElement(
        'div',
        _extends({}, events, {
          className: cls,
          key: key
        }, ref),
        _react2.default.createElement(
          'div',
          { className: prefixCls + '-tab-inner' },
          child.props.tab
        )
      ));
    });

    return rst;
  },
  getOffsetWH: function getOffsetWH(node) {
    var tabPosition = this.props.tabPosition;
    var prop = 'offsetWidth';
    if (tabPosition === 'left' || tabPosition === 'right') {
      prop = 'offsetHeight';
    }
    return node[prop];
  },
  getOffsetLT: function getOffsetLT(node) {
    var tabPosition = this.props.tabPosition;
    var prop = 'left';
    if (tabPosition === 'left' || tabPosition === 'right') {
      prop = 'top';
    }
    return node.getBoundingClientRect()[prop];
  },
  setOffset: function setOffset(offset) {
    var target = Math.min(0, offset);
    if (this.state.offset !== target) {
      this.setState({
        offset: target
      });
    }
  },
  setPrev: function setPrev(v) {
    if (this.state.prev !== v) {
      this.setState({
        prev: v
      });
    }
  },
  setNext: function setNext(v) {
    if (this.state.next !== v) {
      this.setState({
        next: v
      });
    }
  },
  isNextPrevShown: function isNextPrevShown(state) {
    return state.next || state.prev;
  },
  scrollToActiveTab: function scrollToActiveTab() {
    var _refs = this.refs;
    var activeTab = _refs.activeTab;
    var navWrap = _refs.navWrap;

    if (activeTab) {
      var activeTabWH = this.getOffsetWH(activeTab);
      var navWrapNodeWH = this.getOffsetWH(navWrap);
      var offset = this.state.offset;

      var wrapOffset = this.getOffsetLT(navWrap);
      var activeTabOffset = this.getOffsetLT(activeTab);
      if (wrapOffset > activeTabOffset) {
        offset += wrapOffset - activeTabOffset;
        this.setState({
          offset: offset
        });
      } else if (wrapOffset + navWrapNodeWH < activeTabOffset + activeTabWH) {
        offset -= activeTabOffset + activeTabWH - (wrapOffset + navWrapNodeWH);
        this.setState({
          offset: offset
        });
      }
    }
  },
  prev: function prev() {
    var navWrapNode = this.refs.navWrap;
    var navWrapNodeWH = this.getOffsetWH(navWrapNode);
    var state = this.state;
    var offset = state.offset;
    this.setOffset(offset + navWrapNodeWH);
  },
  next: function next() {
    var navWrapNode = this.refs.navWrap;
    var navWrapNodeWH = this.getOffsetWH(navWrapNode);
    var state = this.state;
    var offset = state.offset;
    this.setOffset(offset - navWrapNodeWH);
  },
  render: function render() {
    var props = this.props;
    var state = this.state;
    var prefixCls = props.prefixCls;
    var tabs = this.getTabs();
    var tabMovingDirection = props.tabMovingDirection;
    var tabPosition = props.tabPosition;
    var inkBarClass = prefixCls + '-ink-bar';
    if (tabMovingDirection) {
      inkBarClass += ' ' + prefixCls + '-ink-bar-transition-' + tabMovingDirection;
    }
    var nextButton = void 0;
    var prevButton = void 0;

    var showNextPrev = state.prev || state.next;

    if (showNextPrev) {
      var _classnames, _classnames2;

      prevButton = _react2.default.createElement(
        'span',
        {
          onClick: state.prev ? this.prev : noop,
          unselectable: 'unselectable',
          className: (0, _classnames4.default)((_classnames = {}, _defineProperty(_classnames, prefixCls + '-tab-prev', 1), _defineProperty(_classnames, prefixCls + '-tab-btn-disabled', !state.prev), _classnames))
        },
        _react2.default.createElement('span', { className: prefixCls + '-tab-prev-icon' })
      );

      nextButton = _react2.default.createElement(
        'span',
        {
          onClick: state.next ? this.next : noop,
          unselectable: 'unselectable',
          className: (0, _classnames4.default)((_classnames2 = {}, _defineProperty(_classnames2, prefixCls + '-tab-next', 1), _defineProperty(_classnames2, prefixCls + '-tab-btn-disabled', !state.next), _classnames2))
        },
        _react2.default.createElement('span', { className: prefixCls + '-tab-next-icon' })
      );
    }

    var navOffset = {};
    if (tabPosition === 'left' || tabPosition === 'right') {
      navOffset = {
        top: state.offset
      };
    } else {
      navOffset = {
        left: state.offset
      };
    }

    var tabBarExtraContent = this.props.tabBarExtraContent;

    return _react2.default.createElement(
      'div',
      {
        className: prefixCls + '-bar',
        tabIndex: '0',
        onKeyDown: this.props.onKeyDown
      },
      tabBarExtraContent ? _react2.default.createElement(
        'div',
        { style: tabBarExtraContentStyle },
        tabBarExtraContent
      ) : null,
      _react2.default.createElement(
        'div',
        {
          className: prefixCls + '-nav-container ' + (showNextPrev ? prefixCls + '-nav-container-scrolling' : ''),
          style: props.style,
          ref: 'container'
        },
        prevButton,
        nextButton,
        _react2.default.createElement(
          'div',
          { className: prefixCls + '-nav-wrap', ref: 'navWrap' },
          _react2.default.createElement(
            'div',
            { className: prefixCls + '-nav-scroll' },
            _react2.default.createElement(
              'div',
              { className: prefixCls + '-nav', ref: 'nav', style: navOffset },
              _react2.default.createElement('div', { className: inkBarClass, ref: 'inkBar' }),
              tabs
            )
          )
        )
      )
    );
  }
});

exports.default = Nav;
module.exports = exports['default'];