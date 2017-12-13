package se.bth.aspm.groupone.teamchat.client.handlers;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.RegisterResponseMessage;

@Component("RegisterResponseMessageHandler")
public class RegisterResponseMessageHandler implements MessageHandler {

	@Resource(name = "signinWindow")
	private Window signinWindow;

	@Resource(name = "loginWindow")
	private Window loginWindow;
	
	@Resource(name = "mainWindow")
	private Window mainWindow;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		RegisterResponseMessage registerResponseMessage = (RegisterResponseMessage) message;
		String content = registerResponseMessage.getContent();
		String error = registerResponseMessage.getErrorInfromation();
		
		if (null != content && content.equals(RegisterResponseMessage.REGISTER_OK)) {

			loginWindow.showWindow();
			signinWindow.hideWindow();
			return true;
		}
		
		String er= error.replace(". ", "\n");
		signinWindow.showMessage("Register response:", er);

		
		return false;
	}
}
