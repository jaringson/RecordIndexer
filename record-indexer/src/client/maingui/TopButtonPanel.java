package client.maingui;

import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class TopButtonPanel extends JPanel{
	public TopButtonPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JButton zoomin = new JButton("Zoom In");
		JButton zoomout = new JButton("Zoom Out");
		JButton invert = new JButton("Invert Image");
		JButton toggle = new JButton("Toggle Highlights");
		JButton save = new JButton("Save");
		JButton submit = new JButton("Submit");
		
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
}
