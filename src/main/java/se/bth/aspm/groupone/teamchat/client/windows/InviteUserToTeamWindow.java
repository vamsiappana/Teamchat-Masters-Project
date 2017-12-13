package se.bth.aspm.groupone.teamchat.client.windows;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.InviteUserToTeamMessage;
@Component("InviteUserToTeamWindow")

public class InviteUserToTeamWindow extends AbstractWindow  {
	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				InviteUserToTeamWindow st = new InviteUserToTeamWindow();
				st.setVisible(true);
			}
		});
	}
	
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JButton inviteButton;
	private JButton cancelButton;
	
	@Autowired
	private TeamChatClient teamChatServer;
	
	public InviteUserToTeamWindow() {

		initComponents();
		
	}
	
	
	
	private void initComponents() {

		this.setSize(600, 400);
		Font font = new Font("", Font.BOLD, 16);

		emailLabel = new JLabel();
		emailLabel.setBounds(40, 125, 150, 70);
		emailLabel.setText("Email");
		
		emailLabel.setFont(font);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(240, 140, 200, 40);
		

		inviteButton = new JButton();
		inviteButton.setBounds(440, 300, 100, 50);
		inviteButton.setText("Invite");
		inviteButton.setToolTipText("Invite");
		inviteButton.setFont(font);
		
		cancelButton = new JButton();
		cancelButton.setBounds(330, 300, 100, 50);
		cancelButton.setText("Cancel");
		cancelButton.setToolTipText("Cancel");
		cancelButton.setFont(font);
	
		
		
		inviteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				InviteUserToTeamMessage inviteMessage = new InviteUserToTeamMessage();
				inviteMessage.setEmail(emailTextField.getText());
				
				teamChatServer.sendMessage(inviteMessage);
				
			}
		});
		
		AbstractWindow aw1 = this;
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				aw1.hideWindow();
				
			}
		});
		
		Border padding = BorderFactory.createEmptyBorder(15, 20, 20, 20);

		this.getContentPane().setLayout(null);
		this.add(emailLabel);
		this.add(emailTextField);
		this.add(inviteButton);
		this.add(cancelButton);

		setLocationRelativeTo(null);

	}



	@Override
	public void clearAndReinitialize() {
		// TODO Auto-generated method stub
		
	}
}

				
				