import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.midi.MidiDevice;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.java_websocket.server.WebSocketServer;

/*
 * Parent class of:
 *   -MidiToWs
 *   -WsToMidi
 */
public class WsMidiServer {
	
	protected JButton refreshButton;
	protected JButton serverButton;
	protected JComboBox midiDeviceComboBox;
	protected JTextField portField;
	protected boolean serverIsOn = false;
	protected ButtonListener buttonListener = new ButtonListener();
	protected int serverPort = 8080;
	protected WebSocketServer server;
	protected MidiDevice midiDevice;
	protected String activeDeviceString;
	
	public WsMidiServer () {
		this.buildInterface();
	}
	
	protected void turnOn () {
		this.serverIsOn = true;
		this.serverButton.setText("Stop Server");
		
		//get server port, on error use 8080
		int port = Integer.parseInt(this.portField.getText());
		if (port < 1025 || port > 65535) port = 8080;
		this.serverPort = port;
	}
	
	protected void turnOff () {
		this.serverIsOn = false;
		this.serverButton.setText("Start Server");
	}
	
	protected void refreshComboBox () {}
	
	private void buildInterface () {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		//---------MIDI DEVICE PANEL---------//
		JPanel midiDevicePanel = new JPanel();
		midiDevicePanel.setLayout(new BoxLayout(midiDevicePanel, BoxLayout.X_AXIS));
		
		JLabel receiverLabel = new JLabel("MIDI Device: ");
		this.midiDeviceComboBox = new JComboBox();
		this.midiDeviceComboBox.setPrototypeDisplayValue("xxxxxxxx");
		
		this.refreshButton = new JButton("Refresh");
		this.refreshButton.addActionListener(this.buttonListener);
		
		receiverLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.midiDeviceComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.refreshButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		receiverLabel.setPreferredSize(new Dimension(80, receiverLabel.getHeight()));
		
		midiDevicePanel.add(receiverLabel);
		midiDevicePanel.add(this.midiDeviceComboBox);
		midiDevicePanel.add(this.refreshButton);
		
		
		//---------WS SERVER PANEL--------//
		JPanel wsPanel = new JPanel();
		wsPanel.setLayout(new BoxLayout(wsPanel, BoxLayout.X_AXIS));
		
		JLabel portLabel = new JLabel("ws port");
		this.portField = new JTextField("8080");
		
		this.serverButton = new JButton("Start Server");
		this.serverButton.addActionListener(this.buttonListener);
		
		portLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.portField.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.serverButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		portLabel.setPreferredSize(new Dimension(80, receiverLabel.getHeight()));
		
		wsPanel.add(portLabel);
		wsPanel.add(this.portField);
		wsPanel.add(this.serverButton);
		
		
		//----------BUILD MAIN PANEL------//
		mainPanel.add(midiDevicePanel);
		mainPanel.add(wsPanel);
		
		//---------PUT INTO FRAME---------//
		JFrame wsMidiServerFrame = new JFrame();
		wsMidiServerFrame.add(mainPanel);
		wsMidiServerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		wsMidiServerFrame.pack();
		wsMidiServerFrame.setLocation(10, 10);
		wsMidiServerFrame.setVisible(true);
		if (this instanceof MidiToWs) {
			wsMidiServerFrame.setTitle("Midi to WebSocket");
		}
		else if (this instanceof WsToMidi) {
			wsMidiServerFrame.setTitle("WebSocket to Midi");
		}
		wsMidiServerFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				turnOff();
			}
		});
	}
	
	protected class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			JButton srcButton = (JButton) arg0.getSource();
			if (srcButton == serverButton) {
				if (!serverIsOn) turnOn();
				else turnOff();
			}
			else if (srcButton == refreshButton) {
				refreshComboBox();
			}
		}
	}

}
