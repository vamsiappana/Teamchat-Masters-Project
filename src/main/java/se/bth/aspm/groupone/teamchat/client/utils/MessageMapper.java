package se.bth.aspm.groupone.teamchat.client.utils;

import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.model.messages.DisplayMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewChannelMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewConversationMessageMessage;
@Component
public class MessageMapper {

	public DisplayMessage convertNewChannelMessageMessageToDisplayMessage(NewChannelMessageMessage newChannelMessageMessage)
	{
		DisplayMessage displayMessage = new DisplayMessage();
		displayMessage.setContent(newChannelMessageMessage.getContent());
		displayMessage.setDateOfMessage(newChannelMessageMessage.getDateOfMessage());
		displayMessage.setSenderUsername(newChannelMessageMessage.getSenderUsername());
		return displayMessage;
	}
	
	public DisplayMessage convertNewConversationMessageMessageToDisplayMessage(NewConversationMessageMessage newConversationMessageMessage)
	{
		DisplayMessage displayMessage = new DisplayMessage();
		displayMessage.setContent(newConversationMessageMessage.getContent());
		displayMessage.setDateOfMessage(newConversationMessageMessage.getDateOfMessage());
		displayMessage.setSenderUsername(newConversationMessageMessage.getSenderUsername());
		return displayMessage;
	}
	
}
