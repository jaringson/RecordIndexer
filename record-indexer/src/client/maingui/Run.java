package client.maingui;

import java.awt.Event;
import java.awt.EventQueue;

import client.logingui.LoginFrame;

public class Run {
	static LoginFrame loginframe;
	static IndexerFrame indexerframe;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					loginframe = new LoginFrame();			
					indexerframe = new IndexerFrame();
					// Make the frame window visible
					loginframe.setVisible(true);
					indexerframe.setVisible(false);
				}
			}
		);
	}
	public static void toggle(){
		System.out.println("here");
		if(loginframe.isShowing()){
			loginframe.setVisible(false);
			indexerframe.setVisible(true);
		}
		else{
			loginframe.setVisible(true);
			indexerframe.setVisible(false);
		}
	}
	public static void exitall(){
		
	}
}
