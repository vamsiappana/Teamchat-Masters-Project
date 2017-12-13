package se.bth.aspm.groupone.teamchat.client.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Omar-PC
 */
public class MainWindowExample {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
	Date date;
	Calendar cal;

	private JRadioButton[] gorrillaRadio;
	private JFrame mainWindow;

	public MainWindowExample() {
		// create the frame
		mainWindow = new JFrame();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(1024, 768);
		mainWindow.setLocationRelativeTo(null);
		// end

		// Create layout for radio button lists
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.LEFT);
		// end

		// create channels panel
		JPanel channelListPanel = new JPanel();
		channelListPanel.setLayout(flowLayout);
		channelListPanel.setPreferredSize(new Dimension(190, 760));
		channelListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		addRadioButtons(channelListPanel, 10);
		// end

		// create the team members panel
		JPanel teamMemebersListPanel = new JPanel();
		teamMemebersListPanel.setLayout(flowLayout);
		teamMemebersListPanel.setPreferredSize(new Dimension(190, 760));
		teamMemebersListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		addRadioButtons(teamMemebersListPanel, 10);
		// end

		// create the public chat screen
		JPanel publicChatPanel = new JPanel();
		publicChatPanel.setPreferredSize(new Dimension(644, 650));
		publicChatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JTextArea publicChatArea = new JTextArea();
		publicChatArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(publicChatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(600, 600));
		publicChatPanel.add(scroll);
		// end

		// create the chat box
		JPanel chatBoxPanel = new JPanel();
		chatBoxPanel.setPreferredSize(new Dimension(644, 100));
		channelListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(400, 75));
		chatBoxPanel.add(textField, BorderLayout.CENTER);
		JButton sendButton = new JButton();
		sendButton.setPreferredSize(new Dimension(75, 50));
		sendButton.setText("Send");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendButtonPressed(textField, publicChatArea, "Omar");

			}
		});
		chatBoxPanel.add(sendButton, BorderLayout.CENTER);
		// end

		// add the panels to the main window
		mainWindow.add(channelListPanel, BorderLayout.WEST);
		mainWindow.add(publicChatPanel, BorderLayout.CENTER);
		mainWindow.add(teamMemebersListPanel, BorderLayout.EAST);
		mainWindow.add(chatBoxPanel, BorderLayout.SOUTH);
		mainWindow.setVisible(true);
		// end
	}

	public void sendButtonPressed(JTextField textField, JTextArea textArea, String userName) {
		date = new Date();
		cal = Calendar.getInstance();
		textArea.append("(" + dateFormat.format(cal.getTime()) + ") " + userName + ": " + textField.getText() + "\n");
		textField.setText("");
		textField.requestFocus();
	}

	public void addRadioButtons(JPanel panel, int numOfButtons) {
		ButtonGroup group = new ButtonGroup();
		gorrillaRadio = new JRadioButton[numOfButtons + 1];
		for (int i = 0; i < numOfButtons; i++) {
			gorrillaRadio[i] = new JRadioButton();
			gorrillaRadio[i].setText("option " + i);
			group.add(gorrillaRadio[i]);
			panel.add(gorrillaRadio[i]);
		}
	}

	public static void main(String[] args) {
		new MainWindowExample();
	}
}
