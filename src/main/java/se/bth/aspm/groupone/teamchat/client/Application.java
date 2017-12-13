package se.bth.aspm.groupone.teamchat.client;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.bth.aspm.groupone.teamchat.client.windows.LoginWindow;



public class Application {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

		Locale.setDefault(Locale.ENGLISH);
		
		final TeamChatService teamChatService = applicationContext.getBean(TeamChatService.class);
		Thread TeamChatServiceThread = new Thread(teamChatService::start);
		TeamChatServiceThread.start();

		java.awt.EventQueue.invokeLater(() -> {
			LoginWindow loginWindow = applicationContext.getBean(LoginWindow.class);
			loginWindow.setVisible(true);
		});
	}
}