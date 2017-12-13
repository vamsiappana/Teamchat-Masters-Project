package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.MessagesRequestMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.LoginResponseMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.UserAdditionalPropertiesMessage;

@Component("UserAdditionalPropertiesMessageHandler")
public class UserAdditionalPropertiesMessageHandler implements MessageHandler {

	@Autowired
	ClientState clientState;
	
	@Autowired
	@Qualifier("mainWindow")
	private MainWindow mainWindow;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		UserAdditionalPropertiesMessage userAdditionalPropertiesMessage = (UserAdditionalPropertiesMessage) message;
		boolean isTeamOwner = userAdditionalPropertiesMessage.isTeamOwner();
		this.clientState.setTeamOwner(isTeamOwner);
		if(isTeamOwner)
		{
			mainWindow.addInviteButtonToTeamMembersListPanel();
		}
		else
		{
			mainWindow.removeInviteButtonToTeamMembersListPanel();
		}
		
		return true;
	}
}
