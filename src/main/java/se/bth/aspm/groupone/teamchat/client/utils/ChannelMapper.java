package se.bth.aspm.groupone.teamchat.client.utils;

import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.model.Channel;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.ChannelMessage;

@Component
public class ChannelMapper {
	
	public Channel convertChannelMessageToChannel(ChannelMessage channelMessage)
	{
		Channel channel = new Channel();
		channel.setChannelType(channelMessage.getChannelType());
		channel.setName(channelMessage.getChannelName());
		channel.setUsernamesOfUserAssignedToChannel(channelMessage.getDisplayNamesOfUsersAssignedToChannel());
		return channel;
	}
}
