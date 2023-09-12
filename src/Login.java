import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;


public class Login extends JFrame {

	private static final int FRAME_WIDTH = 270;
	private static final int FRAME_HEIGHT = 230;
	
	private final String URL ="jdbc:mysql://localhost:3306/energy";
	

	private JButton login = new JButton("Login");
	private JTextField userName = new JTextField();
	private JTextField passWord = new JTextField();
	private Connection connection;
	
	// Constructor
	public Login(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Login");
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		loginPanel();
	}
	
	// add the username and password word panel to the frame
	public void loginPanel() {
		
		JPanel loginPanel = new JPanel();
		
		JPanel subLoginPanel = new JPanel();
		JPanel subLoginPanel1 = new JPanel();
		JPanel subLoginPanel2 = new JPanel();
		JPanel subLoginPanel3 = new JPanel();
		
		JLabel title = new JLabel ("GREEN ENERGY");
		title.setPreferredSize(new Dimension(100, 30));
		JLabel name = new JLabel ("user name: ");
		JLabel pass = new JLabel ("password:  ");
		
		userName.setPreferredSize(new Dimension (150,30));
		passWord.setPreferredSize(new Dimension (150,30));
		
		login.addActionListener(new loginListener());
		
		subLoginPanel.add(title);
		
		subLoginPanel1.add(name);
		subLoginPanel1.add(userName);
		
		subLoginPanel2.add(pass);
		subLoginPanel2.add(passWord);
		
		subLoginPanel3.add(login); 
		
		loginPanel.add(subLoginPanel);
		loginPanel.add(subLoginPanel1);
		loginPanel.add(subLoginPanel2);
		loginPanel.add(subLoginPanel3);
		
		this.add(loginPanel);	
	}

	public class loginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==login) {
				if( userName.getText().equals("greenEnergy") && passWord.getText().equals("employee0028")) {
					try {
						 // connect to local database system
						connection = DriverManager.getConnection(URL,"root","trinhNhay28011995");
						new main(connection);
					}catch(Exception E) {
						JOptionPane.showMessageDialog(null, "System can't connect to the database");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "User name or Password is incorrect");
				}
			}
		}
	}
	
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				new Login();
    			}
    		});
        }
}
