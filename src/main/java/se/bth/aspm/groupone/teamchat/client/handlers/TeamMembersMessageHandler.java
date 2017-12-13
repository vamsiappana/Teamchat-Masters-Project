package se.bth.aspm.groupone.teamchat.client.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.TeamMembersMessage;

@Component("TeamMembersMessageHandler")
public class TeamMembersMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		MainWindow mainWindow = (MainWindow) this.mainWindow;
		TeamMembersMessage usersMessage = (TeamMembersMessage) message;
		List<String> usernames = usersMessage.getUsers();
		mainWindow.addTeamMembers(usernames);
		for (String username : usernames) {
			System.out.println(username);
		}
		mainWindow.preparePopupMenuTeammembersListPanel();
		return false;
	}
}
