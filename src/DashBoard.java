
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DashBoard extends JPanel {
	
	private Connection connection;
	private ResultSet result;
	private JPanel panel = new JPanel();

	// constructor
	public DashBoard(Connection connect, ResultSet resultSet) {
		connection = connect;
		result = resultSet;

	}
	
	// add the sub-panels to the main panels
	public JPanel dashBoardPanel() {
		panel.setLayout(new BorderLayout());

		panel.setBackground(Color.white);
		panel.add(topPanel(), BorderLayout.NORTH);
		panel.add(midPanel(), BorderLayout.CENTER);
		panel.add(bottomPanel(), BorderLayout.SOUTH);

		return panel;
	}

	private JPanel topPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.setLayout(new GridLayout(2, 1));

		JPanel subTopPanel1 = new JPanel();
		subTopPanel1.setBackground(Color.WHITE);
		subTopPanel1.setLayout(new GridLayout(2, 1));
		try {
			JLabel name = new JLabel(result.getString("customerName"));
			JLabel accNum = new JLabel("Account #: " + String.valueOf(result.getInt("customerID")));
			subTopPanel1.add(accNum);
			subTopPanel1.add(name);
		} catch (Exception E) {
			E.printStackTrace();
		}

		JPanel subTopPanel2 = new JPanel();
		subTopPanel2.setBackground(Color.WHITE);
		subTopPanel2.setLayout(new GridLayout(1, 3));
		subTopPanel2.add(amountDue());
		subTopPanel2.add(dueDate());
		subTopPanel2.add(remaining());

		topPanel.add(subTopPanel1);
		topPanel.add(subTopPanel2);
		return topPanel;
	}

	private JPanel amountDue() {
		JPanel amountDuePanel = new JPanel();
		amountDuePanel.setBackground(Color.WHITE);
		amountDuePanel.setLayout(new GridLayout(2, 1));
		amountDuePanel.setPreferredSize(new Dimension(50, 50));
		amountDuePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel amountDue = new JLabel("Amount Due");
		amountDuePanel.add(amountDue);
		amountDue.setPreferredSize(new Dimension(50, 30));
		amountDue.setHorizontalAlignment(JLabel.CENTER);

		try {
			JLabel amount = new JLabel(String.valueOf(result.getDouble("amountDue")));
			amount.setPreferredSize(new Dimension(50, 30));
			amount.setHorizontalAlignment(JLabel.CENTER);
			amountDuePanel.add(amount);

		} catch (Exception E) {
			E.printStackTrace();
		}

		return amountDuePanel;
	}

	private JPanel dueDate() {
		JPanel dueDatePanel = new JPanel();
		dueDatePanel.setBackground(Color.WHITE);
		dueDatePanel.setLayout(new GridLayout(2, 1));
		dueDatePanel.setPreferredSize(new Dimension(50, 50));
		dueDatePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel dueDate = new JLabel("Due Date");
		dueDate.setHorizontalAlignment(JLabel.CENTER);
		dueDatePanel.add(dueDate);
		try {
			JLabel duedate = new JLabel(result.getString("dueDate"));
			duedate.setHorizontalAlignment(JLabel.CENTER);
			dueDatePanel.add(duedate);
		} catch (Exception E) {
			E.printStackTrace();
		}
		return dueDatePanel;
	}

	private JPanel remaining() {
		JPanel remainingPanel = new JPanel();
		remainingPanel.setBackground(Color.WHITE);
		remainingPanel.setLayout(new GridLayout(2, 1));
		remainingPanel.setPreferredSize(new Dimension(50, 50));
		remainingPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel remaining = new JLabel("Remaining");
		remaining.setHorizontalAlignment(JLabel.CENTER);
		remainingPanel.add(remaining);
		try {
			JLabel remainingLabel = new JLabel(String.valueOf(result.getDouble("remaining")));
			remainingLabel.setHorizontalAlignment(JLabel.CENTER);
			remainingPanel.add(remainingLabel);
		} catch (Exception E) {
			E.printStackTrace();
		}
		return remainingPanel;
	}

	private JPanel midPanel() {
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new GridLayout(2, 1));

		midPanel.add(reportPanel());
		Bill sumary = new Bill(result);
		midPanel.add(sumary.sumaryPanel());
		return midPanel;
	}

	private JPanel reportPanel() {
		JPanel reportPanel = new JPanel();
		reportPanel.setBorder(BorderFactory.createTitledBorder("Report: "));
		reportPanel.setLayout(new GridLayout(3, 1));
		reportPanel.setBackground(Color.WHITE);
		reportPanel.setPreferredSize(new Dimension(550, 150));

		try {
			if (result.getDouble("totalUsage") > result.getDouble("lastUsage")) {
				JLabel report1 = new JLabel("Amount usage this month is increasing");
				reportPanel.add(report1);
			} else if (result.getDouble("totalUsage") < result.getDouble("lastUsage")) {
				JLabel report1 = new JLabel("Amount usage this month is decreasing");
				reportPanel.add(report1);
			} else if (result.getDouble("totalUsage") == result.getDouble("lastUsage")) {
				JLabel report1 = new JLabel("Amount usage is static");
				reportPanel.add(report1);
			} else {
				JLabel report1 = new JLabel("Amount usage this month is " + result.getDouble("totalUsage"));
				reportPanel.add(report1);
			}

			double average = (result.getDouble("totalUsage") + result.getDouble("lastUsage")) / 2;
			JLabel report2 = new JLabel("Average usage: " + average);
			reportPanel.add(report2);

			JLabel report3 = new JLabel("Meter reading cycle in 30 days");
			reportPanel.add(report3);
		} catch (Exception E) {

		}

		return reportPanel;
	}

	private JPanel bottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(new FlowLayout());

		JButton makePaymentButton = new JButton("Make Payment");
		makePaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.setPreferredSize(new Dimension(400, 300));
				MakePayment makePay = new MakePayment(connection, result);
				panel.add(makePay.makePaymentPanel(), BorderLayout.CENTER);
				panel.setVisible(true);
				panel.revalidate();
				panel.repaint();
			}
		});

		JButton viewBillButton = new JButton("View Bill");
		viewBillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				Bill bill = new Bill(result);
				panel.add(bill.billPanel(), BorderLayout.CENTER);
				panel.setVisible(true);
				panel.revalidate();
				panel.repaint();

			}
		});

		JButton editAccountButton = new JButton("Edit Account");
		editAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				Modify edit = new Modify(connection, result);
				panel.add(edit.modifyPanel(), BorderLayout.CENTER);
				panel.setVisible(true);
				panel.revalidate();
				panel.repaint();
			}
		});

		bottomPanel.add(makePaymentButton);
		bottomPanel.add(viewBillButton);
		bottomPanel.add(editAccountButton);

		return bottomPanel;
	}

}
