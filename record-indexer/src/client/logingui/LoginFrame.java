package client.logingui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import shared.communication.ValidateUser_Result;
import client.Controller;
import client.maingui.Run;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame{

	public LoginFrame(){
		super();

		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(700, 350);
		this.setSize(400, 130);
		this.setResizable(false); 
		
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JLabel userlabel = new JLabel("username");
		JTextField usertext = new JTextField();
		usertext.setPreferredSize(new Dimension(100, 20));
		
		p1.add(userlabel);
		p1.add(usertext);
		
		JLabel passwordlabel = new JLabel("password");
		JPasswordField passwordtext = new JPasswordField();
		passwordtext.setPreferredSize(new Dimension(100, 20));
		
		p2.add(passwordlabel);
		p2.add(passwordtext);
		
		JButton loginb = new JButton("Login");
		JButton exitb = new JButton("Exit");
		ActionListener loginActList = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(Controller.validateUser(usertext.getText(),passwordtext.getText())){
					JOptionPane.showMessageDialog(new JFrame(),
						    "Welcome " + Controller.getUser().getFirstname() + " " 
						    		+Controller.getUser().getLastname()+".\n" 
						    		+"You have indexed "
						    		+ Controller.getUser().getIndexrecords() 
						    		+" records",
						    "Welcome to Indexer",
						    JOptionPane.PLAIN_MESSAGE);
					Run.toggle();
				}
				else{
					JOptionPane.showMessageDialog(new JFrame(),
						    "Invalid username and/or password",
						    "Login Failed",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		};
		ActionListener exitActList = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exitb.addActionListener(exitActList);
		loginb.addActionListener(loginActList);
		
		p3.add(loginb);
		p3.add(exitb);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		
		this.add(p);
	
		
	}

}



