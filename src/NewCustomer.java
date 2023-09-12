import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class NewCustomer extends JPanel {

	private static final int WIDTH = 550;
	private static final int HEIGHT = 450;

	private Connection connection;

	private JButton confirm;
	private JButton back;

	private JTextField customerID = new JTextField();;
	private JTextField customerName = new JTextField();
	private JTextField customerAddress = new JTextField();
	private JTextField customerPhone = new JTextField();
	private JTextField customerEmail = new JTextField();
	private JPanel panel = new JPanel();

	public NewCustomer(Connection connect) {
		connection = connect;
	}

	private JPanel buttonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		confirm = new JButton("Confirm");
		confirm.setPreferredSize(new Dimension(100, 30));
		confirm.addActionListener(new confirmListener());

		back = new JButton("Back");
		back.setPreferredSize(new Dimension(100, 30));
		back.addActionListener(new backListener());

		buttonPanel.add(confirm);
		buttonPanel.add(back);
		return buttonPanel;
	}

	private JPanel infoPanel(String title, JTextField text) {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2, 1));
		JLabel email = new JLabel(title);

		infoPanel.add(email);
		infoPanel.add(text);
		infoPanel.setBackground(Color.WHITE);

		return infoPanel;
	}

	public JPanel newCusPanel() {
		JPanel newCusPanel = new JPanel();

		newCusPanel.setLayout(new GridLayout(7, 1));
		newCusPanel.setBackground(Color.WHITE);
		newCusPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JLabel label = new JLabel("Customer Information:");
		label.setFont(new Font("", Font.BOLD, 18));

		newCusPanel.add(label);
		newCusPanel.add(infoPanel("Customer number:", customerID));
		newCusPanel.add(infoPanel("Customer name:", customerName));
		newCusPanel.add(infoPanel("Customer phone number:", customerPhone));
		newCusPanel.add(infoPanel("Customer address:", customerAddress));
		newCusPanel.add(infoPanel("Customer email address:", customerEmail));
		newCusPanel.add(buttonPanel());

		panel.setBackground(Color.WHITE);
		panel.add(newCusPanel);

		return panel;
	}
	
	//When the confirm button is click the input information will be recorded and used for insert a new row in database
	public class confirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirm) {
				try {
					int acc = Integer.parseInt(customerID.getText());
					String name = customerName.getText();
					String address = customerAddress.getText();
					String phone = customerPhone.getText();
					String email = customerEmail.getText();

					Statement stmt = connection.createStatement();
					ResultSet result = stmt.executeQuery("SELECT customerID From customer where customerID =" + acc);

					if (result.next()) {
						if (result.getInt("customerID") == acc) {
							JOptionPane.showMessageDialog(null, "This account number is already exist!");
						}
					} else {
						stmt.executeUpdate(
								"INSERT INTO customer (customerID, customerName,customerAddress, phoneNumber, emailAddress)"
										+ "VALUE (" + acc + ",'" + name + "','" + address + "','" + phone + "','"
										+ email + "');");
						JOptionPane.showMessageDialog(null, "Successfully Added");
					}
				} catch (Exception E) {
					JOptionPane.showMessageDialog(null, "Please enter information");
					E.printStackTrace();
				}
			}
		}

	}

	public class backListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == back) {
				panel.removeAll();
				Search search = new Search(connection);
				panel.add(search.searchPanel(), BorderLayout.CENTER);
				panel.setVisible(true);
				panel.revalidate();
				panel.repaint();
			}

		}
	}
}
