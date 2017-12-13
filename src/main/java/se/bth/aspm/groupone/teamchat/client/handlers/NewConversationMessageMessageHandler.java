package se.bth.aspm.groupone.teamchat.client.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.model.Channel;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.enums.ChannelType;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewChannelMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewConversationMessageMessage;

@Component("NewConversationMessageMessageHandler")
public class NewConversationMessageMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		MainWindow mainWindow = (MainWindow) this.mainWindow;

		NewConversationMessageMessage newChannelMessageMessage = (NewConversationMessageMessage) message;
		clientState.addNewConversationMessageToReceivedMessages(newChannelMessageMessage);
		String senderUsername = newChannelMessageMessage.getSenderUsername();
		String channelName = clientState.getLoggedUsername().equals(newChannelMessageMessage.getSenderUsername()) ? newChannelMessageMessage.getOtherParticipantUsername() : newChannelMessageMessage.getSenderUsername();
		if(!mainWindow.existsChannelWithGivenName(channelName))
		{
			Channel channel = new Channel();
			channel.setChannelType(ChannelType.Conversation);
			List<String> otherUserInConversation = new ArrayList<String>();
			otherUserInConversation.add(newChannelMessageMessage.getSenderUsername());
			otherUserInConversation.add(newChannelMessageMessage.getOtherParticipantUsername());
			channel.setUsernamesOfUserAssignedToChannel(otherUserInConversation);
			channel.setName(newChannelMessageMessage.getSenderUsername());
			clientState.addNewChannel(channel);
			int positionOnListOfNewChannel = clientState.getPositionOfChannelGivenName(channel.getName());
			mainWindow.addChannelGivenItsPostionInList(channel.getName(), positionOnListOfNewChannel);
			mainWindow.repaint();
		}
		String messageContent = newChannelMessageMessage.getContent();
		Date dateOfMessage = newChannelMessageMessage.getDateOfMessage();

		mainWindow.addMessageToChannel(channelName, senderUsername, messageContent, dateOfMessage);
		mainWindow.repaint();
		return false;
	}
}
