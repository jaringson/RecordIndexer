package client.logingui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame{

	public LoginFrame(){
		super();

		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(600, 350);
		this.setSize(300, 150);
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		JLabel userlabel = new JLabel("username");
		JTextField usertext = new JTextField();
		usertext.setPreferredSize(new Dimension(100, 20));
		
		p1.add(userlabel);
		p1.add(usertext);
		
		JLabel passwordlabel = new JLabel("password");
		JTextField passwordtext = new JTextField();
		passwordtext.setPreferredSize(new Dimension(100, 20));
		
		p2.add(passwordlabel);
		p2.add(passwordtext);
		
		JButton loginb = new JButton("Login");
		JButton exitb = new JButton("Exit");
		
		p3.add(loginb);
		p3.add(exitb);
		
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);
	
		
	}
	public ActionListener listener = new ActiveListener(ActionEvent e){
		if(e.getSource() == "Exit"){
			System.out.pringln("Here is Exit");
		}
	};
}



