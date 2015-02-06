package org.friet.net.login.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.friet.net.main.Main;

public class PanelLogin extends JPanel {
	
	public JLabel userLabel;
	public JTextField userText;
	public JLabel passwordLabel;
	public JPasswordField passwordText;
	public boolean done = false;
	
	public PanelLogin() {
		userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		add(passwordLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		passwordText.addActionListener(new EventLogin());
		add(passwordText);
                
                
	}
	
	public class EventLogin implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
                    String user = "";
                    
                    for (char c : passwordText.getPassword()){
                        user+= c;
                    }
                    done = Main.db.checkPass(userText.getText(),user );
		}
		
	}
}
