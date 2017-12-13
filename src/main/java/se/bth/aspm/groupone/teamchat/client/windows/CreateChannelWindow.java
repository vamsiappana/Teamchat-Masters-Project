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
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.PrivateChannelMessage;

@Component("createChannelWindow")
public class CreateChannelWindow extends AbstractWindow  {

	@Resource(name = "mainWindow")
	private Window mainWindow;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				CreateChannelWindow st = new CreateChannelWindow();
				st.setVisible(true);
			}
		});
	}

	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JButton button1;
	private JButton button2;


	@Autowired
	private TeamChatClient teamChatServer;

	public CreateChannelWindow() {

		initComponents();
		
	}


	private void initComponents() {

		this.setSize(600, 400);
		label1 = new JLabel();
		label1.setBounds(40, 50, 120, 70);
		label1.setText("Channel Name");
		Font font = new Font("", Font.BOLD, 16);
		label1.setFont(font);

		label2 = new JLabel();
		label2.setBounds(40, 125, 150, 70);
		label2.setText("Type");
		label2.setFont(font);
		
		text1 = new JTextField();
		text1.setBounds(240, 75, 200, 40);
		
		 String country[]={"Private","Public"};  
	      
		    JComboBox cb=new JComboBox(country);  
		    cb.setBounds(240, 145, 200, 40);  		    
		    cb.setFont(font);

		button1 = new JButton();
		button1.setBounds(440, 300, 100, 50);
		button1.setText("Create");
		button1.setToolTipText("Create");
		button1.setFont(font);
		
		button2 = new JButton();
		button2.setBounds(330, 300, 100, 50);
		button2.setText("Cancel");
		button2.setToolTipText("Cancel");
		button2.setFont(font);
		
		

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PrivateChannelMessage channelMessage = new PrivateChannelMessage();
				channelMessage.setName(text1.getText());
				channelMessage.setTypeOfChannel(cb.getSelectedItem().toString());
				

				teamChatServer.sendMessage(channelMessage);

			}
		});
		
		AbstractWindow aw = this;
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				aw.hideWindow();
				
			}
		});

		Border padding = BorderFactory.createEmptyBorder(15, 20, 20, 20);

		this.getContentPane().setLayout(null);
		this.add(label1);
		this.add(label2);
		this.add(cb);	
		this.add(text1);	
		this.add(button1);
		this.add(button2);

		setLocationRelativeTo(null);

	}


	@Override
	public void clearAndReinitialize() {
		// TODO Auto-generated method stub
		
	}
}
