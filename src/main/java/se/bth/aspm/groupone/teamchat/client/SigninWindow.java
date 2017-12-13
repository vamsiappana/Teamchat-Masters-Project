package se.bth.aspm.groupone.teamchat.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.windows.AbstractWindow;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.RegisterMessage;

@Component("signinWindow")
public class SigninWindow extends AbstractWindow {

	@Resource(name = "loginWindow")
	private Window loginWindow;

	

	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;

	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JTextField text4;
	private JTextField text5;

	private JButton button1;
	private JButton button2;

	@Autowired
	private TeamChatClient teamChatServer;

	public SigninWindow() {

		initComponents();
	}

	private void initComponents() {

		// JPanel contentPanel = new JPanel();
		this.setSize(600, 670);

		this.setLocationRelativeTo(null);
		;
		label1 = new JLabel();
		label1.setBounds(40, 50, 80, 70);
		label1.setText("Username");
		Font font = new Font("", Font.BOLD, 16);
		label1.setFont(font);

		label2 = new JLabel();
		label2.setBounds(40, 125, 150, 70);
		label2.setText("DisplayName");
		label2.setFont(font);

		label3 = new JLabel();
		label3.setBounds(40, 200, 80, 70);
		label3.setText("Password");
		label3.setFont(font);

		label4 = new JLabel();
		label4.setBounds(40, 275, 150, 70);
		label4.setText("Password confirm");
		label4.setFont(font);

		label5 = new JLabel();
		label5.setBounds(40, 350, 80, 70);
		label5.setText("email");
		label5.setFont(font);


		text1 = new JTextField();
		text1.setBounds(240, 75, 200, 40);

		text2 = new JTextField();
		text2.setBounds(240, 150, 200, 40);

		text3 = new JPasswordField();
		text3.setBounds(240, 225, 200, 40);

		text4 = new JPasswordField();
		text4.setBounds(240, 300, 200, 40);

		text5 = new JTextField();
		text5.setBounds(240, 375, 200, 40);

	
		button1 = new JButton();
		button1.setBounds(440, 500, 100, 50);
		button1.setText("Register");
		button1.setToolTipText("Register");
		button1.setFont(font);
		
		button2 = new JButton();
		button2.setBounds(330, 500, 100, 50);
		button2.setText("Back");
		button2.setToolTipText("Back");
		button2.setFont(font);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				RegisterMessage registerMessage = new RegisterMessage();
				registerMessage.setUsername(text1.getText());
				registerMessage.setDisplayName(text2.getText());
				registerMessage.setPassword(text3.getText());
				registerMessage.setPasswordConfirm(text4.getText());
				registerMessage.setEmail(text5.getText());

				teamChatServer.sendMessage(registerMessage);
			}
		});
		AbstractWindow aw = this;
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				aw.hideWindow();
				loginWindow.showWindow();
			}
		});

		Border padding = BorderFactory.createEmptyBorder(15, 20, 20, 20);

		this.getContentPane().setLayout(null);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(label5);
		// this.add(label6);
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(text5);
		// this.add(text6);
		this.add(button1);
		this.add(button2);

		// pack();
		setLocationRelativeTo(null);

	}

	@Override
	public void clearAndReinitialize() {
		// TODO Auto-generated method stub
		
	}
}
