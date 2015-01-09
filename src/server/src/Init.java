import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;


public class Init {
	
	private ButtonGroup buttonGroup;
	
	public Init () {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "jWsMidi");
		
		//---------------BUILD UI-----------------//
		//--two radio buttons and a start button--//
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JRadioButton wsToMidiButton = new JRadioButton("Web Socket to MIDI");
        wsToMidiButton.setActionCommand("wsToMidi");
        wsToMidiButton.setSelected(true);
        
        JRadioButton midiToWsButton = new JRadioButton("MIDI to Web Socket");
        midiToWsButton.setActionCommand("midiToWs");

        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.add(wsToMidiButton);
        this.buttonGroup.add(midiToWsButton);
        
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
        
        wsToMidiButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        midiToWsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        radioButtonPanel.add(wsToMidiButton);
        radioButtonPanel.add(midiToWsButton);
        
        TitledBorder mainTitle = BorderFactory.createTitledBorder(
        	BorderFactory.createLineBorder(Color.black), "Midi Servers");
        mainTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        radioButtonPanel.setBorder(mainTitle);
        
        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new GridLayout(1, 1));
        JButton startButton = new JButton("start");
        startButton.addActionListener(new ButtonListener());
        startButtonPanel.add(startButton);
        
        radioButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        startButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(radioButtonPanel);
        mainPanel.add(startButtonPanel);
        
		//---------PUT INTO FRAME---------//
		JFrame optionsFrame = new JFrame();
		optionsFrame.add(mainPanel);
		optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionsFrame.pack();
		optionsFrame.setLocation(10, 10);
		optionsFrame.setVisible(true);
		optionsFrame.setTitle("-WS MIDI-");
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String selected = buttonGroup.getSelection().getActionCommand();
			if (selected.equals("wsToMidi")) {
				new WsToMidi();
			}
			else if (selected.equals("midiToWs")) {
				new MidiToWs();
			}
		}
	}
	
	public static void main (String[] args) {
		new Init();
	}

}
