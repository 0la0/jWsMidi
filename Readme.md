#jWsMidi
Communicate with MIDI devices via WebSocket clients.  There are two components to jWsMidi

####[jWsMidiServer](dist/)
is a Java process that manages communication between MIDI devices and WS clients.  

####[wsMidi.js](src/webClient/)
is a JavaScript library that manages and abstracts the connection to the server and message formatting.

The motivation for this project is to allow any device with a browser to become a MIDI controller for audio performance software, or to control a browser app with a MIDI device. 

The server UI allows the user to create two different processes.  "Web Socket to MIDI" listens for messages from all connected web socket clients and relays the messages to the selected MIDI device.  "MIDI to Web Socket" listens for messages from the selected MIDI device and relays these messages to all connected web socket clients.

---
####Dependencies
Java-WebSocket: 
https://github.com/TooTallNate/Java-WebSocket

---
###Usage
Start the server by running the jar in the dist directory.
```Shell
java -jar dist/jWsMidiServer.jar
```
Create a connection to the server from a browser.
```javascript
var midi = new WsMidi.Connection({
  ip: location.host, //IP Address of the host running the server
  port: 8080,       //Port as configured on the server
  nofity: funciton (msg) {
    //A callback function executed when the client receives messages
    //msg is a WsMidi.Message object
  }
});
```
If the connection fails you can manually attempt a connection.
```javascript
midi.connectToServer(ipAddress, port);
```
A connection only sends and receives WsMidi.Message objects.
These objects are very similar to the Java ShortMessage objects.
http://docs.oracle.com/javase/7/docs/api/javax/sound/midi/ShortMessage.html

To send a message to the server, pass a message object to the send function
```javascript
midi.send(new WsMidi.Message(statusCommand, channel, note, velocity));
```

The notify callback is executed when the client receives MIDI messages.
```javascript
function notifyCallback (msg) {
  console.log(msg.getCommand()); //first nibble of a midi message
  console.log(msg.getChannel()); //second nibble 
  console.log(msg.getData1());   //second byte - note
  console.log(msg.getData2());   //third byte - velocity
}
```

[Web client examples are located in the dist directory.](dist/exampleWebClients)