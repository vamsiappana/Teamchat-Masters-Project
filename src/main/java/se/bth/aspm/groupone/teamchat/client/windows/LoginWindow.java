package se.bth.aspm.groupone.teamchat.client.windows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.SigninWindow;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.LoginMessage;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component("loginWindow")
public class LoginWindow extends AbstractWindow {

	// private JPanel mainPanel;
	private JLabel label1;
	private JLabel label2;
	private JButton button1;
	private JButton button2;
	private JTextField text1;
	private JTextField text2;

	@Autowired
	private TeamChatClient teamChatServer;

	@Autowired
	private SigninWindow signinWindow;

	public LoginWindow() {
		initComponents();
	}

	private void initComponents() {
		// mainPanel = new JPanel();
		label1 = new JLabel();
		label1.setBounds(40, 25, 77, 30);

		label1.setText("Username");
		Font font = new Font("", Font.BOLD, 14);
		label1.setFont(font);
		label1.setToolTipText("JLabel");
		label2 = new JLabel();
		label2.setBounds(40, 75, 77, 30);

		label2.setText("Password");
		Font font1 = new Font("", Font.BOLD, 14);
		label2.setFont(font1);
		label2.setToolTipText("JLabe2");

		text1 = new JTextField();

		text1.setBounds(140, 25, 160, 30);
		text2 = new JPasswordField();

		text2.setBounds(140, 75, 160, 30);
		button1 = new JButton();
		button1.setBounds(220, 150, 85, 27);
		button1.setText("Login");
		button1.setToolTipText("Login");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				LoginMessage loginMessage = new LoginMessage();
				loginMessage.setUsername(text1.getText());
				loginMessage.setPassword(text2.getText());

				teamChatServer.sendMessage(loginMessage);
			}
		});

		button2 = new JButton();
		button2.setBounds(80, 150, 85, 27);
		button2.setText("Register");
		button2.setToolTipText("Register");
		AbstractWindow aw = this;
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				aw.hideWindow();
				signinWindow.showWindow();
			}
		});

		JPanel contentPanel = new JPanel();
		Border padding = BorderFactory.createEmptyBorder(15, 15, 15, 15);
		contentPanel.setBorder(padding);
		GridLayout gl = new GridLayout(3, 2, 20, 10);
		contentPanel.setLayout(gl);
		this.setContentPane(contentPanel);

		contentPanel.add(label1);
		contentPanel.add(label2);
		contentPanel.add(text1);
		contentPanel.add(text2);
		contentPanel.add(button1);
		contentPanel.add(button2);

		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void clearAndReinitialize() {
		this.text1.setText(null);
		this.text2.setText(null);
		this.repaint();
	}

}
