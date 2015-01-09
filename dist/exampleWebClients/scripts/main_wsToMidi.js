window.onload = init;

var midi;
var ui = {};
var noteOffTimeout = 50;

function init () {
  buildInterface();
  midi = new WsMidi.Connection({
    ip: location.hostname,
    port: 8080
  });
}

function buildInterface () {
  ui.button01 = new TouchLib.TriggerButton({
    elementId: 'triggerButton1',
    triggerTimeout: 150,
    on: {
      innerHTML: '!!!',
      border: '3px solid #22cc22',
      background: '#669966',
      color: '#222222'
    },
    off: {
      innerHTML: 'trigger',
      border: '3px solid #2222cc',
      background: '#666699',
      color: '#222222'
    },
    cssClassName: 'button',
    notify: function () {
      midi.send(new WsMidi.Message(144, 4, 60, 100));
      setTimeout(function () {
        midi.send(new WsMidi.Message(128, 4, 60, 100));
      }, noteOffTimeout);
    }
  });

  ui.button02 = new TouchLib.TriggerButton({
    elementId: 'triggerButton2',
    triggerTimeout: 150,
    on: {
      innerHTML: '!!!',
      border: '3px solid #22cc22',
      background: '#669966',
      color: '#222222'
    },
    off: {
      innerHTML: 'trigger',
      border: '3px solid #2222cc',
      background: '#666699',
      color: '#222222'
    },
    cssClassName: 'button',
    notify: function () {
      midi.send(new WsMidi.Message(144, 4, 61, 100));
      setTimeout(function () {
        midi.send(new WsMidi.Message(128, 4, 61, 100));
      }, noteOffTimeout);
    }
  });

  ui.button03 = new TouchLib.TriggerButton({
    elementId: 'triggerButton3',
    triggerTimeout: 150,
    on: {
      innerHTML: '!!!',
      border: '3px solid #22cc22',
      background: '#669966',
      color: '#222222'
    },
    off: {
      innerHTML: 'trigger',
      border: '3px solid #2222cc',
      background: '#666699',
      color: '#222222'
    },
    cssClassName: 'button',
    notify: function () {
      midi.send(new WsMidi.Message(144, 4, 62, 100));
      setTimeout(function () {
        midi.send(new WsMidi.Message(128, 4, 62, 100));
      }, noteOffTimeout);
    }
  });

  ui.button04 = new TouchLib.TriggerButton({
    elementId: 'triggerButton4',
    triggerTimeout: 150,
    on: {
      innerHTML: '!!!',
      border: '3px solid #22cc22',
      background: '#669966',
      color: '#222222'
    },
    off: {
      innerHTML: 'trigger',
      border: '3px solid #2222cc',
      background: '#666699',
      color: '#222222'
    },
    cssClassName: 'button',
    notify: function () {
      midi.send(new WsMidi.Message(144, 4, 63, 100));
      setTimeout(function () {
        midi.send(new WsMidi.Message(128, 4, 63, 100));
      }, noteOffTimeout);
    }
  });

  ui.slidersV = new TouchLib.SliderFieldVert({
    elementId: 'slidersV',
    width: 200,
    height: 200,
    numSliders: 4,
    fillStyle: '#6666cc',
    cssClass: 'sliderField',
    notify: function (valArr) {
      midi.send(new WsMidi.Message(176, 4, 40, Math.floor(127 * valArr[0])));
      midi.send(new WsMidi.Message(176, 4, 41, Math.floor(127 * valArr[1])));
      midi.send(new WsMidi.Message(176, 4, 42, Math.floor(127 * valArr[2])));
      midi.send(new WsMidi.Message(176, 4, 43, Math.floor(127 * valArr[3])));
    }
  });

  ui.knob1 = new TouchLib.Knob({
    elementId: 'knobElement1',
    width: 120,
    height: 120,
    cssClass: 'knob',
    outline: '#ccccff',
    fillStyle: '#333366',
    notify: function (val) {
      midi.send(new WsMidi.Message(176, 4, 50, Math.floor(127 * val)));
    }
  });

  ui.knob2 = new TouchLib.Knob({
    elementId: 'knobElement2',
    width: 120,
    height: 120,
    cssClass: 'knob',
    outline: '#ccccff',
    fillStyle: '#333366',
    notify: function (val) {
      midi.send(new WsMidi.Message(176, 4, 51, Math.floor(127 * val)));
    }
  });

}