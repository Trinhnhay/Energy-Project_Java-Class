
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Help extends JPanel {
	
	private Connection connection;
	private JPanel panel = new JPanel();

	public Help(Connection connect) {
		connection = connect;
	}

	public JPanel helpPanel() {
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);

		panel.add(titilePanel(), BorderLayout.NORTH);
		panel.add(instructionPanel(), BorderLayout.CENTER);
		panel.add(backPanel(), BorderLayout.SOUTH);

		return panel;
	}
	
	//add the title panel
	private JPanel titilePanel() {
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Instruction");
		titlePanel.setBackground(Color.WHITE);

		title.setBackground(Color.WHITE);
		title.setFont(new Font("", Font.BOLD, 18));
		titlePanel.add(title);

		return titlePanel;
	}

	//add the content of this panel
	private JPanel instructionPanel() {
		JPanel instructionPanel = new JPanel();
		instructionPanel.setBackground(Color.WHITE);
		instructionPanel.setLayout(new GridLayout(31, 1));

		JLabel addNewInfo1 = new JLabel("Add a new customer account:");
		JLabel addNewInfo2 = new JLabel("    - Click to \"New customer\" button. ");
		JLabel addNewInfo3 = new JLabel(
				"    - Fill out all customer information (some inforation can be missed, and add it later) ");
		JLabel addNewInfo4 = new JLabel(
				"    - Click \"Confirm\" button to add it, click \"Back\" to go back main frame and information filled out won't be saved. ");

		JLabel searchInfo = new JLabel("Search an exist customer: ");
		JLabel searchInfo1 = new JLabel("    - Click to \"Search\" button. ");
		JLabel searchInfo2 = new JLabel(
				"    - Type either account number or name that related to customer you looking for");
		JLabel searchInfo4 = new JLabel("    - Click \"Search\" button ");
		JLabel searchInfo3 = new JLabel(
				"    - Name does not need to be exactly, but account need to be an exact number  ");

		JLabel deleteInfo1 = new JLabel("Delete a customer account:");
		JLabel deleteInfo2 = new JLabel("    - Search for a customer account need to delete");
		JLabel deleteInfo3 = new JLabel(
				"    - Have to click on the radio button that coordinated to the delete account ");
		JLabel deleteInfo4 = new JLabel(
				"    - Click \"Delete\" button below, and it will appear an message if it is deleted ");

		JLabel modifyInfo1 = new JLabel("Modify customer account information:");
		JLabel modifyInfo2 = new JLabel("    - Search for a customer account need to delete");
		JLabel modifyInfo3 = new JLabel(
				"    - Have to click on the radio button that coordinated to the delete account ");
		JLabel modifyInfo4 = new JLabel(
				"    - Click \"Modify\" button below, and fill out information need to be changed, then click \"Update\"");
		JLabel modifyInfo5 = new JLabel(
				"    - Or click \"Dash board\" button, then \"Edit Account\", fill out information need to be changed, then click \"Update\"");

		JLabel dashboardInfo1 = new JLabel("See dash board of a customer account:");
		JLabel dashboardInfo2 = new JLabel("    - Search for a customer account need to delete");
		JLabel dashboardInfo3 = new JLabel(
				"    - Have to click on the radio button that coordinated to the delete account ");
		JLabel dashboardInfo4 = new JLabel("    - Click \"Dash board\" button below");

		JLabel billInfo1 = new JLabel("View Satement (Bill):");
		JLabel billInfo2 = new JLabel("    - Search for a customer account");
		JLabel billInfo3 = new JLabel(
				"    - Have to click on the radio button that coordinated to the delete account ");
		JLabel billInfo4 = new JLabel("    - Click \"Bill\" button below, or \"Dash board\", then \"View Bill\" ");

		JLabel makePayInfo1 = new JLabel("Make payment:");
		JLabel makePayInfo2 = new JLabel("    - Search for a customer account");
		JLabel makePayInfo3 = new JLabel(
				"    - Have to click on the radio button that coordinated to the delete account ");
		JLabel makePayInfo4 = new JLabel("    - Click \"Dash board\", then \"Make Payment\" ");

		instructionPanel.add(addNewInfo1);
		instructionPanel.add(addNewInfo2);
		instructionPanel.add(addNewInfo3);
		instructionPanel.add(addNewInfo4);

		instructionPanel.add(searchInfo);
		instructionPanel.add(searchInfo1);
		instructionPanel.add(searchInfo2);
		instructionPanel.add(searchInfo3);
		instructionPanel.add(searchInfo4);

		instructionPanel.add(deleteInfo1);
		instructionPanel.add(deleteInfo2);
		instructionPanel.add(deleteInfo3);
		instructionPanel.add(deleteInfo4);

		instructionPanel.add(modifyInfo1);
		instructionPanel.add(modifyInfo2);
		instructionPanel.add(modifyInfo3);
		instructionPanel.add(modifyInfo4);
		instructionPanel.add(modifyInfo5);

		instructionPanel.add(dashboardInfo1);
		instructionPanel.add(dashboardInfo2);
		instructionPanel.add(dashboardInfo3);
		instructionPanel.add(dashboardInfo4);

		instructionPanel.add(billInfo1);
		instructionPanel.add(billInfo2);
		instructionPanel.add(billInfo3);
		instructionPanel.add(billInfo4);

		instructionPanel.add(makePayInfo1);
		instructionPanel.add(makePayInfo2);
		instructionPanel.add(makePayInfo3);
		instructionPanel.add(makePayInfo4);

		return instructionPanel;
	}

	//add back button to go back the main frame
	private JPanel backPanel() {
		JPanel backPanel = new JPanel();
		JButton backButton = new JButton("Back");

		backPanel.setBackground(Color.WHITE);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				Search search = new Search(connection);
				panel.add(search.searchPanel(), BorderLayout.CENTER);
				panel.setVisible(true);
				panel.revalidate();
				panel.repaint();

			}
		});
		backPanel.add(backButton);
		return backPanel;
	}

}
