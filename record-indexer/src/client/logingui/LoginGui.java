package client.logingui;

import java.awt.EventQueue;


public class LoginGui {
	public static void main(String[] args) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					LoginFrame frame = new LoginFrame();			
						
					// Make the frame window visible
					frame.setVisible(true);
			
				}
			}
		);
	}
}
