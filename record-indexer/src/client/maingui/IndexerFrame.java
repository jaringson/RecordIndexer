package client.maingui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame {
	IndexerFrame(){
		super();

		this.setTitle("Indexer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(1200, 800);
		
		new BorderLayout();
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new TopButtonPanel(), BorderLayout.NORTH);
		
		
		JTabbedPane left = new JTabbedPane(); 
		left.addTab("Table Entry",new TableEntry());
		left.addTab("Form Entry", new FormEntry());
		
		JTabbedPane right = new JTabbedPane();
		right.addTab("Field Help", new FieldHelp());
		right.addTab("Image Navigation", new ImageNav());
		
		JSplitPane vertiPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, left,right);
		vertiPane.setOrientation(1);
		vertiPane.setDividerLocation(500);
		
		JSplitPane horPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new View(), vertiPane);
		horPane.setPreferredSize(new Dimension(this.getWidth(), 200));
		horPane.setOrientation(0);
		horPane.setDividerLocation(400);
		
		p.add(horPane, BorderLayout.CENTER);
		
		this.add(new FileMenu(), BorderLayout.NORTH);
		this.add(p, BorderLayout.CENTER);
		
	}
	
}
