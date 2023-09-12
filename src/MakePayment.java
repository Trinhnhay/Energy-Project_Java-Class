import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MakePayment extends JPanel {

	private Connection connection;
	private ResultSet result;

	private JPanel panel = new JPanel();
	private ButtonGroup group = new ButtonGroup();
	private JTextField amountText;

	private JRadioButton cash;
	private JRadioButton card;
	private JRadioButton other;
	
	private double amount;
	private boolean customizeAmount = true;

	// Constructor
	public MakePayment(Connection connect, ResultSet resultSet) {
		connection = connect;
		result = resultSet;
	}

	public JPanel makePaymentPanel() {
		JPanel makePaymentPanel = new JPanel();

		makePaymentPanel.setPreferredSize(new Dimension(350, 350));
		makePaymentPanel.setBackground(Color.WHITE);
		makePaymentPanel.setLayout(new GridLayout(5, 1));
		makePaymentPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel title = new JLabel("Make payment");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("", Font.BOLD, 20));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		JButton acceptButton = new JButton("Accept");
		acceptButton.setPreferredSize(new Dimension(100, 40));
		//Every time the button is click, it will calculate the remaining and update to the database
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Statement stmt;
				try {
					if (customizeAmount == true)
						amount = Double.parseDouble(amountText.getText());

					double remaining = result.getDouble("amountDue") - amount;

					stmt = connection.createStatement();
					stmt.executeUpdate("UPDATE customer SET payment = " + amount + ", remaining =" + remaining
							+ " WHERE customerID = " + result.getInt("customerID"));
					JOptionPane.showMessageDialog(null, "Approved");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		buttonPanel.add(acceptButton);
		makePaymentPanel.add(title);
		makePaymentPanel.add(fullAmountPanel());
		makePaymentPanel.add(amountPanel());
		makePaymentPanel.add(paymentTypePanel());
		makePaymentPanel.add(buttonPanel);

		panel.add(makePaymentPanel);
		panel.setBackground(Color.WHITE);
		return panel;
	}

	private JPanel amountPanel() {
		JPanel amountPanel = new JPanel();
		amountPanel.setBackground(Color.WHITE);

		amountText = new JTextField();
		amountText.setPreferredSize(new Dimension(100, 30));
		amountText.setEditable(false);
		JRadioButton amountRadioButton = new JRadioButton("Amount");
		amountRadioButton.setBackground(Color.WHITE);
		amountRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amountText.setEditable(true);

			}
		});

		amountPanel.add(amountRadioButton);
		amountPanel.add(amountText);
		group.add(amountRadioButton);
		return amountPanel;
	}

	private JPanel fullAmountPanel() {
		JPanel fullAmountPanel = new JPanel();
		fullAmountPanel.setBackground(Color.WHITE);

		try {
			JRadioButton fullAmount = new JRadioButton("Full amount ($" + result.getDouble("amountDue") + ")");
			fullAmount.setBackground(Color.WHITE);
			fullAmount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						amount = result.getDouble("amountDue");
						customizeAmount = false;
					} catch (Exception E) {
					}
				}
			});
			group.add(fullAmount);
			fullAmountPanel.add(fullAmount);
		} catch (Exception E) {
		}
		return fullAmountPanel;
	}

	private JPanel paymentTypePanel() {
		JPanel paymentTypePanel = new JPanel();

		paymentTypePanel.setBackground(Color.WHITE);
		cash = new JRadioButton("Cash   ");
		cash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (e.getSource() == cash) {
						Statement stmt = connection.createStatement();
						stmt.executeUpdate("UPDATE customer SET paymentType = 'cash' WHERE customerID = "
								+ result.getInt("customerID"));
					}
				} catch (Exception E) {
				}
			}
		});
		cash.setBackground(Color.WHITE);
		cash.doClick();

		card = new JRadioButton("Card   ");
		card.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (e.getSource() == card) {
						Statement stmt = connection.createStatement();
						stmt.executeUpdate("UPDATE customer SET paymentType = 'card' WHERE customerID = "
								+ result.getInt("customerID"));
					}
				} catch (Exception E) {

				}
			}
		});
		card.setBackground(Color.WHITE);

		other = new JRadioButton("Other  ");
		other.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (e.getSource() == other) {
						Statement stmt = connection.createStatement();
						stmt.executeUpdate("UPDATE customer SET paymentType = 'other' WHERE customerID = "
								+ result.getInt("customerID"));
					}
				} catch (Exception E) {

				}
			}
		});
		other.setBackground(Color.WHITE);

		ButtonGroup group = new ButtonGroup();
		group.add(cash);
		group.add(card);
		group.add(other);

		paymentTypePanel.add(cash);
		paymentTypePanel.add(card);
		paymentTypePanel.add(other);

		return paymentTypePanel;
	}

}
