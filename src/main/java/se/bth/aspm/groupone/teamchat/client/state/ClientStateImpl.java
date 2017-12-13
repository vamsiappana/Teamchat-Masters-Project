package se.bth.aspm.groupone.teamchat.client.state;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import se.bth.aspm.groupone.teamchat.client.model.Channel;
import se.bth.aspm.groupone.teamchat.client.model.messages.DisplayMessage;
import se.bth.aspm.groupone.teamchat.client.utils.Comparators;
import se.bth.aspm.groupone.teamchat.client.utils.MessageMapper;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewChannelMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewConversationMessageMessage;

@Component
public class ClientStateImpl implements ClientState {

    @Autowired
    private MessageMapper messageMapper;

	@Setter @Getter
	private String loggedUsername;
	
	@Setter @Getter
	private String loggedUserTeamName;
	
	@Setter
	private boolean isTeamOwner = false;
	
	@Setter @Getter
	private boolean isConversationChosenByUser = false;
	
	@Setter @Getter
	private String nameOfChannelClickedWithRightButton;
	
	@Setter @Getter
	private String nameOfTeammemberClickedWithRightButton;

	private Map<String, List<DisplayMessage>> channelNameToDisplayMessagesMap = new HashMap<String, List<DisplayMessage>>();
	
	@Setter @Getter
	private String currentlyDisplayedChannelName;
	
	private Map<String, Channel> channelNameToChannelObject = new TreeMap<String, Channel>();
	
	private Map<Channel,String> channelChannelObjectToChannelName = new TreeMap<Channel, String>(Comparators.CHANNEL_COMPARATOR);
	
	public boolean isTeamOwner()
	{
		return this.loggedUserTeamName == null ? false : this.isTeamOwner;
	}
	
	public void addNewChannelMessageToReceivedMessages(NewChannelMessageMessage newChannelMessage)
	{
		List<DisplayMessage> storedDisplayMessages;
		if ((storedDisplayMessages = this.channelNameToDisplayMessagesMap.get(newChannelMessage.getChannelName())) == null)
		{
			storedDisplayMessages = new ArrayList<DisplayMessage>();
		}
		storedDisplayMessages.add(messageMapper.convertNewChannelMessageMessageToDisplayMessage(newChannelMessage));
	}
	
	public void addNewConversationMessageToReceivedMessages(NewConversationMessageMessage newConversationMessage)
	{
		String channelName = newConversationMessage.getSenderUsername().equals(this.loggedUsername) ? newConversationMessage.getOtherParticipantUsername() : newConversationMessage.getSenderUsername(); 
		List<DisplayMessage> storedDisplayMessages;
		if ((storedDisplayMessages = this.channelNameToDisplayMessagesMap.get(channelName)) == null)
		{
			storedDisplayMessages = new ArrayList<DisplayMessage>();
		}
		storedDisplayMessages.add(messageMapper.convertNewConversationMessageMessageToDisplayMessage(newConversationMessage));
	}


	@Override
	public void addNewChannel(Channel channel) {
		this.channelNameToChannelObject.put(channel.getName(), channel);
		this.channelChannelObjectToChannelName.put(channel,channel.getName());
	}
	
	@Override
	public int getPositionOfChannelGivenName(String channelName) {
		int i=0;
		for(Channel currentChannel: this.channelChannelObjectToChannelName.keySet())
		{
			if(currentChannel.getName().equals(channelName))
				return i;
			i++;
		}
		return -1;
	}

	@Override
	public List<String> getSortedNamesOfChannels() {
		return new ArrayList<String>(this.channelChannelObjectToChannelName.values());
	}
	
	public Channel getChannelByName(String channelName)
	{
		return this.channelNameToChannelObject.get(channelName);
	}

	@Override
	public void clearAndReinitialize() {
		this.setLoggedUsername(null);
		this.setLoggedUserTeamName(null);
		this.setConversationChosenByUser(false);
		this.setNameOfChannelClickedWithRightButton(null);
		this.setCurrentlyDisplayedChannelName(null);
		this.channelNameToDisplayMessagesMap.clear();
		this.channelNameToChannelObject.clear();
		this.channelChannelObjectToChannelName.clear();
	}

	@Override
	public void removeUserFromChannelsGivenUsername(String username) {
		for(Channel channel: channelChannelObjectToChannelName.keySet())
		{
			if(channel.getUsernamesOfUserAssignedToChannel() != null)
			{
				channel.getUsernamesOfUserAssignedToChannel().remove(username);
			}
		}
		
	}
	
}
