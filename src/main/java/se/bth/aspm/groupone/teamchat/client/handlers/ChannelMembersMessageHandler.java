package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.ChannelMembersMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.TeamMembersMessage;

import java.util.List;

@Component("ChannelMembersMessageHandler")
public class ChannelMembersMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		MainWindow mainWindow = (MainWindow) this.mainWindow;
		ChannelMembersMessage channelMembersMessage = (ChannelMembersMessage) message;
		List<String> usernames = channelMembersMessage.getChannelMembers();
		mainWindow.addChannelMembers(usernames);
		return false;
	}
}
