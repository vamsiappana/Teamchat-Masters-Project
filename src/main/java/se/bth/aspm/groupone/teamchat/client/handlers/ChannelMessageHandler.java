package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.utils.ChannelMapper;
import se.bth.aspm.groupone.teamchat.client.windows.MainWindow;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.enums.ChannelType;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.MessagesRequestMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.ChannelMessage;

@Component("ChannelMessageHandler")
public class ChannelMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;
	
	@Autowired
	ChannelMapper channelMapper;
	
	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {

		MainWindow mainWindow = (MainWindow) this.mainWindow;

		ChannelMessage channelMessage = (ChannelMessage) message;
		
		clientState.addNewChannel(channelMapper.convertChannelMessageToChannel(channelMessage));
		int positionOnListOfNewChannel = clientState.getPositionOfChannelGivenName(channelMessage.getChannelName());
		mainWindow.addChannelGivenItsPostionInList(channelMessage.getChannelName(), positionOnListOfNewChannel);
		
		MessagesRequestMessage messagesRequestMessage = new MessagesRequestMessage();
		messagesRequestMessage.setChannelName(channelMessage.getChannelName());
		messagesRequestMessage.setNumberOfMessages(100);
		if(channelMessage.getChannelType().equals(ChannelType.Conversation))
		{
			messagesRequestMessage.setConversationRequest(Boolean.TRUE);
		}
		else
		{
			messagesRequestMessage.setConversationRequest(Boolean.FALSE);
		}
		teamChatClient.sendMessage(messagesRequestMessage);
		
		mainWindow.repaint();
		return false;
	}
}
