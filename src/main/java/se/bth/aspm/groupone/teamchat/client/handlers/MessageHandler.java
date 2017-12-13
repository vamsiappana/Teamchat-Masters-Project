package se.bth.aspm.groupone.teamchat.client.handlers;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.protocol.Message;

public interface MessageHandler {
	boolean handleMessage(TeamChatClient teamChatClient, Message message);
}
