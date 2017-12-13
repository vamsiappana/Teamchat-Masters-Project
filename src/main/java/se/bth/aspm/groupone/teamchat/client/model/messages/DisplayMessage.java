package se.bth.aspm.groupone.teamchat.client.model.messages;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class DisplayMessage {
	
	@Getter @Setter
	private String senderUsername;
	
	@Getter @Setter
	private Date dateOfMessage;
	
	@Getter @Setter
	private String content;

}
