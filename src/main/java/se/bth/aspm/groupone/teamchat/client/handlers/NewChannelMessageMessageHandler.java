package se.bth.aspm.groupone.teamchat.client.handlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewChannelMessageMessage;

@Component("NewChannelMessageMessageHandler")
public class NewChannelMessageMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		MainWindow mainWindow = (MainWindow) this.mainWindow;

		NewChannelMessageMessage newChannelMessageMessage = (NewChannelMessageMessage) message;
		clientState.addNewChannelMessageToReceivedMessages(newChannelMessageMessage);
		String senderUsername = newChannelMessageMessage.getSenderUsername();
		String channelName = newChannelMessageMessage.getChannelName();
		String messageContent = newChannelMessageMessage.getContent();
		Date dateOfMessage = newChannelMessageMessage.getDateOfMessage();

		mainWindow.addMessageToChannel(channelName, senderUsername, messageContent, dateOfMessage);
		mainWindow.repaint();
		return false;
	}
}
