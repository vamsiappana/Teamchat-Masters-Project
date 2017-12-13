package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.TeamMessage;

@Component("TeamMessageHandler")
public class TeamMessageHandler implements MessageHandler {

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		TeamMessage teamMessage = (TeamMessage) message;
		this.clientState.setLoggedUserTeamName(teamMessage.getTeamName());
		return false;
	}
}
