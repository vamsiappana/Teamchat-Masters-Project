package se.bth.aspm.groupone.teamchat.client.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import se.bth.aspm.groupone.teamchat.protocol.enums.ChannelType;

public class Channel {
	
	@Setter @Getter
	private String name;
	
	@Setter @Getter
	private List<String> usernamesOfUserAssignedToChannel;
	
	@Setter @Getter
	private ChannelType channelType;

}
