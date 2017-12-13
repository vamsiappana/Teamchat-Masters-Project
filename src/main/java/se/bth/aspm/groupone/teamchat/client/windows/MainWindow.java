package se.bth.aspm.groupone.teamchat.client.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.model.Channel;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.protocol.enums.ChannelType;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.ChannelMembersRequestMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.ChannelMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.ConversationMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.KickMemberFromPrivateChannelMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.LogoutMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.RemoveTeammemberMessage;

/**
 *
 * @author Omar-PC
 */
@Component("mainWindow")
public class MainWindow extends AbstractWindow {

	@Autowired
	ClientState clientState;

	@Autowired
	private TeamChatClient teamChatClient;

	@Autowired
	private InvitePersonWindow invitePersonWindow;

	@Autowired
	private CreateChannelWindow createChannelWindow;

	@Autowired
	private InviteUserToTeamWindow inviteUserToTeamWindow;

	private Map<String, JTextArea> channelNameTochatAreaMap = new TreeMap<String, JTextArea>();
	private ArrayList<JLabel> channelsList = new ArrayList<JLabel>();
	private JTextField chatBox;
	private JPanel teamMemebersListPanel;
	private JPanel channelListPanel;
	private JPopupMenu popupMenuChannelListPanel;
	private JPopupMenu popupMenuTeamMembersListPanel;
	/** 菜单项 **/
	private JMenuItem itemsOfPopupMenuChannelListPanel[], itemsOfPopupMenuTeamMembersListPanel[];
	private JScrollPane scroll;
	private JButton inviteButton;
	private JButton addButton;
	private JPanel channelMembersPanel = new JPanel();
	private JPanel teamMembersPanel = new JPanel();
	JLabel channelsText;
	private JTabbedPane tabbedPanel;

	JMenuBar menuBar;
	JMenu menu;

	public MainWindow() {
		initComponents();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				MainWindow st = new MainWindow();
				st.setVisible(true);
			}
		});
	}

	public void clearAndReinitialize() {
		channelNameTochatAreaMap.clear();
		channelsList.clear();
		Set<java.awt.Component> componentsToBeLeftOnChannelListPanel = new HashSet<java.awt.Component>();
		Set<java.awt.Component> componentsToBeLeftOnTeamMemebersListPanel = new HashSet<java.awt.Component>();
		componentsToBeLeftOnChannelListPanel.add(addButton);
		componentsToBeLeftOnTeamMemebersListPanel.add(inviteButton);
		componentsToBeLeftOnTeamMemebersListPanel.add(tabbedPanel);
		componentsToBeLeftOnTeamMemebersListPanel.add(channelsText);
		this.removeAllComponentsExceptGivenComponents(this.channelListPanel, componentsToBeLeftOnChannelListPanel);
		this.removeAllComponentsExceptGivenComponents(teamMemebersListPanel, componentsToBeLeftOnTeamMemebersListPanel);
		//this.removeAllComponentsExceptFirstAdded(channelListPanel);
		//this.removeAllComponentsExceptFirstAdded(teamMemebersListPanel);
	}

	private void removeAllComponentsExceptFirstAdded(JPanel jPanel) {
		for (int i = jPanel.getComponentCount() - 1; i > 0; i--) {
			jPanel.remove(i);
		}
	}
	
	private void removeAllComponentsExceptGivenComponents(JPanel jPanel, Collection<java.awt.Component> notToRemove) {
		for (int i = jPanel.getComponentCount() - 1; i >= 0; i--) {
			java.awt.Component componentAtI = jPanel.getComponent(i);
			if( !notToRemove.contains(componentAtI))
			{
				jPanel.remove(i);
			}
		}
	}

	private void initComponents() {
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

		// create the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024, 768);
		setResizable(false);
		//this.setBackground(Color.green);
		//this.getContentPane().setBackground(Color.red);
		//this.getContentPane().setVisible(false);//如果改为true那么就变成了红色。
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setVisible(true);
		//this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		
		// end

		// Create layout for radio button lists
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.LEFT);
		// end

		// create channels panel
		channelListPanel = new JPanel();
		channelsText = new JLabel();
		channelsText.setText("Channels");
		channelsText.setFont(new Font("calibri", Font.PLAIN, 39));
		channelListPanel.setLayout(flowLayout);
		channelListPanel.setPreferredSize(new Dimension(190, 760));
		
		channelListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		channelListPanel.add(channelsText, BorderLayout.NORTH);
		// end

		// create the team members panel
		teamMemebersListPanel = new JPanel();
		JLabel teamMembersText = new JLabel();
		teamMembersText.setText("Members");
		teamMembersText.setFont(new Font("calibri", Font.PLAIN, 39));
		tabbedPanel = new JTabbedPane();
		tabbedPanel.setAlignmentX(0);
		tabbedPanel.setPreferredSize(new Dimension(200, 760));
		tabbedPanel.addTab("Channel", null, channelMembersPanel, null);
		tabbedPanel.setFont(new Font("",Font.BOLD,20));
		tabbedPanel.addTab("Team", null, teamMembersPanel, null);

		teamMembersPanel.setLayout(flowLayout);
		channelMembersPanel.setLayout(flowLayout);

