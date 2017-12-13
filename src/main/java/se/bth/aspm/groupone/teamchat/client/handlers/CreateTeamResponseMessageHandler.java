package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.CreateTeamResponseMessage;

@Component("CreateTeamResponseMessageHandler")
public class CreateTeamResponseMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("CreateOrJoinTeamWindow")
	private Window createOrJoinTeamWindow;

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		CreateTeamResponseMessage createTeamResponseMessage = (CreateTeamResponseMessage) message;
		Integer responseCode = createTeamResponseMessage.getResponseCode();
		String teamName = createTeamResponseMessage.getTeamName();

		switch (responseCode) {
			case CreateTeamResponseMessage.RESPONSE_CODE__OK:
				createOrJoinTeamWindow.hideWindow();
				mainWindow.showWindow();
				break;

			case CreateTeamResponseMessage.RESPONSE_CODE__TEAM_ALREADY_EXISTS:
				createOrJoinTeamWindow.showMessage("Error", String.format("Team name '%s' is already in use.", teamName));
				return false;
		}


		return true;
	}
}
