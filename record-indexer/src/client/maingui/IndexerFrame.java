package client.maingui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame {
	IndexerFrame(){
		super();

		this.setTitle("Indexer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 200);
		this.setSize(700, 700);
//		this.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		
		JMenuItem downloadMenuItem = new JMenuItem("Download Batch");
		menu.add(downloadMenuItem);
		
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		menu.add(logoutMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);
		
		new BorderLayout();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new TopButtonPanel());
		p.add(new View());
		
		this.add(menuBar, BorderLayout.NORTH);
		this.add(p, BorderLayout.CENTER);
		
	}
	
}
