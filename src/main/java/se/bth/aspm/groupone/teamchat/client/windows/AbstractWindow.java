package se.bth.aspm.groupone.teamchat.client.windows;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class AbstractWindow extends JFrame implements Window {

	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, "Info - " + title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void showWindow() {
		this.initialize();
		this.setVisible(true);
	}

	public void hideWindow() {
		this.setVisible(false);
	}

	@Override
	public void initialize() {

	}
}
