package client.maingui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import shared.model.Project;
import client.*;
import client.BatchState.BatchStateListener;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class DownloadBatch extends JDialog{
	public DownloadBatch(){
		
		JPanel p0 =new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		
		JLabel projectlabel = new JLabel("Project: ");
		JComboBox<String> projectscombo = new JComboBox<String>();
		projectscombo.setPreferredSize(new Dimension(100, 20));
		ArrayList<Project> allprojects = Controller.getProjects();
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
}
class sampleActList implements ActionListener, BatchStateListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}

};
class cancelActList implements ActionListener , BatchStateListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
class downloadActList implements ActionListener, BatchStateListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
