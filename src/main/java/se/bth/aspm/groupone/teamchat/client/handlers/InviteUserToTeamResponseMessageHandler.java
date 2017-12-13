package se.bth.aspm.groupone.teamchat.client.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.TeamChatClient;
import se.bth.aspm.groupone.teamchat.client.state.ClientState;
import se.bth.aspm.groupone.teamchat.client.windows.Window;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.CreateTeamResponseMessage;
import se.bth.aspm.groupone.teamchat.protocol.messages.server.InviteUserToTeamResponseMessage;

@Component("InviteUserToTeamResponseMessageHandler")
public class InviteUserToTeamResponseMessageHandler implements MessageHandler {

	@Autowired
	@Qualifier("InviteUserToTeamWindow")
	private Window inviteUserToTeamWindow;

	@Autowired
	@Qualifier("mainWindow")
	private Window mainWindow;

	@Autowired
	ClientState clientState;

	@Override
	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		InviteUserToTeamResponseMessage inviteUserToTeamResponseMessage = (InviteUserToTeamResponseMessage) message;
		Integer responseCode = inviteUserToTeamResponseMessage.getResponseCode();

		switch (responseCode) {
			case InviteUserToTeamResponseMessage.RESPONSE_CODE__FAIL_OPERATION_NOT_PERMITED:
				inviteUserToTeamWindow.hideWindow();
				mainWindow.showWindow();
				mainWindow.showMessage("Operation not permitted", "You are not a team owner - you cannot invite other people to the team!");
				return false;
				
			case InviteUserToTeamResponseMessage.RESPONSE_CODE__FAIL_EMAIL_NOT_VALID:
				inviteUserToTeamWindow.showMessage("Error", "Requested email: " + inviteUserToTeamResponseMessage.getEmail() + " is not valid!");
				return false;
				
			case InviteUserToTeamResponseMessage.RESPONSE_CODE__FAIL_OTHER:
				inviteUserToTeamWindow.showMessage("Error", "Unexpected error, please try again later!");
				return false;

			case InviteUserToTeamResponseMessage.RESPONSE_CODE__FAIL_USER_ALREADY_EXISTS_AND_IS_ASSIGNED_TO_TEAM:
				inviteUserToTeamWindow.showMessage("Error", "There is already registered user for requested email: " + inviteUserToTeamResponseMessage.getEmail());
				return false;
				
			case InviteUserToTeamResponseMessage.RESPONSE_CODE__OK_USER_REGISTERED_FOR_THIS_EMAIL_BUT_NOT_ASSIGNED_TO_ANY_TEAM:
				inviteUserToTeamWindow.hideWindow();
				mainWindow.showWindow();
				mainWindow.showMessage("Operation successful:","Invitation email has been sent to mailbox: " + inviteUserToTeamResponseMessage.getEmail() 
				+ ", there is already user registered for this email: " + inviteUserToTeamResponseMessage.getUsernameOfUserHavingRequesetedEmail());
				break;
			case InviteUserToTeamResponseMessage.RESPONSE_CODE__OK_NO_USER_REGISTERED_FOR_EMAIL:
				inviteUserToTeamWindow.hideWindow();
				mainWindow.showWindow();
				mainWindow.showMessage("Operation successful:","Invitation email has been sent to mailbox: " + inviteUserToTeamResponseMessage.getEmail());
				break;
		}


		return true;
	}
}
