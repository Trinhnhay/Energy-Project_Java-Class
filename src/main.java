import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class main extends JFrame {

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private static final String LOGO = "energy.jpg"; // banner of the company

	private JPanel center = new JPanel();
	private JPanel newCustomerPanel = new JPanel();
	private JPanel searchPanel = new JPanel();
	private JPanel helpPanel = new JPanel();

	private Connection connection;

	//Constructor
	public main(Connection connect) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Energy Kansas City");
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBackground(Color.WHITE);

		connection = connect;
		displayMainFrame();
		pack();

	}
	
	//The frame is divided into 3 parts, this method is adding panel to each part
	public void displayMainFrame() {
		JPanel left = new JPanel();
		JPanel top = new JPanel();

		left.setPreferredSize(new Dimension(FRAME_WIDTH / 6, FRAME_HEIGHT));
		left.setBackground(Color.WHITE);
		left.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		left.setLayout(new BorderLayout());
		left.add(addButton(), BorderLayout.CENTER);
		this.add(left, BorderLayout.WEST);

		top.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT / 5));
		top.setBackground(Color.white);
		top.add(new JLabel(new ImageIcon(getClass().getResource(LOGO))), BorderLayout.CENTER);
		this.add(top, BorderLayout.NORTH);

		center.setBackground(Color.white);
		center.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		center.setPreferredSize(new Dimension(FRAME_WIDTH - (FRAME_WIDTH / 4), FRAME_HEIGHT - (FRAME_HEIGHT / 7)));
		center.setVisible(true);
		this.add(center, BorderLayout.CENTER);

	}
	
	//The WEST part include button: SEARCH, NEW COUSTOMER, HELP. 
	//This method is add those buttons in the panel and its actionlistenr
	private JPanel addButton() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		panel.setLayout(new GridLayout(13, 1));

		JPanel newCusCenter = new JPanel();
		JButton newCustomer = new JButton("New Customer");
		JPanel subPanel1 = new JPanel();
		newCusCenter.setBackground(Color.WHITE);
		newCustomer.setPreferredSize(new Dimension(120, 40));
		subPanel1.setBackground(Color.WHITE);
		subPanel1.add(newCustomer);
		newCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				newCustomerPanel.setVisible(false);
				searchPanel.setVisible(false);
				helpPanel.setVisible(false);

				helpPanel.removeAll();
				newCustomerPanel.removeAll();
				searchPanel.removeAll();

				NewCustomer newCus = new NewCustomer(connection);
				newCustomerPanel.add(newCus.newCusPanel());
				newCustomerPanel.setVisible(true);
				center.add(newCustomerPanel);
			}
		});

		JButton search = new JButton("search");
		JPanel subPanel2 = new JPanel();
		searchPanel.setBackground(Color.WHITE);
		search.setPreferredSize(new Dimension(120, 40));
		subPanel2.setBackground(Color.WHITE);
		subPanel2.add(search);
		search.doClick();
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newCustomerPanel.setVisible(false);
				searchPanel.setVisible(false);
				helpPanel.setVisible(false);

				searchPanel.removeAll();
				helpPanel.removeAll();
				newCustomerPanel.removeAll();

				Search newSearch = new Search(connection);
				searchPanel.add(newSearch.searchPanel());
				center.add(searchPanel);
				searchPanel.setVisible(true);
			}
		});

		JButton help = new JButton("Help");
		JPanel subPanel3 = new JPanel();
		helpPanel.setBackground(Color.WHITE);
		help.setPreferredSize(new Dimension(120, 40));
		subPanel3.setBackground(Color.WHITE);
		subPanel3.add(help);
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				newCustomerPanel.setVisible(false);
				searchPanel.setVisible(false);
				helpPanel.setVisible(false);

				searchPanel.removeAll();
				helpPanel.removeAll();
				newCustomerPanel.removeAll();

				Help help = new Help(connection);
				helpPanel.add(help.helpPanel());
				center.add(helpPanel);
				helpPanel.setVisible(true);

			}
		});

		panel.add(subPanel1);
		panel.add(subPanel2);
		panel.add(subPanel3);
		search.doClick();

		return panel;

	}

}
