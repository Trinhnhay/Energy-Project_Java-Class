import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Modify extends JPanel {

	private Connection connection;
	private JPanel mainPanel = new JPanel();
	
	private JTextField customerID;
	private JTextField customerName;
	private JTextField customerAddress;
	private JTextField customerPhone;
	private JTextField customerEmail;
	private JTextField customerTotalUsage;
	private JTextField customerDueDate;
	private JTextField customerLastUsage;
	private JTextField customerLastDueDate;
	private JTextField customerLastPayment;

	private JButton update;
	private JButton cancel;
	private ResultSet result;

	public Modify(Connection connect, ResultSet resultSet) {
		connection = connect;
		result = resultSet;
	}
	
	// add label and texlField to the panel. 
	//The textField will be blank if the information is not in the database
	public JPanel modifyPanel() {
		mainPanel.setVisible(false);
		mainPanel.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(11, 1));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		try {
			JPanel idPanel = new JPanel();
			JPanel namePanel = new JPanel();
			JPanel addressPanel = new JPanel();
			JPanel phonePanel = new JPanel();
			JPanel emailPanel = new JPanel();
			JPanel totalUsagePanel = new JPanel();
			JPanel dueDatePanel = new JPanel();
			JPanel lastUsagePanel = new JPanel();
			JPanel lastDueDatePanel = new JPanel();
			JPanel lastPaymentPanel = new JPanel();

			idPanel.setLayout(new GridLayout(1, 2));
			namePanel.setLayout(new GridLayout(1, 2));
			addressPanel.setLayout(new GridLayout(1, 2));
			phonePanel.setLayout(new GridLayout(1, 2));
			emailPanel.setLayout(new GridLayout(1, 2));
			totalUsagePanel.setLayout(new GridLayout(1, 2));
			dueDatePanel.setLayout(new GridLayout(1, 2));
			lastUsagePanel.setLayout(new GridLayout(1, 2));
			lastDueDatePanel.setLayout(new GridLayout(1, 2));
			lastPaymentPanel.setLayout(new GridLayout(1, 2));

			JLabel id = new JLabel("Account Number       ");
			JLabel name = new JLabel("Customer Name:   ");
			JLabel address = new JLabel("Customer Address:");
			JLabel phone = new JLabel("Customer Phone Number:");
			JLabel email = new JLabel("Customer Email addess:");
			JLabel totalUsage = new JLabel("Total Usage:");
			JLabel dueDate = new JLabel("Due Date:");
			JLabel lastUsage = new JLabel("Last Usage:");
			JLabel lastDueDate = new JLabel("Last due date:");
			JLabel lastPayment = new JLabel("Last Payment:");

			customerID = new JTextField(String.valueOf(result.getInt("customerID")));
			customerName = new JTextField(result.getString("customerName"));
			customerAddress = new JTextField(result.getString("customerAddress"));
			customerPhone = new JTextField(result.getString("phoneNumber"));
			customerEmail = new JTextField(result.getString("emailAddress"));
			customerTotalUsage = new JTextField(String.valueOf(result.getDouble("totalUsage")));
			customerDueDate = new JTextField(result.getString("dueDate"));
			customerLastUsage = new JTextField(String.valueOf(result.getDouble("lastUsage")));
			customerLastDueDate = new JTextField(result.getString("lastDueDate"));
			customerLastPayment = new JTextField(String.valueOf(result.getDouble("lastPayment")));

			customerID.setPreferredSize(new Dimension(100, 30));
			customerName.setPreferredSize(new Dimension(150, 30));
			customerAddress.setPreferredSize(new Dimension(250, 30));
			customerPhone.setPreferredSize(new Dimension(150, 30));
			customerEmail.setPreferredSize(new Dimension(250, 30));
			customerTotalUsage.setPreferredSize(new Dimension(100, 30));
			customerDueDate.setPreferredSize(new Dimension(100, 30));
			customerLastUsage.setPreferredSize(new Dimension(100, 30));
			customerLastDueDate.setPreferredSize(new Dimension(100, 30));
			customerLastPayment.setPreferredSize(new Dimension(100, 30));

			idPanel.add(id);
			namePanel.add(name);
			addressPanel.add(address);
			phonePanel.add(phone);
			emailPanel.add(email);
			totalUsagePanel.add(totalUsage);
			dueDatePanel.add(dueDate);
			lastUsagePanel.add(lastUsage);
			lastDueDatePanel.add(lastDueDate);
			lastPaymentPanel.add(lastPayment);

			idPanel.add(customerID);
			namePanel.add(customerName);
			addressPanel.add(customerAddress);
			phonePanel.add(customerPhone);
			emailPanel.add(customerEmail);
			totalUsagePanel.add(customerTotalUsage);
			dueDatePanel.add(customerDueDate);
			lastUsagePanel.add(customerLastUsage);
			lastDueDatePanel.add(customerLastDueDate);
			lastPaymentPanel.add(customerLastPayment);

			idPanel.setBackground(Color.WHITE);
			namePanel.setBackground(Color.WHITE);
			addressPanel.setBackground(Color.WHITE);
			phonePanel.setBackground(Color.WHITE);
			emailPanel.setBackground(Color.WHITE);
			totalUsagePanel.setBackground(Color.WHITE);
			dueDatePanel.setBackground(Color.WHITE);
			lastUsagePanel.setBackground(Color.WHITE);
			lastDueDatePanel.setBackground(Color.WHITE);

			lastPaymentPanel.setBackground(Color.WHITE);

			panel.add(idPanel);
			panel.add(namePanel);
			panel.add(addressPanel);
			panel.add(phonePanel);
			panel.add(emailPanel);
			panel.add(totalUsagePanel);
			panel.add(dueDatePanel);
			panel.add(lastUsagePanel);
			panel.add(lastDueDatePanel);
			panel.add(lastPaymentPanel);
		} catch (Exception E) {
			E.printStackTrace();
		}

		JPanel confirmPanel = new JPanel();
		update = new JButton("Update");
		update.addActionListener(new confirmListener());
		update.setPreferredSize(new Dimension(100, 30));
		confirmPanel.add(update);
		confirmPanel.setBackground(Color.WHITE);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new confirmListener());
		cancel.setPreferredSize(new Dimension(100, 30));
		confirmPanel.add(cancel);

		panel.add(confirmPanel);
		panel.setVisible(true);
		
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.setVisible(true);
		return mainPanel;
	}
	
	// When use click update, it will take the input and write SQL statement to update the database
	public class confirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == update) {
				try {
					int acc = Integer.parseInt(customerID.getText());
					String name = customerName.getText();
					String address = customerAddress.getText();
					String phone = customerPhone.getText();
					String email = customerEmail.getText();
					double usage = Double.parseDouble(customerTotalUsage.getText());
					double cost = usage * 0.16;
					double cityTax = cost * 6.5 / 100;
					String dueDate = customerDueDate.getText();
					double lastused = Double.parseDouble(customerLastUsage.getText());
					double lastCost = (lastused * 0.16) + ((lastused * 0.16) * 6.5 / 100);
					String lastDue = customerLastDueDate.getText();
					double lastPay = Double.parseDouble(customerLastPayment.getText());
					double lastRemain = lastCost - lastPay;
					double amountDue = cost + lastRemain;
					Statement stmt = connection.createStatement();
					stmt.executeUpdate("UPDATE customer SET customerName = '" + name + "', customerAddress = '"
							+ address + "', phoneNumber = '" + phone + "', emailAddress= '" + email + "', totalUsage = "
							+ usage + ", cost = " + cost + ", dueDate = '" + dueDate + "', lastUsage = " + lastused
							+ ", lastCost = " + lastCost + ", lastPayment = " + lastPay + ", lastRemaining = "
							+ lastRemain + ", amountDue = " + amountDue + ", tax = " + cityTax + ", lastDueDate = '"
							+ lastDue + "' WHERE customerID = " + acc);

					JOptionPane.showMessageDialog(null, "Successfully Updated");
				} catch (Exception E) {
					JOptionPane.showMessageDialog(null, "Please enter valid information");
					E.printStackTrace();
				}
			}
			if (e.getSource() == cancel) {
				mainPanel.removeAll();
				mainPanel.setVisible(false);
				Search search = new Search(connection);
				mainPanel.add(search.searchPanel());
				mainPanel.setVisible(true);
			}
		}

	}

}
