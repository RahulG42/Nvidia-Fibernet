package Intership_Project_2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BillGenerator extends JPanel {

	private JTextField AccountIDField, planBillField, dueFineField, stateTaxField, totalAmountField;
	JButton generateBilltButton, calculateTotoalButton;
	String bill, due, tax, date;
	double total;
	String paymentType;
	private static final Color BUTTON_COLOR = new Color(41, 100, 145);
	private static final Color BUTTON_HOVER_COLOR = new Color(41, 128, 185);
	private static final Color BUTTON_PRESS_COLOR = new Color(41, 128, 225);

	public BillGenerator() {
		initComponent();
		JPanel mainPanel = mainContent();
		add(mainPanel);
		setVisible(true);

	}

	public JPanel mainContent() {
		JPanel mainPanel = new JPanel(null);
		mainPanel.add(Box.createVerticalStrut(50));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel temp = new JLabel("Bill Generation");
		temp.setFont(new Font("Segoe UI", Font.BOLD, 25));
		temp.setForeground(Color.black);
		temp.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0));

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setOpaque(false);
		formPanel.setAlignmentX(CENTER_ALIGNMENT);

		addFormRow("Account ID", AccountIDField, formPanel);
		addFormRow("Plan Bill(Monthly)", planBillField, formPanel);
		addFormRow("Due Fine ($)", dueFineField, formPanel);
		addFormRow("State Tax", stateTaxField, formPanel);
		addFormRow("Total Amount", totalAmountField, formPanel);

		mainPanel.add(temp);
		mainPanel.add(formPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
		buttonPanel.setOpaque(false);

		buttonPanel.add(calculateTotoalButton);
		buttonPanel.add(generateBilltButton);

		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalGlue());

		return mainPanel;
	}

	public void initComponent() {
		AccountIDField = new JTextField(40);
		AccountIDField.setText("");

		dueFineField = new JTextField(40);
		dueFineField.setText("0.0");

		stateTaxField = new JTextField(40);
		stateTaxField.setEditable(false);
		stateTaxField.setText("1.99");

		planBillField = new JTextField(40);
		planBillField.setText("0.0");

		totalAmountField = new JTextField(40);
		totalAmountField.setText("0.0");

		calculateTotoalButton = createStyledButton("Calaculate Total");
		generateBilltButton = createStyledButton("Generate Bill");
	}

	public void calculateTotal() {

//	    	JOptionPane.showMessageDialog(BillGenerator.this,"Enter Account ID first","Error", JOptionPane.ERROR_MESSAGE);
//	    	System.out.println();

		if (AccountIDField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(BillGenerator.this, "Enter Account ID first", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {

			try {
				String accountNo = AccountIDField.getText();
				System.out.println(accountNo);
				String query = "Select (planPrice) from broaddband_plans where accountNo='" + accountNo + "'" + ";";
				ConnnectionJDBC co = new ConnnectionJDBC();

				ResultSet rs = co.s.executeQuery(query);

				if (rs.next()) {

					planBillField.setText(String.valueOf(rs.getDouble("planPrice")));
//					dueFineField.setText(String.valueOf(rs.getDouble("dueFine")));
//					stateTaxField.setText(String.valueOf(rs.getDouble("gstTax")));					
					bill=planBillField.getText();
					double i=Double.parseDouble(bill);
					due=dueFineField.getText();
					double i2=Double.parseDouble(due);					
					tax=stateTaxField.getText();
					double i3=Double.parseDouble(tax);
					total=(i+i2)*i3;					
					totalAmountField.setText(String.valueOf(total));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void addFormRow(String labletext, JTextField field, JPanel formepanal) {

		JPanel rowpanal = new JPanel();
		rowpanal.setLayout(new BoxLayout(rowpanal, BoxLayout.X_AXIS));
		rowpanal.setOpaque(false);

		JLabel lable = new JLabel(labletext);
		lable.setForeground(Color.BLACK);
		lable.setFont(new Font("Arial", Font.PLAIN, 22));
		lable.setPreferredSize(new Dimension(200, 25));
		rowpanal.add(lable);

		field.setFont(new Font("Arial", Font.PLAIN, 22));
		rowpanal.add(field);

		formepanal.add(rowpanal);
		formepanal.add(Box.createVerticalStrut(40));
		rowpanal.add(Box.createRigidArea(new Dimension(0, 10)));

	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				boolean isPressed = getModel().isPressed();
				boolean isMouseOver = getModel().isRollover();

				Color bgColor = isPressed ? BUTTON_PRESS_COLOR : isMouseOver ? BUTTON_HOVER_COLOR : BUTTON_COLOR;

				GradientPaint gradient = new GradientPaint(0, 0, bgColor, 0, getHeight(), bgColor.darker());
				g2.setPaint(gradient);
				g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

				g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
				FontMetrics metrics = g2.getFontMetrics();
				int textX = (getWidth() - metrics.stringWidth(getText())) / 2;
				int textY = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

				g2.setColor(new Color(0, 0, 0, 50));
				g2.drawString(getText(), textX + 1, textY + 1);

				g2.setColor(Color.WHITE);
				g2.drawString(getText(), textX, textY);

				g2.dispose();
			}
		};

		button.setPreferredSize(new Dimension(150, 40));
		button.setBorder(new EmptyBorder(5, 15, 5, 15));
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch (text) {
				case "Calaculate Total":

					calculateTotal();

					break;
				case "Generate Bill":
					generateAndSaveBill();
					break;

				}

			}
		});

		return button;
	}

	
	private void generateAndSaveBill() {
	 

	    if (AccountIDField.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Enter AccountID First", "Failed", JOptionPane.ERROR_MESSAGE);
	    } else if (stateTaxField.getText().equals("0.0")) {
	        JOptionPane.showMessageDialog(this, "Tax cannot be empty", "Failed", JOptionPane.ERROR_MESSAGE);
	    } else {
	        try {
	            String accountNo = AccountIDField.getText().trim();
	            System.out.println(accountNo);
	            String trD = createTraction();
	            paymentType = "Due Payment"; // Fixed spelling mistake
	            
	            LocalDate today = LocalDate.now();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	            String formattedDate = today.format(formatter);
	            
	            double bill2 = total;
	            // Query to check if accountNo exists in the table
	            String query1 = "SELECT * FROM bill WHERE accountNo = '" + accountNo+"';";

	           
	            String query = "";
	            ConnnectionJDBC con1 = new ConnnectionJDBC();

	            try {
	                ResultSet rs = con1.s.executeQuery(query1);
	                
	                if (rs.next()) {
	                	System.out.println(rs.getString("accountNo"));
	                    // If record exists, UPDATE the bill
	                    query = "UPDATE bill SET billAmount = " + bill2 +
	                            ", transactionID = '" + trD + 
	                            "', paymentType = '" + paymentType + 
	                            "', dueFine = '" + due + 
	                            "', gstTax = '" + tax + 
	                            "', `date` = '" + formattedDate + 
	                            "' WHERE accountNo = '" + accountNo+"';";
	                } else {
	                    // If record doesn't exist, INSERT new bill
	                    query = "INSERT INTO bill (accountNo, billAmount, transactionID, paymentType, dueFine, gstTax, `date`) " +
	                            "VALUES (" + accountNo + ", " + bill2 + ", '" + trD + "', '" + paymentType + "', '" + due + "', '" + tax + "', '" + formattedDate + "')";
	            System.out.println(query);
	                }

	                con1.s.executeUpdate(query);
	                JOptionPane.showMessageDialog(null, "Bill Generated and Added to User Account", "Success", JOptionPane.INFORMATION_MESSAGE);
	                
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

    private void addSaveBillButton(JPanel mainPanel) {
        JPanel saveBillPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveBillPanel.setOpaque(false);

        JButton saveBillButton = createStyledButton("Save Bill");
        saveBillButton.setPreferredSize(new Dimension(120, 40));
        saveBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAndSaveBill();
            }
        });

        saveBillPanel.add(saveBillButton);
        mainPanel.add(saveBillPanel);
    }
    
    public String  createTraction()
    {
    	Random random = new Random();
        return String.format("%05d", random.nextInt(100000));
    	
    	
    }
	

}
