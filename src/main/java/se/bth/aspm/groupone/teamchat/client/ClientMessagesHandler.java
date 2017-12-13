package se.bth.aspm.groupone.teamchat.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import se.bth.aspm.groupone.teamchat.client.handlers.MessageHandler;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.MessageSerializer;

@Component
public class ClientMessagesHandler implements MessageHandler {
	private static final Logger LOGGER = Logger.getLogger(ClientMessagesHandler.class);

	@Autowired
	private ApplicationContext applicationContext;

	public boolean handleMessage(TeamChatClient teamChatClient, Message message) {
		String messageHandlerClassName = String.format("%sHandler", message.getCommand());

		MessageHandler messageHandler = (MessageHandler) applicationContext.getBean(messageHandlerClassName);
		if (messageHandler == null) {
			LOGGER.error(String.format("UNRECOGNISED MESSAGE %s", MessageSerializer.serialize(message)));
			return false;
		}

		return messageHandler.handleMessage(teamChatClient, message);
	}
}
