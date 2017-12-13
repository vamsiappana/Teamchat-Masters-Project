package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;

@Component("ChannelListMessageHandler")
public class ChannelListMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;
	
	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
/*
		MainWindow mainWindow = (MainWindow) this.mainWindow;

		ChannelListMessage channelListMessage = (ChannelListMessage) message;
		List<String> channels = channelListMessage.getChannels();
		clientState.addNewChannels(channels);
		mainWindow.addChannelList(clientState.getChannels());*/
		return false;
	}
}
