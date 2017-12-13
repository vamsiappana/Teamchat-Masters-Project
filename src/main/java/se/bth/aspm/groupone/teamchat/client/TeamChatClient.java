package se.bth.aspm.groupone.teamchat.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.bth.aspm.groupone.teamchat.client.utils.SSLSocketManager;
import se.bth.aspm.groupone.teamchat.protocol.Message;
import se.bth.aspm.groupone.teamchat.protocol.MessageDeserializer;
import se.bth.aspm.groupone.teamchat.protocol.MessageSerializer;
import se.bth.aspm.groupone.teamchat.protocol.messages.client.ClientMessage;

import java.io.*;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;

public class TeamChatClient {
	private static final Logger LOGGER = Logger.getLogger(TeamChatClient.class);

	private String serverIp;
	private Integer serverPort;

	private SSLSocket clientSocket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	@Autowired
	private MessageDeserializer messageDeserializer;
	
	@Autowired
	SSLSocketManager sslSocketManager;

	public TeamChatClient(String serverIp, Integer serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	public boolean connect() {
		try {
			clientSocket = sslSocketManager.createSSLSocket(serverIp, serverPort);//new Socket(serverIp, serverPort);
			clientSocket.startHandshake();

			InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream(), "Cp1250");
			bufferedReader = new BufferedReader(inputStreamReader);

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), "Cp1250");
			bufferedWriter = new BufferedWriter(outputStreamWriter);
		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean disconnect() {
		LOGGER.info("Shutting down TeamChatClient");

		if (clientSocket == null) {
			LOGGER.error("Socket not initialized");
			return false;
		}

		if (!clientSocket.isConnected() || clientSocket.isClosed()) {
			LOGGER.error("Socket not connected or stream is closed");
			return false;
		}

		try {
			clientSocket.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	public Message readMessage() {
		if (!this.isConnected()) {
			LOGGER.error("Not connected");
			return null;
		}

		try {
			String messageAsText = bufferedReader.readLine();
			if (messageAsText == null) {
				LOGGER.error("Connection broken");
				return null;
			}

			Message message = messageDeserializer.deserialize(messageAsText);

			LOGGER.info(String.format("SERVER -> CLIENT: %s", messageAsText));

			return message;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public boolean sendMessage(ClientMessage message) {
		if (!this.isConnected()) {
			LOGGER.error("Not connected");
			return false;
		}

		try {
			String messageAsText = MessageSerializer.serialize(message);
			bufferedWriter.write(messageAsText);
			bufferedWriter.newLine();
			bufferedWriter.flush();

			LOGGER.info(String.format("CLIENT -> SERVER: %s", MessageSerializer.serialize(message)));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean isConnected() {
		return clientSocket != null && clientSocket.isConnected();
	}
}
