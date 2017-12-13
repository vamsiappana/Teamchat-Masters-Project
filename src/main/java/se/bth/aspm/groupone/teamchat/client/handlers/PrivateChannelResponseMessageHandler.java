package se.bth.aspm.groupone.teamchat.client.handlers;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.PrivateChannelResponseMessage;

@Component("PrivateChannelResponseMessageHandler")
@Transactional
public class PrivateChannelResponseMessageHandler implements MessageHandler {



	@Resource(name = "createChannelWindow")
	private Window createChannelWindow;
	
	@Resource(name = "mainWindow")
	private Window mainWindow;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		PrivateChannelResponseMessage channelResponseMessage = (PrivateChannelResponseMessage) message;
		String content = channelResponseMessage.getContent();
		String error = channelResponseMessage.getErrorInfromation();
		
		if (null != content && content.equals(PrivateChannelResponseMessage.REGISTER_OK)) {

			mainWindow.showWindow();
			createChannelWindow.hideWindow();
			return true;
		}
		
		String er= error.replace(". ", "\n");
		createChannelWindow.showMessage("Register response:", er);

		
		return false;
	}
}
