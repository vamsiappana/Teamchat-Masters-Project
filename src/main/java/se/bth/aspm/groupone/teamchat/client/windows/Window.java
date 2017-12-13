package se.bth.aspm.groupone.teamchat.client.windows;

public interface Window {

	void showMessage(String title, String message);

	void showWindow();

	void hideWindow();

	void initialize();
	
	void clearAndReinitialize();
}
