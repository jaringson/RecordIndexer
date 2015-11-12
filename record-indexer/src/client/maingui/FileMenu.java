package client.maingui;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import shared.model.Project;
import client.Controller;

@SuppressWarnings("serial")
public class FileMenu extends JMenuBar{
	public FileMenu(){
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

				DownloadBatch d = new DownloadBatch();
				
	
			}
		};
		downloadMenuItem.addActionListener(downloadActList);
		
		menu.add(downloadMenuItem);
		
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		menu.add(logoutMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);
	}
}
