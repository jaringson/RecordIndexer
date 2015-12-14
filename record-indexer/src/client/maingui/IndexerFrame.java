package client.maingui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.*;

import client.BatchState;
import client.Controller;
import client.image.ImageComp;
import client.image.ImageNav;
import client.qualitycheck.QualityCheck;
import client.table.TableEntry;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame {
	

	private JSplitPane horPane;
	private JSplitPane vertiPane;
	
	
	public int getVerSlide() {
		return vertiPane.getDividerLocation();
	}


	public int getHorSlide() {
		return horPane.getDividerLocation();
	}


	@SuppressWarnings("static-access")
	public IndexerFrame(BatchState bState){
		super();
		BatchState.frame= this;
		
		
		this.setTitle("Indexer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(bState.xLocation, bState.yLocation);
		this.setSize(bState.width, bState.height);
		
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
					Controller.Save();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
                e.getWindow().dispose();
            }
        });
		
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new TopButtonPanel(bState), BorderLayout.NORTH);
		
		
		JTabbedPane left = new JTabbedPane(); 
		JScrollPane scrollPane1 = new JScrollPane(new TableEntry(bState));
		left.addTab("Table Entry",scrollPane1);
		left.addTab("Form Entry", new FormEntry(bState));
		
		ImageNav imagenav = null;
		ImageComp imagecomp = new ImageComp(bState);
		imagenav = new ImageNav(bState, imagecomp);
		imagecomp.setImageNav(imagenav);
		
		JTabbedPane right = new JTabbedPane();
		JScrollPane scrollPane2 = new JScrollPane(new FieldHelp(bState));
		right.addTab("Image Navigation", imagenav);
		right.addTab("Field Help", scrollPane2);
		
		
		JSplitPane vertiPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, left,right);
		vertiPane.setOrientation(1);
		vertiPane.setDividerLocation(bState.verSlide);
		vertiPane.setResizeWeight(.5);
		this.vertiPane = vertiPane;
		
		JSplitPane horPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagecomp, vertiPane);
		horPane.setPreferredSize(new Dimension(this.getWidth(), 200));
		horPane.setOrientation(0);
		horPane.setDividerLocation(bState.horSlide);
		horPane.setResizeWeight(.5);
		this.horPane  = horPane;
		
		p.add(horPane, BorderLayout.CENTER);
		
		this.add(new FileMenu(bState), BorderLayout.NORTH);
		this.add(p, BorderLayout.CENTER);
		this.setVisible(true);
		
		QualityCheck quality = new QualityCheck(bState);
	}


	
}
