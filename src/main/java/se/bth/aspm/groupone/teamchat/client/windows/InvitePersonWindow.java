package se.bth.aspm.groupone.teamchat.client.windows;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.PrivateChannelMessage;

@Component("invitePersonWindow")
public class InvitePersonWindow extends AbstractWindow  {
	
	

	@Resource(name = "mainWindow")
	private Window mainWindow;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				InvitePersonWindow st = new InvitePersonWindow();
				st.setVisible(true);
			}
		});
	}

	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JButton button1;
	private JButton button2;
	
	private JComboBox cb;
	
	@Getter @Setter
	private String ChannelName;
	
	private List<String> teamMembers;


	@Autowired
	private TeamChatClient teamChatServer;

	public InvitePersonWindow() {

		initComponents();
		
	}


	private void initComponents() {

		this.setSize(600, 350);
		label1 = new JLabel();
		label1.setBounds(40, 50, 120, 70);
		label1.setText("Team Members");
		Font font = new Font("", Font.BOLD, 16);
		label1.setFont(font);
		
		ChannelName = new String();
		teamMembers = new ArrayList<>();

		
		
		    cb=new JComboBox(); 
		    
		    /*for(Iterator item = teamMembers.iterator();item.hasNext(); ){
		    
		    	cb.addItem((String)(item.next()));
		    }*/
		   // addTeamMembers st = new addTeamMembers();
		    cb.setBounds(240, 75, 200, 40);  		    
		    cb.setFont(font);
		    
		    

		button1 = new JButton();
		button1.setBounds(440, 200, 100, 50);
		button1.setText("Invite");
		button1.setToolTipText("Invite");
		button1.setFont(font);
		
		button2 = new JButton();
		button2.setBounds(330, 200, 100, 50);
		button2.setText("Cancel");
		button2.setToolTipText("Cancel");
		button2.setFont(font);
		
		

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
			PrivateChannelMessage channelMessage = new PrivateChannelMessage();
			channelMessage.setName(ChannelName);
			channelMessage.setUser(cb.getSelectedItem().toString());
			

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
		
		this.add(cb);	
		
		this.add(button1);
		this.add(button2);

		setLocationRelativeTo(null);
		
		

	}
	
	public void setChannelName(String name){
		this.ChannelName = name;
	}
	
	public void setTeamMember(List<String> userList){
		this.teamMembers = userList;
		cb.removeAllItems();
		for(Iterator item = teamMembers.iterator();item.hasNext(); ){
	    
			cb.addItem((String)(item.next()));
		}
	}


	@Override
	public void clearAndReinitialize() {
		// TODO Auto-generated method stub
		
	}

		
	
}
