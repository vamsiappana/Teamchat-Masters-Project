package se.bth.aspm.groupone.teamchat.client.windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.CreateTeamMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.JoinTeamMessage;

@Component("CreateOrJoinTeamWindow")
public class CreateOrJoinTeamWindow extends AbstractWindow {

	@Autowired
	private TeamChatClient teamChatServer;

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	private JLabel label2;
	private JTextField textField1;
	private JLabel label3;
	private JLabel label1;
	private JSeparator separator1;
	private JLabel label4;
	private JTextField textField2;
	private JLabel label5;
	private JTextField textField3;
	private JButton button1;
	private JButton button2;

	public CreateOrJoinTeamWindow() {
		initComponents();

		this.setSize(800, 265);
	}

	private void initComponents() {
		label2 = new JLabel();
		textField1 = new JTextField();
		label3 = new JLabel();
		label1 = new JLabel();
		separator1 = new JSeparator();
		label4 = new JLabel();
		textField2 = new JTextField();
		label5 = new JLabel();
		textField3 = new JTextField();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();

		//---- label2 ----
		label2.setText("Join to existing team");
		label2.setFont(new Font("Tahoma", Font.BOLD, 20));

		//---- label3 ----
		label3.setText("Team name:");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- label1 ----
		label1.setText("Create new team");
		label1.setFont(new Font("Tahoma", Font.BOLD, 20));

		//---- separator1 ----
		separator1.setBackground(Color.gray);

		//---- label4 ----
		label4.setText("Team name:");
		label4.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- label5 ----
		label5.setText("Invitation key:");
		label5.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- button1 ----
		button1.setText("Create");
		button1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button1.addActionListener(actionEvent -> {
			CreateTeamMessage createTeamMessage = new CreateTeamMessage();
			createTeamMessage.setTeamName(textField1.getText());
			teamChatServer.sendMessage(createTeamMessage);
		});

		//---- button2 ----
		button2.setText("Join");
		button2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button2.addActionListener(actionEvent -> {
			JoinTeamMessage joinTeamMessage = new JoinTeamMessage();
			joinTeamMessage.setTeamName(textField2.getText());
			joinTeamMessage.setInvitationKey(textField3.getText());
			teamChatServer.sendMessage(joinTeamMessage);
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addGroup(contentPaneLayout.createParallelGroup()
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addGap(31, 31, 31)
												.addGroup(contentPaneLayout.createParallelGroup()
														.addComponent(label1, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
														.addGroup(contentPaneLayout.createSequentialGroup()
																.addComponent(label3, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))))
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addGap(166, 166, 166)
												.addComponent(button1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
								.addGap(26, 26, 26)
								.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
								.addGroup(contentPaneLayout.createParallelGroup()
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(button2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
												.addGap(110, 110, 110))
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addGap(38, 38, 38)
												.addGroup(contentPaneLayout.createParallelGroup()
														.addComponent(label2, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
														.addGroup(contentPaneLayout.createParallelGroup()
																.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
																		.addComponent(label5, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
																		.addGap(12, 12, 12)
																		.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
																.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
																		.addComponent(label4, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
																		.addGap(12, 12, 12)
																		.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))))))
								.addGap(579, 579, 579))
		);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(contentPaneLayout.createParallelGroup()
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addComponent(label2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
												.addGap(18, 18, 18)
												.addGroup(contentPaneLayout.createParallelGroup()
														.addGroup(contentPaneLayout.createSequentialGroup()
																.addGap(5, 5, 5)
																.addComponent(label4))
														.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
												.addGap(18, 18, 18)
												.addGroup(contentPaneLayout.createParallelGroup()
														.addGroup(contentPaneLayout.createSequentialGroup()
																.addGap(5, 5, 5)
																.addComponent(label5))
														.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
												.addGap(18, 18, 18)
												.addComponent(button2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
												.addContainerGap(28, Short.MAX_VALUE))
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addComponent(label1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
												.addGap(18, 18, 18)
												.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
														.addComponent(label3))
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
												.addComponent(button1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
												.addGap(24, 24, 24))
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addComponent(separator1)
												.addContainerGap())))
		);

		pack();
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void clearAndReinitialize() {
		// TODO Auto-generated method stub
		
	}
}
