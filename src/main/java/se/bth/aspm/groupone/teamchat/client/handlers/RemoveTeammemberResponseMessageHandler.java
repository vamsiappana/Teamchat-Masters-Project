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
import se.bth.aspm.groupone.teamchat.protocol.messages.server.RemoveTeammemberResponseMessage;

@Component("RemoveTeammemberResponseMessageHandler")
public class RemoveTeammemberResponseMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		RemoveTeammemberResponseMessage removeTeammemberResponseMessage = (RemoveTeammemberResponseMessage) message;
		String content = removeTeammemberResponseMessage.getContent();

		switch (content) {
			case RemoveTeammemberResponseMessage.REMOVE_OK:
				mainWindow.showMessage("Remove teammember response:", "Remove of user:" + removeTeammemberResponseMessage.getUsername() + " successful.");
				break;

			case RemoveTeammemberResponseMessage.REMOVE_FAIL_USER_DOESNT_EXITST:
				mainWindow.showMessage("Remove teammember response:", "Remove of user:" + removeTeammemberResponseMessage.getUsername() + " failed - user doesn't exist.");
				return false;

			case RemoveTeammemberResponseMessage.REMOVE_FAIL_OTHER:
				mainWindow.showMessage("Remove teammember response:", "Remove of user:" + removeTeammemberResponseMessage.getUsername() + " failed due to uknown reason.");
				return false;
		}
		return true;
	}
}
