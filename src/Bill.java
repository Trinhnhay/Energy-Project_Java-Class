import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Bill extends JPanel {

	private ResultSet result;
	private JPanel panel = new JPanel();

	// constructor
	public Bill(ResultSet resultSet) {
		result = resultSet;
	}
	
	//add the sub - panels to the main panel
	public JPanel billPanel() {

		panel.setLayout(new BorderLayout(10, 10));
		panel.setPreferredSize(new Dimension(600, 400));
		panel.setBackground(Color.white);
		panel.add(infoPanel(), BorderLayout.NORTH);
		panel.add(sumaryPanel(), BorderLayout.CENTER);
		panel.add(lastMonthPanel(), BorderLayout.SOUTH);

		return panel;
	}

	public JPanel sumaryPanel() {
		JPanel sumaryPanel = new JPanel();
		sumaryPanel.setLayout(new BorderLayout());
		sumaryPanel.setBackground(Color.WHITE);

		JPanel subSumaryPanel1 = new JPanel();
		JLabel title = new JLabel("Summary");
		title.setHorizontalAlignment(JLabel.LEFT);
		subSumaryPanel1.add(title);
		subSumaryPanel1.setBackground(Color.WHITE);

		JPanel subSumaryPanel2 = new JPanel();
		subSumaryPanel2.setLayout(new GridLayout(6, 2));
		subSumaryPanel2.setBackground(Color.WHITE);
		subSumaryPanel2.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel("Total use this month:   ");
		JLabel label2 = new JLabel("Cost (0.16/Kwh):   ");
		JLabel label3 = new JLabel("City tax (6.5%):   ");
		JLabel label4 = new JLabel("Remaining:   ");
		JLabel label5 = new JLabel("Amount due:   ");
		JLabel label6 = new JLabel("Due date:   ");

		label1.setHorizontalAlignment(JLabel.RIGHT);
		label2.setHorizontalAlignment(JLabel.RIGHT);
		label3.setHorizontalAlignment(JLabel.RIGHT);
		label4.setHorizontalAlignment(JLabel.RIGHT);
		label5.setHorizontalAlignment(JLabel.RIGHT);
		label6.setHorizontalAlignment(JLabel.RIGHT);

		try {
			JLabel label7 = new JLabel(String.valueOf(result.getDouble("totalUsage")));
			JLabel label8 = new JLabel(String.valueOf(result.getDouble("cost")));
			JLabel label9 = new JLabel(String.valueOf(result.getDouble("tax")));
			JLabel label10 = new JLabel(String.valueOf(result.getDouble("remaining")));
			JLabel label11 = new JLabel("$ " + String.valueOf(result.getDouble("amountDue")));
			JLabel label12 = new JLabel(result.getString("dueDate"));

			subSumaryPanel2.add(label1);
			subSumaryPanel2.add(label7);
			subSumaryPanel2.add(label2);
			subSumaryPanel2.add(label8);
			subSumaryPanel2.add(label3);
			subSumaryPanel2.add(label9);
			subSumaryPanel2.add(label4);
			subSumaryPanel2.add(label10);
			subSumaryPanel2.add(label5);
			subSumaryPanel2.add(label11);
			subSumaryPanel2.add(label6);
			subSumaryPanel2.add(label12);
		
		} catch (Exception E) {
			E.printStackTrace();
		}
		sumaryPanel.add(subSumaryPanel1, BorderLayout.NORTH);
		sumaryPanel.add(subSumaryPanel2, BorderLayout.CENTER);
		sumaryPanel.setVisible(true);
		return sumaryPanel;
	}

	private JPanel infoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.white);
		infoPanel.setLayout(new GridLayout(4, 2));
		infoPanel.setPreferredSize(new Dimension(500, 80));
		try {
			JLabel companyName = new JLabel("Green Energy Company");
			JLabel accountNumber = new JLabel("Account number: " + String.valueOf(result.getInt("customerID")));
			accountNumber.setHorizontalAlignment(JLabel.RIGHT);
			JLabel companyWeb = new JLabel("retailenergyprovider.com");
			JLabel customerName = new JLabel(result.getString("customerName"));
			customerName.setHorizontalAlignment(JLabel.RIGHT);
			JLabel companyEmail = new JLabel("customercare@retailenergyprovider.com");
			JLabel customerAddress = new JLabel(result.getString("customerAddress"));
			customerAddress.setHorizontalAlignment(JLabel.RIGHT);
			JLabel companyPhone = new JLabel("1-800-129-456");
			JLabel customerPhone = new JLabel(result.getString("phoneNumber"));
			customerPhone.setHorizontalAlignment(JLabel.RIGHT);

			infoPanel.add(companyName);
			infoPanel.add(accountNumber);
			infoPanel.add(companyWeb);
			infoPanel.add(customerName);
			infoPanel.add(companyEmail);
			infoPanel.add(customerAddress);
			infoPanel.add(companyPhone);
			infoPanel.add(customerPhone);

		} catch (Exception E) {
			E.printStackTrace();
		}
		return infoPanel;
	}

	private JPanel lastMonthPanel() {
		JPanel lastMonthPanel = new JPanel();

		lastMonthPanel.setLayout(new BorderLayout());
		lastMonthPanel.setBackground(Color.WHITE);
		lastMonthPanel.setPreferredSize(new Dimension(480, 170));

		JPanel subLastMonthPanel1 = new JPanel();
		JLabel title = new JLabel("Last month");
		title.setHorizontalAlignment(JLabel.LEFT);
		subLastMonthPanel1.add(title);
		subLastMonthPanel1.setBackground(Color.WHITE);

		JPanel subLastMonthPanel2 = new JPanel();
		subLastMonthPanel2.setLayout(new GridLayout(5, 2));
		subLastMonthPanel2.setBackground(Color.WHITE);
		subLastMonthPanel2.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel("Usage:     ");
		JLabel label2 = new JLabel("Cost (tax included):    ");
		JLabel label3 = new JLabel("Payment:    ");
		JLabel label4 = new JLabel("Remaining:   ");
		JLabel label5 = new JLabel("Due date:   ");

		label1.setHorizontalAlignment(JLabel.RIGHT);
		label2.setHorizontalAlignment(JLabel.RIGHT);
		label3.setHorizontalAlignment(JLabel.RIGHT);
		label4.setHorizontalAlignment(JLabel.RIGHT);
		label5.setHorizontalAlignment(JLabel.RIGHT);

		try {
			JLabel label6 = new JLabel(String.valueOf(result.getDouble("lastUsage")));
			JLabel label7 = new JLabel(String.valueOf(result.getDouble("lastCost")));
			JLabel label8 = new JLabel(String.valueOf(result.getDouble("lastPayment")));
			JLabel label9 = new JLabel(String.valueOf(result.getDouble("lastRemaining")));
			JLabel label10 = new JLabel(result.getString("lastDueDate"));
			subLastMonthPanel2.add(label1);
			subLastMonthPanel2.add(label6);
			subLastMonthPanel2.add(label2);
			subLastMonthPanel2.add(label7);
			subLastMonthPanel2.add(label3);
			subLastMonthPanel2.add(label8);
			subLastMonthPanel2.add(label4);
			subLastMonthPanel2.add(label9);
			subLastMonthPanel2.add(label5);
			subLastMonthPanel2.add(label10);

			JPanel subLastMonthPanel3 = new JPanel();
			subLastMonthPanel3.setBackground(Color.WHITE);
			JButton emailButton = new JButton("Email");
			JButton downloadButton = new JButton("Download");
			emailButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						PDF pdf = new PDF(result);
						pdf.text(String.valueOf(result.getInt("customerID")));
						SentEmail email = new SentEmail();
						if (result.getString("emailAddress").equals(""))
							JOptionPane.showMessageDialog(null, "The email address has not provided");
						else {
							email.sentEmail(result.getString("emailAddress"), result.getInt("customerID") + ".pdf");
							JOptionPane.showMessageDialog(null, "Invoice is sent");
						}
					} catch (Exception E) {
					}
				}
			});
			downloadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						PDF pdf = new PDF(result);
						pdf.text(String.valueOf(result.getInt("customerID")));
						JOptionPane.showMessageDialog(null, "Invoice is downloaded");
					} catch (Exception E) {
					}
				}
			});

			subLastMonthPanel3.add(emailButton);
			subLastMonthPanel3.add(downloadButton);

			lastMonthPanel.add(subLastMonthPanel1, BorderLayout.NORTH);
			lastMonthPanel.add(subLastMonthPanel2, BorderLayout.CENTER);
			lastMonthPanel.add(subLastMonthPanel3, BorderLayout.SOUTH);

		} catch (Exception E) {
			E.printStackTrace();
		}
		return lastMonthPanel;
	}

}
