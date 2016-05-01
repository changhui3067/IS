'use strict';

var velocity = void 0;
if (typeof document !== 'undefined' && typeof window !== 'undefined') {
  velocity = require('velocity-animate');
}

function animate(node, show, transitionName, done) {
  var ok = void 0;

  function complete() {
    if (!ok) {
      ok = true;
      done();
    }
  }

  // Fix safari flash bug
  /*eslint-disable */
  node.style.display = show ? 'block' : 'none';
  /*eslint-enable */
  velocity(node, transitionName, {
    duration: 240,
    complete: complete,
    easing: 'easeInOutQuad'
  });
  return {
    stop: function stop() {
      velocity(node, 'finish');
      complete();
    }
  };
}

var animation = {
  enter: function enter(node, done) {
    return animate(node, false, 'slideDown', done);
  },
  leave: function leave(node, done) {
    return animate(node, true, 'slideUp', done);
  },
  appear: function appear(node, done) {
    return animate(node, false, 'slideDown', done);
  }
};

module.exports = animation;