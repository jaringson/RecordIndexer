package client.maingui;

import java.awt.EventQueue;

import client.Controller;
import client.logingui.LoginFrame;

public class Run {
	static LoginFrame loginframe;
	static IndexerFrame indexerframe;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					Controller.initialize(args[0], Integer.parseInt(args[1]));
					loginframe = new LoginFrame();			
					indexerframe = new IndexerFrame();
					// Make the frame window visible
					loginframe.setVisible(true);
					indexerframe.setVisible(true);
				}
			}
		);
	}
	
	public static void toggle(){
		if(loginframe.isShowing()){
			loginframe.setVisible(false);
			indexerframe.setVisible(true);
		}
		else{
			loginframe.setVisible(true);
			indexerframe.setVisible(false);
		}
	}
	public static void downloaded (){
		
	}
}
