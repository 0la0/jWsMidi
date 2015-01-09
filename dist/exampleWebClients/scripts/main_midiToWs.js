window.onload = init;

var midi;
var ui = {};

function init () {
  buildInterface();
  midi = new WsMidi.Connection({
    ip: location.hostname,
    port: 8080,
    notify: midiIn
  });
}

function midiIn (msg) {
  switch (msg.getData1()) {
    case 28: 
      if (msg.getData2() > 0) ui.button01.processAction();
      break;
    case 29: 
      if (msg.getData2() > 0) ui.button02.processAction();
      break;
    case 24: 
      if (msg.getData2() > 0) ui.button03.processAction();
      break;
    case 25: 
      if (msg.getData2() > 0) ui.button04.processAction();
      break;
    case 37:
      ui.slidersV.setVal(0, msg.getData2() / 127);
      break;
    case 38:
      ui.slidersV.setVal(1, msg.getData2() / 127);
      break;
    case 39:
      ui.slidersV.setVal(2, msg.getData2() / 127);
      break;
    case 40:
      ui.slidersV.setVal(3, msg.getData2() / 127);
      break;
    case 42:
      ui.knob1.setVal(msg.getData2() / 127);
      break;
    case 44:
      ui.knob2.setVal(msg.getData2() / 127);
      break;
  }
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
      //Button1 app logic here
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
      //Button2 app logic here
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
      //Button3 app logic here
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
      //Button4 app logic here
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
      //Slider field app logic here
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
      //knob1 app logic here
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
      //knob2 app logic here
    }
  });

}