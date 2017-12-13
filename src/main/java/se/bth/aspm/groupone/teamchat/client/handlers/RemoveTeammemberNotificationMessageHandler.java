package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.MessagesRequestMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.LoginResponseMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.RemoveTeammemberNotificationMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.RemoveTeammemberResponseMessage;

@Component("RemoveTeammemberNotificationMessageHandler")
public class RemoveTeammemberNotificationMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;
	
	@Autowired
	@Qualifier("loginWindow")
	private Window loginWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		RemoveTeammemberNotificationMessage removeTeammemberNotificationMessage = (RemoveTeammemberNotificationMessage) message;
		String usernameOfRemovedTeammember = removeTeammemberNotificationMessage.getUsernameOfRemovedUser();
		if(usernameOfRemovedTeammember !=null && usernameOfRemovedTeammember.equals(clientState.getLoggedUsername()))
		{
			mainWindow.hideWindow();
			mainWindow.clearAndReinitialize();
			loginWindow.showWindow();
			loginWindow.showMessage("Removed from team.","You have been removed from team. Please relogin in order to create own team or join to team.");
		}
		else
		{
			clientState.removeUserFromChannelsGivenUsername(usernameOfRemovedTeammember);
		}
		return true;
	}
}
