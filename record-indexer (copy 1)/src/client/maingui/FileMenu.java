package client.maingui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import client.BatchState;
import client.Controller;

@SuppressWarnings("serial")
public class FileMenu extends JMenuBar{
	
	public FileMenu(BatchState bState){
//		this.setName("File");
//		JMenuBar menuBar = new JMenuBar();
		this.setPreferredSize(new Dimension(0,25));
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		this.add(menu);
	
		JMenuItem downloadMenuItem = new JMenuItem("Download Batch");
		ActionListener downloadActList = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Controller.getBatch() == null){
					new DownloadBatch(bState);
				}
				else{
					JOptionPane.showMessageDialog(new JFrame(), "A Batch is Already Downloaded");
				}
				
			}
		};
		downloadMenuItem.addActionListener(downloadActList);
		menu.add(downloadMenuItem);
		
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		ActionListener logoutActList = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		};
		logoutMenuItem.addActionListener(logoutActList);
		menu.add(logoutMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		ActionListener exitActList = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//save data
				System.exit(0);
			}
		};
		exitMenuItem.addActionListener(exitActList);
		menu.add(exitMenuItem);
	}
}
