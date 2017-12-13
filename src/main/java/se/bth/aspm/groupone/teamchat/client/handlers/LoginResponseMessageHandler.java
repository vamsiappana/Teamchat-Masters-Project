package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.InitialDataMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.MessagesRequestMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.LoginResponseMessage;

@Component("LoginResponseMessageHandler")
public class LoginResponseMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("loginWindow")
	private Window loginWindow;

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	@Qualifier("CreateOrJoinTeamWindow")
	private Window createOrJoinTeamWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
		String content = loginResponseMessage.getContent();

		switch (content) {
			case LoginResponseMessage.LOGIN_FAIL:
				loginWindow.showMessage("Login response:", "Invalid credentials");
				return false;

			case LoginResponseMessage.LOGIN_OK:
				mainWindow.showWindow();
				InitialDataMessage initialDataMessage = new InitialDataMessage();
				teamChatClient.sendMessage(initialDataMessage);
				break;

			case LoginResponseMessage.LOGIN_OK__USER_WITHOUT_TEAM:
				createOrJoinTeamWindow.showWindow();
				break;
		}

		loginWindow.hideWindow();
		clientState.setLoggedUsername(loginResponseMessage.getUsername());
		return true;
	}
}
