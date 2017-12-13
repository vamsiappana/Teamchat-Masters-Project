package se.bth.aspm.groupone.teamchat.client.state;

import java.util.List;
import java.util.Set;

import se.bth.aspm.groupone.teamchat.client.model.Channel;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewChannelMessageMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.NewConversationMessageMessage;

public interface ClientState {

	public String getLoggedUsername();

	public void setLoggedUsername(String username);
	
	public void addNewChannelMessageToReceivedMessages(NewChannelMessageMessage newChannelMessage);
	
	public void addNewConversationMessageToReceivedMessages(NewConversationMessageMessage newConversationMessage);
	
	public String getCurrentlyDisplayedChannelName();
	
	public void setCurrentlyDisplayedChannelName(String currentlyDisplayedChannelName);
	
	public void addNewChannel(Channel channels);
	
	public List<String> getSortedNamesOfChannels();
	
	public String getLoggedUserTeamName();
	
	public void setLoggedUserTeamName(String teamName);

	int getPositionOfChannelGivenName(String channelName);
	
	boolean isConversationChosenByUser();
	
	void setConversationChosenByUser(boolean isConversationChosenByUser);
	
	public Channel getChannelByName(String channelName);
	
	public String getNameOfChannelClickedWithRightButton();
	
	public void setNameOfChannelClickedWithRightButton(String channelName);
	
	public String getNameOfTeammemberClickedWithRightButton();
	
	public void setNameOfTeammemberClickedWithRightButton(String teamName);
	
	public void clearAndReinitialize();
	
	public void setTeamOwner(boolean isTeamOwner);
	
	public void removeUserFromChannelsGivenUsername(String username);
	
	public boolean isTeamOwner();
}
