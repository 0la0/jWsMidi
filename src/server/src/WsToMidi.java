import java.io.IOException;
import java.net.InetSocketAddress;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.swing.DefaultComboBoxModel;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/*
 * Listens for Web Socket messages
 * and sends them to a selected MIDI device
 */
public class WsToMidi extends WsMidiServer {
	
	private Receiver midiReceiver;
	
	public WsToMidi () {
		super();
		this.refreshComboBox();
	}
	
	@Override
	protected void turnOn () {
		super.turnOn();
		this.activeDeviceString = (String) this.midiDeviceComboBox.getSelectedItem();
		this.midiReceiver = MidiDeviceFactory.getReceiver(this.activeDeviceString);
		this.midiDevice = MidiDeviceFactory.getReceiverDevice(this.activeDeviceString);
		try {
			this.midiDevice.open();
			this.server = new WsServer(this.serverPort);
			this.server.start();
			//this.serverButton.setText("Stop Server");
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void turnOff () {
		super.turnOff();
		if (this.midiDevice != null) this.midiDevice.close();
		try {
			if (this.server != null) this.server.stop();
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
		this.midiDeviceComboBox.setModel(new DefaultComboBoxModel(MidiDeviceFactory.getReceivers()));
	}
	
	private void processDataFrame (String msg) {
		String[] tokens = msg.split(",");
		ShortMessage sm = new ShortMessage();
		try {
			sm.setMessage(
				Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
				Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])
			);
			this.midiReceiver.send(sm, this.midiDevice.getMicrosecondPosition() + 100);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			processDataFrame(message);
		}

		@Override
		public void onOpen(WebSocket conn, ClientHandshake handshake) {
		    System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
		}
		
	}

}
