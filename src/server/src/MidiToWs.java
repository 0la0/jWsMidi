import java.io.IOException;
import java.net.InetSocketAddress;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.swing.DefaultComboBoxModel;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/*
 * Listens for MIDI messages,
 * and broadcasts messages to all web socket clients
 */
public class MidiToWs extends WsMidiServer {
	
	public MidiToWs () {
		super();
		this.refreshComboBox();
	}
	
	@Override
	protected void turnOn () {
		super.turnOn();
		this.activeDeviceString = (String) this.midiDeviceComboBox.getSelectedItem();
		this.midiDevice = MidiDeviceFactory.getTransmitterDevice(this.activeDeviceString);
		try {
			this.midiDevice.open();
			this.midiDevice.getTransmitter().setReceiver(new MidiInReceiver());
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.server = new WsServer(this.serverPort);
		this.server.start();
	}
	
	@Override
	protected void turnOff () {
		super.turnOff();
		if (this.midiDevice != null) this.midiDevice.close();
		try {
			if (this.server != null) {
				this.server.stop(0);
				//this.server.
				this.server = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void refreshComboBox () {
		MidiDeviceFactory.refreshDevices();
		this.midiDeviceComboBox.setModel(
				new DefaultComboBoxModel(MidiDeviceFactory.getReceivers()));
	}
	
	private class WsServer extends WebSocketServer{
		
		public WsServer(int port){
			super(new InetSocketAddress(port));
			System.out.println("new wss server running on port: " + port);
		}

		@Override
		public void onClose(WebSocket conn, int code, String reason, boolean remote) {
			System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
		}

		@Override
		public void onError(WebSocket conn, Exception ex) {
			System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
			System.out.println(ex.toString());
		}

		@Override
		public void onMessage(WebSocket conn, String message) {
			System.out.println("Received: " + message);
		}

		@Override
		public void onOpen(WebSocket conn, ClientHandshake handshake) {
			System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
		}
	}
	
	private class MidiInReceiver implements Receiver {
		
		@Override
		public void close () {}

		@Override
		public void send (MidiMessage msg, long timeStamp) {
			ShortMessage sm = (ShortMessage) msg;
			String dataFrame = sm.getCommand() + "," + sm.getChannel() + "," + sm.getData1() + "," + sm.getData2();
			//-------SEND TO ALL WEB SOCKET CLIENTS-------//
			for (WebSocket socket : server.connections()) {
				socket.send(dataFrame);
			}
		}
		
	}
	

}
