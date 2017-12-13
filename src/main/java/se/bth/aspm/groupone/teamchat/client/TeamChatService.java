package se.bth.aspm.groupone.teamchat.client;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.protocol.Message;

@Component
public class TeamChatService {
	private static final Logger LOGGER = Logger.getLogger(TeamChatService.class);

	@Autowired
	@Getter
	private TeamChatClient teamChatClient;

	private Boolean isRunning;

	@Autowired
	private ClientMessagesHandler clientMessagesHandler;

	public void start() {
		this.isRunning = true;

		while (this.isRunning) {
			boolean result = teamChatClient.connect();
			if (!result) {
				LOGGER.error("Connection refused or problems with SSL socket configuration");

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}

				continue;
			}

			LOGGER.info("Connected to TeamChat Server");

			while (teamChatClient.isConnected()) {
				Message message = teamChatClient.readMessage();
				if (message == null) {
					LOGGER.error("Connection broken");
					break;
				}

				handleMessage(message);
			}
		}
	}

	public boolean handleMessage(Message message) {
		return clientMessagesHandler.handleMessage(teamChatClient, message);
	}
}
