package client.maingui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import shared.communication.DownloadBatch_Result;
import shared.model.Project;
import client.*;

@SuppressWarnings("serial")
public class DownloadBatch extends JDialog {
	
	JComboBox<String> projectscombo;
	ArrayList<Project> allprojects;
	protected static BatchState bState;
	
	public DownloadBatch(BatchState bState){
		
		this.bState = bState;
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
			
			String projectname = (String) projectscombo.getSelectedItem();
			for(Project curproject: allprojects){
				if(curproject.getTitle().equals(projectname)){
					String ss = Controller.getSampleImage(curproject.getId());
					Image image = null;
					try {
						image = ImageIO.read(new URL(ss));
						image = image.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JLabel label = new JLabel(new ImageIcon(image));
					sample.add(label);
				}
			}
			sample.setResizable(false);
			sample.setSize(500,400);
			sample.setModal(true);
			sample.setLocation(200, 200);
			sample.setVisible(true);
		}
	};
	
	class cancelActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			DownloadBatch d = (DownloadBatch) b.getTopLevelAncestor();
			d.dispose();	
		}
	};
	class downloadActList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DownloadBatch_Result result = new DownloadBatch_Result();
			JButton b = (JButton) e.getSource();
			String projectname = (String) projectscombo.getSelectedItem();
			for(Project curproject: allprojects){
				if(curproject.getTitle().equals(projectname)){
					result = Controller.downloadBatch(curproject.getId());
				}
			}
			
			DownloadBatch d = (DownloadBatch) b.getTopLevelAncestor();
			d.dispose();
			
			bState.downloadBatch(result);
		}

	}
}

