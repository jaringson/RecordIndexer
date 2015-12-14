package client.maingui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;

import client.BatchState;
import client.Controller;


@SuppressWarnings("serial")
public class TopButtonPanel extends JPanel {
	BatchState bState;
	Boolean on;
	Boolean inverted = false;
	
	public TopButtonPanel(BatchState bState){
//		bState.addListener(this);
		this.bState = bState;
		on = bState.highlightOn;
		this.setPreferredSize(new Dimension(0, 35));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
		
		JButton zoomin = new JButton("Zoom In");
		zoomin.addActionListener(new zoomActList());
		JButton zoomout = new JButton("Zoom Out");
		zoomout.addActionListener(new zoomActList());
		JButton invert = new JButton("Invert Image");
		invert.addActionListener(new invertActList());
		JButton toggle = new JButton("Toggle Highlights");
		toggle.addActionListener(new toggleActList());
		JButton save = new JButton("Save");
		save.addActionListener(new saveActList());
		JButton submit = new JButton("Submit");
		submit.addActionListener(new submitActList());
		
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(zoomin);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(zoomout);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(invert);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(toggle);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(save);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(submit);
		this.add(Box.createGlue());
	
		
	}
	
	
	class toggleActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(on){
				on =false;
				bState.toggleHighLight(false);
			}
			else{
				on = true;
				bState.toggleHighLight(true);
			}
		}
	};
	
	class zoomActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("Zoom In")){
				bState.zoom(true);
			}
			else{
				bState.zoom(false);
			}
		}
	};
	class invertActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(inverted){
				inverted=false;
				bState.invertImage(false);
			}
			else{
				inverted=true;
				bState.invertImage(true);
			}
		}
	};
	class saveActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Controller.Save();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	class submitActList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			bState.submitBatch();
		}
	};

}
