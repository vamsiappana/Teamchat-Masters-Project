package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.InitialDataMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.JoinTeamResponseMessage;

@Component("JoinTeamResponseMessageHandler")
public class JoinTeamResponseMessageHandler implements MessageHandler {

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
		JoinTeamResponseMessage joinTeamResponseMessage = (JoinTeamResponseMessage) message;
		Integer responseCode = joinTeamResponseMessage.getResponseCode();

		switch (responseCode) {
			case JoinTeamResponseMessage.RESPONSE_CODE__OK:
				InitialDataMessage initialDataMessage = new InitialDataMessage();
				teamChatClient.sendMessage(initialDataMessage);
				createOrJoinTeamWindow.hideWindow();
				mainWindow.showWindow();
				break;

			case JoinTeamResponseMessage.RESPONSE_CODE__INVALID_INVITATION_KEY_OR_BAD_TEAMNAME:
				createOrJoinTeamWindow.showMessage("Error", "Invalid invitation key.");
				return false;
		}


		return true;
	}
}
