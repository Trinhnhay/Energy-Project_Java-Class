import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Search extends JPanel {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 450;

	private Connection connection;
	private Statement stmt;
	private ResultSet resultSet;

	private JTextField cusAccount;
	private JTextField customerName;
	private JButton search;

	private JPanel panel = new JPanel();
	private JPanel dataPanel = new JPanel();
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton[] choose = new JRadioButton[100];
	private int totalCount = 1;
	private int index;
	
	//constructor
	public Search(Connection connect) {
		connection = connect;

	}

	public JPanel searchPanel() {

		panel.setLayout(new GridLayout(10, 1));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JPanel accountPanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel confirmPanel = new JPanel();

		JLabel label = new JLabel("Searching for...");
		JLabel account = new JLabel("Account Number:         ");
		JLabel name = new JLabel("Customer Name:   ");
		search = new JButton("Search");

		label.setFont(new Font("", Font.BOLD, 20));

		cusAccount = new JTextField();
		customerName = new JTextField();

		cusAccount.setPreferredSize(new Dimension(100, 30));
		customerName.setPreferredSize(new Dimension(150, 30));
		confirmPanel.setPreferredSize(new Dimension(100, 30));

		accountPanel.add(account);
		namePanel.add(name);
		confirmPanel.add(search);

		accountPanel.add(cusAccount);
		namePanel.add(customerName);

		accountPanel.setBackground(Color.WHITE);
		namePanel.setBackground(Color.WHITE);
		confirmPanel.setBackground(Color.WHITE);

		panel.add(label);
		panel.add(accountPanel);
		panel.add(namePanel);
		panel.add(confirmPanel);

		search.addActionListener(new searchListener());

		panel.setVisible(true);
		return panel;
	}

	//use information that user input and write sql statement to retrieve finding customer
	private class searchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == search) {
				try {

					stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String query;
					if (customerName.getText().equals("")) {
						int account = Integer.parseInt(cusAccount.getText());
						query = "SELECT * From customer where customerID =" + account;
					} else if (cusAccount.getText().equals("")) {
						String name = customerName.getText();
						query = "SELECT * From customer where customerName like '%" + name + "%'";

					} else {
						int account = Integer.parseInt(cusAccount.getText());
						String name = customerName.getText();
						query = "SELECT * From customer where customerID =" + account + "OR customerName like '%" + name
								+ "%'";
					}
					resultSet = stmt.executeQuery(query);
					panel.removeAll();
					searchPanel();
					titleTable();
					while (resultSet.next()) {
						cusData(resultSet, totalCount);
						totalCount++;
					}
					buttonPanel();
				} catch (Exception E) {
					JOptionPane.showMessageDialog(null, "There is no such that information");
				}
			}
		}
	}

	//display the customers have found
	private void titleTable() {
		panel.setVisible(false);
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		JLabel title = new JLabel(
				"   Acount ID                Name                                          Addess                                                Phone number");
		title.setPreferredSize(new Dimension(580, 30));
		titlePanel.add(title);
		panel.add(titlePanel);
		panel.setVisible(true);
	}

	private void cusData(ResultSet result, final int count) {
		
		dataPanel = new JPanel();
		
		JPanel dataAccPanel = new JPanel();
		JPanel dataNamePanel = new JPanel();
		JPanel dataAddressPanel = new JPanel();
		JPanel dataPhonePanel = new JPanel();

		dataPanel.setBackground(Color.WHITE);
		dataPanel.setLayout(new FlowLayout());
		// using array to store JRadioButton that related to the customer has be found
		// so that every the user click on the RadioButton, the system will know which customer will be chose
		choose[count] = new JRadioButton();
		choose[count].setBackground(Color.WHITE);
		// this action make the resultSet point to the customer has been chosen
		choose[count].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < totalCount - count; i++) {
					index = count;
					try {
						resultSet.previous();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		group.add(choose[count]);
		try {
			JLabel dataAcc = new JLabel(String.valueOf(result.getInt("customerID")));
			JLabel dataName = new JLabel(result.getString("customerName"));
			JLabel dataAddress = new JLabel(result.getString("customerAddress"));
			JLabel dataPhone = new JLabel(result.getString("phoneNumber"));

			dataAccPanel.add(dataAcc);
			dataNamePanel.add(dataName);
			dataAddressPanel.add(dataAddress);
			dataPhonePanel.add(dataPhone);

			dataAccPanel.setPreferredSize(new Dimension(50, 30));
			dataNamePanel.setPreferredSize(new Dimension(110, 30));
			dataAddressPanel.setPreferredSize(new Dimension(270, 30));
			dataPhonePanel.setPreferredSize(new Dimension(100, 30));

			dataAccPanel.setBackground(Color.white);
			dataNamePanel.setBackground(Color.white);
			dataAddressPanel.setBackground(Color.white);
			dataPhonePanel.setBackground(Color.white);
		} catch (Exception E) {
		}
		dataPanel.setPreferredSize(new Dimension(500, 30));
		dataPanel.add(choose[count], BorderLayout.WEST);
		dataPanel.setBackground(Color.white);

		dataPanel.setLayout(new FlowLayout());
		dataPanel.add(dataAccPanel);
		dataPanel.add(dataNamePanel);
		dataPanel.add(dataAddressPanel);
		dataPanel.add(dataPhonePanel);
		dataPanel.setVisible(true);

		panel.add(dataPanel);
		panel.setVisible(true);

	}

	private void buttonPanel() {
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new FlowLayout());
		subPanel.setBackground(Color.WHITE);
		JButton modifyButton = new JButton("Modify");

		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					panel.setLayout(new BorderLayout());
					panel.setVisible(false);
					panel.removeAll();

					Modify modify = new Modify(connection, resultSet);

					panel.add(modify.modifyPanel(), BorderLayout.CENTER);
					panel.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Please choose one customer");
			}
		});

		JButton dashBoardButton = new JButton("Dash board");
		dashBoardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					panel.setLayout(new BorderLayout());
					panel.setVisible(false);
					panel.removeAll();
					DashBoard dashBoard = new DashBoard(connection, resultSet);
					panel.add(dashBoard.dashBoardPanel(), BorderLayout.CENTER);
					panel.setVisible(true);
				}else
					JOptionPane.showMessageDialog(null, "Please choose one customer");
			}
		});

		JButton statement = new JButton("Bill");
		statement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					panel.setLayout(new BorderLayout());
					panel.setVisible(false);
					panel.removeAll();
					Bill bill = new Bill(resultSet);
					panel.add(bill.billPanel(), BorderLayout.CENTER);
					panel.setVisible(true);
				}else
					JOptionPane.showMessageDialog(null, "Please choose one customer");
			}
		});
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					try {
						stmt.executeUpdate("DELETE from customer WHERE customerID =" + resultSet.getInt("customerID"));
						JOptionPane.showMessageDialog(null, "Deleted");
						dataPanel.setVisible(false);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		subPanel.add(modifyButton);
		subPanel.add(dashBoardButton);
		subPanel.add(statement);
		subPanel.add(delete);
		subPanel.setVisible(true);
		panel.add(subPanel);

	}

}
