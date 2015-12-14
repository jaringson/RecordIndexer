package client.maingui;

import java.awt.EventQueue;

import client.Controller;
import client.logingui.LoginFrame;

public class Run {
	static String one;
	static String two;
	static LoginFrame loginframe;
	static IndexerFrame indexerframe;
	
	public static void main(String[] args) {
		one = args[0];
		two = args[1];
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					Controller.initialize(args[0], Integer.parseInt(args[1]));
					loginframe = new LoginFrame();			
					loginframe.setVisible(true);
				}
			}
		);
	}
	
	public static void toggle(){
		if(loginframe.isShowing()){
			loginframe.setVisible(false);
		}
	}
}
