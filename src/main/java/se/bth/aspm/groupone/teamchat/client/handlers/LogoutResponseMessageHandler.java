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
import se.bth.aspm.groupone.teamchat.protocol.messages.server.LogoutResponseMessage;

@Component("LogoutResponseMessageHandler")
public class LogoutResponseMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("loginWindow")
	private Window loginWindow;

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		LogoutResponseMessage logoutResponseMessage = (LogoutResponseMessage) message;
		String content = logoutResponseMessage.getContent();

		switch (content) {
			case LogoutResponseMessage.LOGOUT_OK:
				clientState.clearAndReinitialize();
				mainWindow.hideWindow();
				mainWindow.clearAndReinitialize();
				loginWindow.showWindow();
				loginWindow.showMessage("Logout: ", logoutResponseMessage.getUsername() + " logged out.");
				break;
		}
		return true;
	}
}