//		teamMemebersListPanel.setLayout(new FlowLayout());
		teamMemebersListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		teamMemebersListPanel.setPreferredSize(new Dimension(200, 760));
		teamMemebersListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		teamMemebersListPanel.add(teamMembersText, BorderLayout.NORTH);
		teamMemebersListPanel.add(tabbedPanel, BorderLayout.NORTH);
		// end

		preparePopupMenuChannelListPanel();
		
		//String channelName;
		popupMenuTeamMembersListPanel = new JPopupMenu();
		JMenuItem kickFromChannelMenuItem = new JMenuItem("Kick from channel");
		kickFromChannelMenuItem.addActionListener(actionEvent -> {
			JLabel usernameLabel = (JLabel)popupMenuTeamMembersListPanel.getInvoker();
            String username = usernameLabel.getText();

			KickMemberFromPrivateChannelMessage kickMemberFromPrivateChannelMessage = new KickMemberFromPrivateChannelMessage();
			kickMemberFromPrivateChannelMessage.setUsername(username);
			kickMemberFromPrivateChannelMessage.setChannelName(clientState.getCurrentlyDisplayedChannelName());
			teamChatClient.sendMessage(kickMemberFromPrivateChannelMessage);
			channelMembersPanel.remove(usernameLabel);
			invalidate();
			repaint();
        });
		popupMenuTeamMembersListPanel.add(kickFromChannelMenuItem);


		// end
		// create the public chat screen
		JPanel publicChatPanel = new JPanel();
		publicChatPanel.setPreferredSize(new Dimension(644, 650));
		publicChatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JTextArea dummyChannelTextArea = new JTextArea();
		dummyChannelTextArea.setEditable(false);
		scroll = new JScrollPane(dummyChannelTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(600, 600));
		publicChatPanel.add(scroll);
		// end

		// create the chat box
		JPanel chatBoxPanel = new JPanel();
		chatBoxPanel.setPreferredSize(new Dimension(644, 100));
		channelListPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chatBox = new JTextField();
		chatBox.setPreferredSize(new Dimension(400, 75));
		chatBoxPanel.add(chatBox, BorderLayout.CENTER);
		addButton = new JButton();
		addButton.setPreferredSize(new Dimension(100, 30));
		addButton.setText("Add");
		addButton.setFont(new Font("",Font.BOLD, 20));
		AbstractWindow aw = this;
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				createChannelWindow.showWindow();
			}
		});
		JButton sendButton = new JButton();
		sendButton.setPreferredSize(new Dimension(105, 50));
		sendButton.setText("Send");
		sendButton.setFont(new Font("",Font.BOLD, 18));
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				sendButtonActionPerformed(evt);

			}
		});
		// Invite team member
		inviteButton = new JButton();
		inviteButton.setPreferredSize(new Dimension(65, 50));
		inviteButton.setText("invite");

		inviteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				inviteUserToTeamWindow.showWindow();

			}
		});
		// end

		chatBoxPanel.add(sendButton, BorderLayout.CENTER);
		// end

		// Menu section
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("Log out", KeyEvent.VK_T);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				teamChatClient.sendMessage(new LogoutMessage());
			}
		});
		menu.add(menuItem);

		// add the panels to the main window
		this.add(menuBar, BorderLayout.NORTH);
		this.add(channelListPanel, BorderLayout.WEST);
		this.add(publicChatPanel, BorderLayout.CENTER);
		this.add(teamMemebersListPanel, BorderLayout.EAST);
		this.add(chatBoxPanel, BorderLayout.SOUTH);
		channelListPanel.add(addButton, BorderLayout.EAST);
		// end
	}


	public void addInviteButtonToTeamMembersListPanel() {
		if(inviteButton.getParent() == null || !inviteButton.getParent().equals(this.teamMembersPanel))
		{
			teamMembersPanel.add(inviteButton, BorderLayout.SOUTH);
		}
	}
	
	public void removeInviteButtonToTeamMembersListPanel() {
		if(inviteButton.getParent() != null && inviteButton.getParent().equals(this.teamMembersPanel))
		{
			teamMembersPanel.remove(this.inviteButton);
		}
	}

	public void preparePopupMenuTeammembersListPanel() {
		// String channelName;
		MainWindow mainWindow = this;
		popupMenuTeamMembersListPanel = new JPopupMenu(); // 实例化弹出菜单
		String[] str = { "Remove from team" }; // 菜单项名称
		itemsOfPopupMenuTeamMembersListPanel = new JMenuItem[1]; // 创建3个菜单项
		// MenuItemMonitor menuItemMonitor = new MenuItemMonitor(){;
		for (int i = 0; i < itemsOfPopupMenuTeamMembersListPanel.length; i++) {
			itemsOfPopupMenuTeamMembersListPanel[i] = new JMenuItem(str[i]); // 实例化菜单项
			popupMenuTeamMembersListPanel.add(itemsOfPopupMenuTeamMembersListPanel[i]); // 将菜单项添加到菜单中
		}
		itemsOfPopupMenuTeamMembersListPanel[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int result = JOptionPane.showConfirmDialog(mainWindow, "Are you sure that You want to remove user: " + clientState.getNameOfTeammemberClickedWithRightButton());
				if(result == JOptionPane.YES_OPTION)
				{
					RemoveTeammemberMessage removeTeammemberMessage = new RemoveTeammemberMessage();
					removeTeammemberMessage.setUsername(mainWindow.clientState.getNameOfTeammemberClickedWithRightButton());
					mainWindow.teamChatClient.sendMessage(removeTeammemberMessage);
				}
			}
		});
	}

	private void preparePopupMenuChannelListPanel() {
		// String channelName;
		popupMenuChannelListPanel = new JPopupMenu(); // 实例化弹出菜单 what's this ?
		String[] str = { "Invite Person", "Close", "Open" }; // 菜单项名称
		itemsOfPopupMenuChannelListPanel = new JMenuItem[3]; // 创建3个菜单项
		MenuItemMonitor menuItemMonitor = new MenuItemMonitor();
		for (int i = 0; i < itemsOfPopupMenuChannelListPanel.length; i++) {
			itemsOfPopupMenuChannelListPanel[i] = new JMenuItem(str[i]); // 实例化菜单项
			popupMenuChannelListPanel.add(itemsOfPopupMenuChannelListPanel[i]); // 将菜单项添加到菜单中
			// 设置ActionCommand，方便我们获取下标
			itemsOfPopupMenuChannelListPanel[i].setActionCommand(i + "");
			// 为各菜单项设置监听
			itemsOfPopupMenuChannelListPanel[i].addActionListener(menuItemMonitor);
		}
		itemsOfPopupMenuChannelListPanel[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int size = teamMemebersListPanel.getComponentCount();
				List<String> userList = new ArrayList<>();
				int i = 0;
				for (i = 0; i < size - 1; i++) {
					JLabel lable = (JLabel) teamMemebersListPanel.getComponent(i);
					String name = lable.getText();
					userList.add(name);
				}
				invitePersonWindow.setTeamMember(userList);
				invitePersonWindow.setChannelName(clientState.getNameOfChannelClickedWithRightButton());
				invitePersonWindow.showWindow();

			}
		});
	}

	private void addChannelGivenNameToChatAreaMap(String nameOfChannel) {
		JTextArea channelTextArea = new JTextArea();
		channelTextArea.setEditable(false);
		channelNameTochatAreaMap.put(nameOfChannel, channelTextArea);
	}

	public boolean existsChannelWithGivenName(String nameOfChannel) {
		return channelNameTochatAreaMap.get(nameOfChannel) != null;
	}

	public void setCurrentChannelOnViewGivenName(String nameOfChannel) {
		JTextArea channelTextArea = this.channelNameTochatAreaMap.get(nameOfChannel);
		scroll.setViewportView(channelTextArea);
		this.setTitle(nameOfChannel);
	}

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (!clientState.isConversationChosenByUser()) {
			ChannelMessageMessage channelMessageMessage = new ChannelMessageMessage();
			channelMessageMessage.setChannelName(clientState.getCurrentlyDisplayedChannelName());
			channelMessageMessage.setContent(chatBox.getText());
			teamChatClient.sendMessage(channelMessageMessage);
		} else {
			ConversationMessageMessage conversationMessageMessage = new ConversationMessageMessage();
			conversationMessageMessage.setOtherParticipantUsername(clientState.getCurrentlyDisplayedChannelName());
			conversationMessageMessage.setContent(chatBox.getText());
			teamChatClient.sendMessage(conversationMessageMessage);
		}
	}

	private class MenuItemMonitor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// 获取String格式的ActionCommand
			String strIndex = ((JMenuItem) event.getSource()).getActionCommand();

			int niIndex = Integer.parseInt(strIndex);

		}
	}

	public void addMessageToChannel(String channelName, String senderUsername, String messageContent,
			Date dateOfMessage) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfMessage);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

		channelNameTochatAreaMap.get(channelName)
				.append("(" + dateFormat.format(cal.getTime()) + ") " + senderUsername + ": " + messageContent + "\n");
		chatBox.setText("");
		chatBox.requestFocus();
	}

	public void addTeamMembers(List<String> userNames) {
		teamMembersPanel.removeAll();

		MainWindow mainWindow = this;
		JLabel[] labelList = new JLabel[userNames.size()];
		for (int i = 0; i < userNames.size(); i++) {
			labelList[i] = new JLabel();
			labelList[i].setText(userNames.get(i));
			labelList[i].setPreferredSize(new Dimension(180, 15));
			labelList[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
						String otherUserInConversationString = null;
						if (clientState.getPositionOfChannelGivenName((otherUserInConversationString = ((JLabel) e.getComponent()).getText())) < 0) // if channel is not added
						{
							Channel channel = new Channel();
							channel.setChannelType(ChannelType.Conversation);
							List<String> usernamesOfConversationParticipants = new ArrayList<String>();
							usernamesOfConversationParticipants.add(otherUserInConversationString);
							usernamesOfConversationParticipants.add(clientState.getLoggedUsername());
							channel.setUsernamesOfUserAssignedToChannel(usernamesOfConversationParticipants);
							channel.setName(otherUserInConversationString);
							clientState.addNewChannel(channel);
							int positionOnListOfNewChannel = clientState
									.getPositionOfChannelGivenName(channel.getName());
							mainWindow.addChannelGivenItsPostionInList(channel.getName(), positionOnListOfNewChannel);
							mainWindow.repaint();
						}
						setCurrentChannelOnViewGivenName(otherUserInConversationString);
						clientState.setCurrentlyDisplayedChannelName(otherUserInConversationString);
						clientState.setConversationChosenByUser(true);
					} else if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1 && clientState.isTeamOwner()) {
						String teamMemberToRemoveUsername = ((JLabel) e.getComponent()).getText();
						mainWindow.clientState.setNameOfTeammemberClickedWithRightButton(teamMemberToRemoveUsername);
						popupMenuTeamMembersListPanel.show(e.getComponent(), e.getX(), e.getY());
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
			teamMembersPanel.add(labelList[i]);
		}
		this.repaint();
		this.revalidate();
	}
	
	public void addChannelMembers(List<String> userNames) {
		channelMembersPanel.removeAll();

		MainWindow mainWindow = this;
		JLabel[] labelList = new JLabel[userNames.size()];
		for (int i = 0; i < userNames.size(); i++) {
			labelList[i] = new JLabel();
			labelList[i].setText(userNames.get(i));
			labelList[i].setPreferredSize(new Dimension(180, 15));
			labelList[i].addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						if (clientState.getChannelByName(clientState.getCurrentlyDisplayedChannelName()).getChannelType().equals(ChannelType.Private)) {
							popupMenuTeamMembersListPanel.setInvoker(e.getComponent());
							popupMenuTeamMembersListPanel.show(e.getComponent(), e.getX(), e .getY());
						}
					} else {
						if(e.getClickCount() == 2)
						{
							String otherUserInConversationString;
							if(clientState.getPositionOfChannelGivenName((otherUserInConversationString = ((JLabel)e.getComponent()).getText()))<0) //if channel is not added
							{
								Channel channel = new Channel();
								channel.setChannelType(ChannelType.Conversation);
								List<String> usernamesOfConversationParticipants = new ArrayList<String>();
								usernamesOfConversationParticipants.add(otherUserInConversationString);
								usernamesOfConversationParticipants.add(clientState.getLoggedUsername());
								channel.setUsernamesOfUserAssignedToChannel(usernamesOfConversationParticipants);
								channel.setName(otherUserInConversationString);
								clientState.addNewChannel(channel);
								int positionOnListOfNewChannel = clientState.getPositionOfChannelGivenName(channel.getName());
								mainWindow.addChannelGivenItsPostionInList(channel.getName(), positionOnListOfNewChannel);
								mainWindow.repaint();
							}
							setCurrentChannelOnViewGivenName(otherUserInConversationString);
							clientState.setCurrentlyDisplayedChannelName(otherUserInConversationString);
							clientState.setConversationChosenByUser(true);
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}});
			channelMembersPanel.add(labelList[i]);
		}
		this.repaint();
		this.revalidate();
	}

	public void addChannelGivenItsPostionInList(String channelName, int positionInList) {
		JLabel newChannel = new JLabel();
		newChannel.setText(channelName);
		newChannel.setPreferredSize(new Dimension(100, 15));
		newChannel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					JLabel channelJLabel = (JLabel) e.getComponent();
					String nameOfClickedChannel = channelJLabel.getText();
					setCurrentChannelOnViewGivenName(nameOfClickedChannel);
					clientState.setCurrentlyDisplayedChannelName(nameOfClickedChannel);
					clientState.setConversationChosenByUser(clientState.getChannelByName(nameOfClickedChannel)
							.getChannelType().equals(ChannelType.Conversation));
					
					tabbedPanel.getComponentAt(0).setVisible(true);
					if (!clientState.isConversationChosenByUser()) {
						ChannelMembersRequestMessage channelMembersRequestMessage = new ChannelMembersRequestMessage();
						channelMembersRequestMessage.setChannelName(nameOfClickedChannel);
						teamChatClient.sendMessage(channelMembersRequestMessage);
					} else {
						addChannelMembers(Collections.singletonList(nameOfClickedChannel));
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					String channelName = ((JLabel) e.getComponent()).getText();
					clientState.setNameOfChannelClickedWithRightButton(channelName);
					invitePersonWindow.setChannelName(channelName);
					popupMenuChannelListPanel.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		JLabel channelLabelReplacedByNewChannelForGivenPosition = null;
		if (positionInList < channelsList.size()) {
			channelLabelReplacedByNewChannelForGivenPosition = channelsList.get(positionInList);
			List<JLabel> tailOfChannelsListFromGivenPosition = channelsList.subList(positionInList,
					channelsList.size());
			this.channelsList.set(positionInList, newChannel);
			for (int i = 0; i < tailOfChannelsListFromGivenPosition.size() - 1; i++) {
				this.channelsList.set(positionInList + i + 1, tailOfChannelsListFromGivenPosition.get(i));
			}
			this.channelsList
					.add(tailOfChannelsListFromGivenPosition.get(tailOfChannelsListFromGivenPosition.size() - 1));
		} else {
			this.channelsList.add(newChannel);
		}

		if (channelLabelReplacedByNewChannelForGivenPosition != null) // it neans if we don't append JLabel to the end of list
		{
			int positionOfChannelInChannelListPanel = getPositionOfChannelInChannelListPanelGivenChannelName(
					channelLabelReplacedByNewChannelForGivenPosition.getText());
			List<java.awt.Component> tailOfComponentsFromChannelList = getTailOfComponentsFromChannelListPanelStartingFromPosition(
					positionOfChannelInChannelListPanel);
			this.removeComponentsFromChannelListPanle(tailOfComponentsFromChannelList);
			this.channelListPanel.add(newChannel);
			this.addComponents(tailOfComponentsFromChannelList);
		} else {
			this.channelListPanel.add(newChannel);
		}
		if (channelNameTochatAreaMap.get(channelName) == null) {
			addChannelGivenNameToChatAreaMap(channelName);
		}
		this.repaint();
		this.revalidate();
	}

	private List<java.awt.Component> getTailOfComponentsFromChannelListPanelStartingFromPosition(int position) {
		List<java.awt.Component> tailOfJLabelsTakenFromChannelListPanel = new ArrayList<java.awt.Component>();
		for (int i = position; i < this.channelListPanel.getComponentCount(); i++) {
			tailOfJLabelsTakenFromChannelListPanel.add(this.channelListPanel.getComponent(i));
		}
		return tailOfJLabelsTakenFromChannelListPanel;
	}

	private void removeComponentsFromChannelListPanle(Collection<java.awt.Component> components) {
		for (java.awt.Component component : components) {
			this.channelListPanel.remove(component);
		}
	}

	private void addComponents(Collection<java.awt.Component> components) {
		for (java.awt.Component component : components) {
			this.channelListPanel.add(component);
		}
	}

	public int getPositionOfChannelInChannelListPanelGivenChannelName(String channelName) {
		for (int i = 0; i < this.channelListPanel.getComponentCount(); i++) {
			java.awt.Component currentComponent = null;
			if (((currentComponent = this.channelListPanel.getComponent(i)) instanceof JLabel)
					&& ((JLabel) currentComponent).getText().equals(channelName)) {
				return i;
			}
		}
		return -1;
	}

	public void removeChannelList() {
		channelListPanel.removeAll();
	}

}
