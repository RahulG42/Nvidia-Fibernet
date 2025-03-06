package Intership_Project_2;



import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentGUI extends JFrame {
    private JTextField otherAmountField, dueAmountField;
    private JRadioButton payDueButton, payOtherButton;
    private ButtonGroup paymentGroup;

    private static double billAmount, tax, dueFine, planAmt, gstTax;
    private static String accountNo, transactionId, paymentType, date;
    private static BillLogics logics;
    private BufferedImage backgroundImage;

    private static final Color BUTTON_COLOR = new Color(76, 161, 175);
    private static final Color BUTTON_HOVER_COLOR = new Color(58, 143, 157);
    private static final Color BUTTON_PRESS_COLOR = new Color(45, 125, 138);

    public PaymentGUI(String accountNumber) {
        logics = new BillLogics();
        try {
        	
            logics.getDetails(accountNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching bill details.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PaymentGUI.planAmt = BillLogics.billAmount - 4.90;
        PaymentGUI.billAmount = BillLogics.billAmount;
        PaymentGUI.accountNo = BillLogics.accountNo;
        
        PaymentGUI.date = BillLogics.date;
        PaymentGUI.tax = BillLogics.tax;
        PaymentGUI.transactionId = BillLogics.transactionID;
        PaymentGUI.paymentType = BillLogics.paymentType;
        PaymentGUI.dueFine = BillLogics.dueFine;

        setTitle("Nvidia-Fibernet Bill Payment");
        setLocation(500,200);
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loadBackgroundImage();  // Load background image

        BackgroundPanel contentPane = new BackgroundPanel();
        setContentPane(contentPane);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        addHeaderPanel(mainPanel);
        addPaymentOptionsPanel(mainPanel);
        addPlanDetailsPanel(mainPanel);
        addButtonPanel(mainPanel);
        addSaveBillButton(mainPanel);
        add(mainPanel);

       
        contentPane.add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadBackgroundImage() {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/icons/nvidiabackground.jpg"));
            backgroundImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = backgroundImage.getGraphics();
            g.drawImage(imageIcon.getImage(), 0, 0, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println("Error loading background image.");
        }
    }

    private class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.setColor(new Color(255, 255, 255, 200)); // Transparent overlay
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        }
    }

    private void addHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        headerPanel.setOpaque(false);

        JLabel billLabel = new JLabel("Bill Amt:");
        billLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel amountLabel = new JLabel("$ " + String.format("%.2f", billAmount));
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel dueDateLabel = new JLabel("Due Date:", SwingConstants.RIGHT);
        dueDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM. dd, yyyy");
        JLabel dateLabel = new JLabel(date, SwingConstants.RIGHT);
        
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        headerPanel.add(billLabel);
        headerPanel.add(dueDateLabel);
        headerPanel.add(amountLabel);
        headerPanel.add(dateLabel);

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(10);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private void addPaymentOptionsPanel(JPanel mainPanel) {
    	JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);

        paymentGroup = new ButtonGroup();

        JPanel duePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        duePanel.setOpaque(false);

        payDueButton = new JRadioButton("  Pay Due Amount");
        payDueButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        payDueButton.setFocusPainted(false);
        payDueButton.setOpaque(false);
        
        
        dueAmountField = createStyledTextField();
 
        dueAmountField.setEditable(false);
        dueAmountField.setText("$ " + String.format("%.2f", billAmount));
        dueAmountField.setHorizontalAlignment(JTextField.CENTER);
        dueAmountField.setEnabled(false);

        JPanel otherPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        otherPanel.setOpaque(false);

        payOtherButton = new JRadioButton("Pay Other Amount");
        payOtherButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        payOtherButton.setFocusPainted(false);
        payOtherButton.setOpaque(false);

        otherAmountField = createStyledTextField();
        otherAmountField.setEnabled(false);

        otherAmountField.setEditable(true);
        paymentGroup.add(payDueButton);
        paymentGroup.add(payOtherButton);

        payDueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dueAmountField.setEnabled(true);
                otherAmountField.setEnabled(false);
            }
        });

        payOtherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dueAmountField.setEnabled(false);
                otherAmountField.setEnabled(true);
            }
        });

        duePanel.add(payDueButton);
        duePanel.add(dueAmountField);
        otherPanel.add(payOtherButton);
        otherPanel.add(otherAmountField);

        optionsPanel.add(duePanel);
        optionsPanel.add(otherPanel);
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    

    private void addPlanDetailsPanel(JPanel mainPanel) {
    	 JPanel planPanel = new JPanel(new GridLayout(2, 1, 0, 5));
         planPanel.setOpaque(false);

         JLabel planLabel = new JLabel(User_DashBoard.planType); 
         planLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

         JLabel billingLabel = new JLabel("Monthly Billing");
         billingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
         billingLabel.setForeground(Color.BLACK);

         planPanel.add(planLabel);
         planPanel.add(billingLabel);

         mainPanel.add(planPanel);
         mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addButtonPanel(JPanel mainPanel) {
    	 JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         buttonPanel.setOpaque(false);

         JButton proceedButton = createStyledButton("Proceed To Pay");
         proceedButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if (!payDueButton.isSelected() && !payOtherButton.isSelected()) {
                     JOptionPane.showMessageDialog(PaymentGUI.this,
                             "Please Select a Payment Option",
                             "Payment Option Required",
                             JOptionPane.WARNING_MESSAGE);
                     return;
                 }

                 double amount = payDueButton.isSelected() ? billAmount : parseAmount(otherAmountField.getText());

                 if (payOtherButton.isSelected() && amount <= 0) {
                     JOptionPane.showMessageDialog(PaymentGUI.this,
                             "Please Enter A Valid Amount",
                             "Invalid Amount",
                             JOptionPane.WARNING_MESSAGE);
                     return;
                 }

                 try {
					logics.pay();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                 if (billAmount == 0) {
                     JOptionPane.showMessageDialog(PaymentGUI.this,
                             "Due Is $0.0. Cannot Proceed To Payment " + String.format("%.2f", amount),
                             "Payment Failed",
                             JOptionPane.INFORMATION_MESSAGE);
                 } else {
                     JOptionPane.showMessageDialog(PaymentGUI.this,
                             "Proceeding To Payment Gateway With Amount: $" + String.format("%.2f", amount),
                             "Payment Processing",
                             JOptionPane.INFORMATION_MESSAGE);
                 }
             }
         });

         buttonPanel.add(proceedButton);
         mainPanel.add(buttonPanel);    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isPressed = getModel().isPressed();
                boolean isMouseOver = getModel().isRollover();

                Color bgColor = isPressed ? BUTTON_PRESS_COLOR :
                        isMouseOver ? BUTTON_HOVER_COLOR :
                                BUTTON_COLOR;

                GradientPaint gradient = new GradientPaint(
                        0, 0, bgColor,
                        0, getHeight(), bgColor.darker()
                );
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

        return button;
    }
    
    private double parseAmount(String text) {
        try {
            return Double.parseDouble(text.replace("$", "").trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private void generateAndSaveBill() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Bill");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("pdf files (*.pdf)", "pdf"));

        if (billAmount != 0) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("=================================\n");
                    writer.write("        Nvidia-Fibernet Bill      \n");
                    writer.write("=================================\n\n");
                    writer.write("Bill Due Date: " + new SimpleDateFormat("MMM. dd, yyyy").format(new Date()) + "\n");
                    date = new SimpleDateFormat("MMM. dd, yyyy").format(new Date());
                    writer.write("Plan Bill: $" + planAmt + "\n");
                    writer.write("Due Fine: $ " + dueFine + "\n");
                    writer.write("State Tax: $ " + gstTax + "\n");
                    writer.write("Payment Type: " + paymentType + "\n");
                    writer.write("Total Bill: $ " + billAmount + "\n");
                    writer.write("Billing Type: Monthly Billing\n");
                    writer.write("\n=================================\n");

                    JOptionPane.showMessageDialog(this,
                            "Bill saved successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error saving bill: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Bill Amount Is $0.0 Cannot Save The Bill!",
                    "Failed",
                    JOptionPane.INFORMATION_MESSAGE);
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


//    public static void main(String[] args) {
//        new PaymentGUI("ACC73551");
//    }
}