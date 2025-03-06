
package Intership_Project_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class admin_Dashboard extends JFrame {

	static long number1;
	JButton billGeneratorButton, accountDetailsButton, serviceRequestButton, profileButton;
	String planType, planData, accountNo;
	double planPrice;

	JPanel center;
	JPanel dafault, mainPanel;

	public admin_Dashboard(long number) {
		number1 = number;

		setUpFrame();
		mainPanel = new JPanel(new BorderLayout());
		center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBackground(Color.WHITE);
		dafault = displayUserData();
		center.add(dafault);

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		sidePanel.setBackground(new Color(50, 50, 50));
		sidePanel.setPreferredSize(new java.awt.Dimension(250, getHeight()));

		addButton(sidePanel);

		mainPanel.add(center, BorderLayout.CENTER);
		mainPanel.add(sidePanel, BorderLayout.WEST);

		add(mainPanel, BorderLayout.CENTER);
		getContentPane().setBackground(new Color(200, 240, 240));
		setVisible(true);
	}

	public void setUpFrame() {
		setTitle("Admin Dashboard");
		setSize(1366, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void addButton(JPanel sidePanel) {

		billGeneratorButton = createButton("Bill Generator");

		accountDetailsButton = createButton("Account Details");
		serviceRequestButton = createButton("Service Request");
		profileButton = createButton("Profile");

		sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		sidePanel.add(billGeneratorButton);
		sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		sidePanel.add(accountDetailsButton);
		sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		sidePanel.add(serviceRequestButton);
		sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		sidePanel.add(profileButton);

	}

	public JButton createButton(String name) {
		JButton button = new JButton(name);
		button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		button.setSize(50, 30);
		button.add(Box.createRigidArea(new Dimension(180, 30)));
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setFocusPainted(false);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(230, 40));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(70, 70, 70));
		button.add(Box.createVerticalStrut(1));
		button.setBorderPainted(false);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (name) {
				case "Bill Generator":
					center.remove(dafault);
					dafault = displayBillPanel();
					center.add(dafault);
					center.revalidate();
					center.repaint();
					break;
				case "Account Details":
					center.remove(dafault);
					dafault = displayUserData();
					center.add(dafault);
					center.revalidate();
					center.repaint();

					break;

				case "Profile":
					center.remove(dafault);
					dafault = profile();
					center.add(dafault);
					center.revalidate();
					center.repaint();
					break;

				case "Service Request":
					center.remove(dafault);
					dafault = serviceRequest();
					center.add(dafault);
					center.revalidate();
					center.repaint();
					break;

				}

			}
		});
		return button;
	}

	public JPanel displayUserData() {

		JPanel displayUserPanel = new JPanel();
		displayUserPanel.setLayout(new BoxLayout(displayUserPanel, BoxLayout.Y_AXIS));
		displayUserPanel.setAlignmentX(CENTER_ALIGNMENT);
		displayUserPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		ShowUserData userDataPanel = new ShowUserData();
		displayUserPanel.add(userDataPanel, BorderLayout.CENTER);
		return displayUserPanel;
	}

	public JPanel displayBillPanel() {

		JPanel displayBillPanel = new JPanel();
		displayBillPanel.setLayout(new BoxLayout(displayBillPanel, BoxLayout.Y_AXIS));
		displayBillPanel.setAlignmentX(CENTER_ALIGNMENT);
		displayBillPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		BillGenerator userDataPanel = new BillGenerator();
		displayBillPanel.add(userDataPanel, BorderLayout.CENTER);
		return displayBillPanel;
	}

	public JPanel profile() {

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
		profilePanel.setAlignmentX(CENTER_ALIGNMENT);
		profilePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		Profile userDataPanel = new Profile();
		profilePanel.add(userDataPanel, BorderLayout.CENTER);
		return profilePanel;
	}

	public JPanel serviceRequest() {

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
		profilePanel.setAlignmentX(CENTER_ALIGNMENT);
		profilePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		Service_Request_Table userDataPanel = new Service_Request_Table();
		profilePanel.add(userDataPanel, BorderLayout.CENTER);
		return profilePanel;
	}

}
