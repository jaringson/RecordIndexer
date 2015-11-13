package client.maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import shared.model.Project;
import client.*;
import client.BatchState.BatchStateListener;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class DownloadBatch extends JDialog{
	
	JComboBox<String> projectscombo;
	ArrayList<Project> allprojects;
	
	public DownloadBatch(){
		
		JPanel p0 =new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		
		JLabel projectlabel = new JLabel("Project: ");
		projectscombo = new JComboBox<String>();
		projectscombo.setPreferredSize(new Dimension(100, 20));
		allprojects = Controller.getProjects();
		for (Project p :allprojects){
			projectscombo.addItem(p.getTitle());
		}
		JButton getSampleImage = new JButton("View Sample");
		getSampleImage.addActionListener(new sampleActList());
		
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(projectlabel);
		p1.add(projectscombo);
		p1.add(Box.createRigidArea(new Dimension(5,5)));
		p1.add(getSampleImage);
		p1.setSize(new Dimension(350,5));
		
		JButton cancel = new JButton("Cancel");
		JButton download = new JButton("Download");
		cancel.addActionListener(new cancelActList());
		
		download.addActionListener(new downloadActList());
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(cancel);
		p2.add(Box.createRigidArea(new Dimension(5,5)));
		p2.add(download);
//		JFrame frame = new JFrame();
//		ArrayList<Project> allprojects = Controller.getProjects();
//		if(allprojects != null){
//			Object[] possibilities = new Object[allprojects.size()];
//			int i=0;
//			for (Project p :allprojects){
//				//System.out.println(i);
//				possibilities[i] =p.getTitle();
//				i++;
//			}
//			String s = (String)JOptionPane.showInputDialog(
//			                    frame,
//			                    "Projects: ",
//			                    "Download Batch",
//			                    JOptionPane.PLAIN_MESSAGE,
//			                    null, 
//			                    possibilities,
//			                    "");
//			if ((s != null) && (s.length() > 0)){
//				for(Project p :allprojects){
//					if(p.getTitle().equals(s)){
//						Controller.setProject(p);
//					}
//				}
//			}
//		}
		p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

		p0.add(p1);
		p0.add(Box.createRigidArea(new Dimension(350,5)));
		p0.add(p2);
		
		this.add(p0);
		
		this.pack();
		this.setLocation(600, 400);
		this.setModal(true);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	class sampleActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog sample = new JDialog();
			JPanel p = new JPanel();
			p.setSize(500, 500);
			
			
			
//			JButton b = (JButton) e.getSource();
//			JPanel p1 = (JPanel) b.getParent();
//			JComboBox<String> combo = (JComboBox<String>) p1.getComponent(1);
			String projectname = (String) projectscombo.getSelectedItem();
			for(Project curproject: allprojects){
				if(curproject.getTitle().equals(projectname)){
					String ss = Controller.getSampleImage(curproject.getId());
					System.out.print(ss);
					JEditorPane website;
					website = new JEditorPane();
					website.setEditable(false);
//						website.setOpaque(true);
//						website.setBackground(Color.white);
						try {
							website.setPage(ss);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//						JScrollPane htmlScrollPane = new JScrollPane(website);
//				        htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					sample.add(website);
				}
			}
			
			
			
//			sample.add(p);
			sample.setSize(500,500);
			sample.setModal(true);
			sample.setLocation(200, 200);
			sample.setVisible(true);
		
		}



	};

}

class cancelActList implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton j = (JButton) e.getSource();
		DownloadBatch d = (DownloadBatch) j.getTopLevelAncestor();
		d.dispose();	
	}
}
class downloadActList implements ActionListener, BatchStateListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		JPanel p2 = (JPanel) b.getParent();
		JPanel p0 = (JPanel) p2.getParent();
		JPanel p1 = (JPanel) p0.getComponent(0);
		JComboBox<String> combo = (JComboBox<String>) p1.getComponent(1);
		
		
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}
}
