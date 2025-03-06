

package Intership_Project_2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class BuyConnection extends JFrame {

	static String accountNo=User_DashBoard.accountNo;
	static long phoneNo=User_DashBoard.phoneNo;
	Plan selectedPlan;
	
	 private static final Color BUTTON_COLOR = new Color(76, 161, 175);
	    private static final Color BUTTON_HOVER_COLOR = new Color(58, 143, 157);
	    private static final Color BUTTON_PRESS_COLOR = new Color(45, 125, 138);
	
	private static final Color NVIDIA_GREEN = new Color(100, 245,0);
	private static final Font TITLE_FONT =  new Font("Segoe UI", Font.BOLD,28);
	private static final Font SUBTITLE_FONT =  new Font("Segoe UI Emoji", Font.PLAIN,14);
	private static final Font PRICE_FONT =  new Font("Segoe UI", Font.BOLD,24);
	
	BuyConnection()
	{
		setTitle("Nvidia Fibernet Broadband");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,800);
		setBackground(Color.WHITE);
		
		
		
		
		  GradientPanel mainPanel = new GradientPanel("/icons/nvidia.jpg");
	      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	      mainPanel.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createLineBorder(new Color(200, 200, 200)),
		            BorderFactory.createEmptyBorder(20, 20, 20, 20)
		    ));
	      addHeader(mainPanel);
	      addPlansPanel(mainPanel);
	      addRegistrationForm(mainPanel);
	      JScrollPane scrollPane = new JScrollPane(mainPanel);
	       scrollPane.setBorder(null);
	       scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	       add(scrollPane);
	       setLocationRelativeTo(null);

	      setVisible(true);
	};
	
	public void addHeader(JPanel mainPanel)
	{
		JPanel headlerPanel=new JPanel();
		headlerPanel.setOpaque(false);
		headlerPanel.setLayout(new BoxLayout(headlerPanel, BoxLayout.Y_AXIS));
		
		JLabel titleLabel =new JLabel("Nvidia Fibernet");
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(NVIDIA_GREEN);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel subtitle  =new JLabel("Experience Lightning-Fast Internet");
		subtitle.setFont(SUBTITLE_FONT);
		subtitle.setForeground(Color.DARK_GRAY);
		subtitle.setAlignmentX(CENTER_ALIGNMENT);
		
		
		headlerPanel.add(titleLabel);
		headlerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		headlerPanel.add(subtitle);
		headlerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		mainPanel.add(headlerPanel);
		
	}
	
	 private ArrayList<Plan> createPlans() {
	        ArrayList<Plan> plans = new ArrayList<>();
	        plans.add(new Plan("Basic", 49.99, "500GB", "100 Mbps", "30 Days", new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime Basic")), new Color(135, 206, 235)));
	        plans.add(new Plan("Standard", 69.99, "1000GB", "300 Mbps", "30 Days", new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Standard")), new Color(100, 149, 237)));
	        plans.add(new Plan("Premium", 89.99, "2000GB", "500 Mbps", "30 Days", new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Premium", "HBO Max")), new Color(65, 105, 225)));
	        plans.add(new Plan("Ultra", 119.99, "4000GB", "1 Gbps", "30 Days",  new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Premium", "HBO Max", "ESPN+")), new Color(0, 0, 139)));
	        plans.add(new Plan("Gamer Pro", 149.99, "Unlimited", "2 Gbps", "30 Days", new ArrayList<>(Arrays.asList("All Streaming Apps", "Gaming Server Priority", "Cloud Gaming")), NVIDIA_GREEN));
	        return plans;
	    }
	
	 
	 private void addPlansPanel(JPanel mainPanel) {
	        JPanel plansPanel = new JPanel(new GridLayout(0, 2, 20, 20));
	        plansPanel.setOpaque(false);
	        plansPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));

	        ArrayList<Plan> plans = createPlans();
	        ButtonGroup planGroup = new ButtonGroup();
	        
	        for (int i = 0; i < plans.size(); i++) {
	            Plan plan = plans.get(i);
	           
	            RoundedPanel planCard = new RoundedPanel(Color.WHITE);
	            planCard.setLayout(new BoxLayout(planCard, BoxLayout.Y_AXIS));
	            planCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	            // Plan type header
	            JLabel typeLabel = new JLabel(plan.planType);
	            typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	            typeLabel.setForeground(plan.themeColor);
	            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	            // Price
	            JLabel priceLabel = new JLabel("\u0024" + String.format("%.2f", plan.planPrice) + "/month");
	            priceLabel.setFont(PRICE_FONT);
	            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	            // Features panel
	            JPanel featuresPanel = new JPanel();
	            featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
	            featuresPanel.setOpaque(false);
	            
	          
	            
	            
	            //My-icons-method
	            addIcon(featuresPanel,"rocket",plan.planSpeed);
	            addIcon(featuresPanel,"bar_chart",plan.planData);
	            addIcon(featuresPanel,"stopwatch",plan.planDuration);
	            addIcon(featuresPanel,"mobile_phone",String.join(", ", plan.appSubscriptions));
	            
	            
	            //sirs emoji method
//	            addFeature(featuresPanel, "\uD83D\uDE80 " + plan.planSpeed);
//	            addFeature(featuresPanel, "\uD83D\uDCCA " + plan.planData);
//	            addFeature(featuresPanel, "\u23F1\uFE0F " + plan.planDuration);
//	            addFeature(featuresPanel, "\uD83D\uDCF1 Apps: " + String.join(", ", plan.appSubscriptions));
            
	            
	            // Radio button
	            JRadioButton radioBtn = new JRadioButton("Select Plan");
	            radioBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	            radioBtn.setForeground(plan.themeColor);
	            radioBtn.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    selectedPlan = plan;
	                }
	            });
	            planGroup.add(radioBtn);
	            // Add components to card
	            planCard.add(typeLabel);
	            planCard.add(Box.createRigidArea(new Dimension(0, 10)));
	            planCard.add(priceLabel);
	            planCard.add(Box.createRigidArea(new Dimension(0, 15)));
	            planCard.add(featuresPanel);
	            planCard.add(Box.createRigidArea(new Dimension(0, 15)));
	            planCard.add(radioBtn);
	            plansPanel.add(planCard);
	        }

	        mainPanel.add(plansPanel);
	        
	 }
	 public void addFeature(JPanel panel, String feature) {
	        JLabel featureLabel = new JLabel(feature);
	        featureLabel.setFont(SUBTITLE_FONT);
	        featureLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	        panel.add(featureLabel);
	    }
	 
	 public void addIcon(JPanel panel,String name,String type)
	 {
		 ImageIcon original =  new ImageIcon(getClass().getResource("/icons/"+name+".png"));
         Image resizedImage = original.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
         ImageIcon resizedIcon = new ImageIcon(resizedImage);
         JLabel label = new JLabel(type, resizedIcon, JLabel.LEFT);
         label.setFont(new Font("Segoe UI", Font.PLAIN,14));
         label.setHorizontalTextPosition(SwingConstants.RIGHT); 
         panel.add(label);
	 }
	 
	 public void addRegistrationForm (JPanel mainPanel)
	 {
		 JPanel formPanel = new RoundedPanel(Color.WHITE);
		 formPanel.setBackground(Color.blue);
		 formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		 formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20,20, 20));
		 
		 JLabel formTitle = new JLabel("Complete Your Registration");
	        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	        formTitle.setForeground(NVIDIA_GREEN);
	        
	        JTextField mobileField = createStyledTextField("Enter Mobile Number");
	        JTextField userNameField = createStyledTextField("Enter Username");
	        JComboBox<String> cityCombo = createStyledComboBox();
	        
	        JButton submitBtn = createStyledButton("Register Now");
	        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
	        submitBtn.setForeground(Color.BLACK);
	        submitBtn.setBackground(NVIDIA_GREEN);
	        submitBtn.setBorder(new EmptyBorder(10, 30, 10, 30));
	        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        submitBtn.addActionListener(e -> handleSubmission(mobileField, userNameField, cityCombo));

	        formPanel.add(formTitle);
	        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	        formPanel.add(mobileField);
	        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        formPanel.add(userNameField);
	        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        formPanel.add(cityCombo);
	        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	        formPanel.add(submitBtn);
	        
	        mainPanel.add(formPanel);
		 
	 }
	 
	  private JComboBox<String> createStyledComboBox() {
	        String[] cities = {"Select City", "Austin", "Texas", "Raleigh, North Carolina", "Kansas City, Missouri", "New York City, Brooklyn", "Manhattan - New York City"};
	        JComboBox<String> combo = new JComboBox<>(cities);
	        combo.setMaximumSize(new Dimension(300, 40));
	        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
	        combo.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(5,5,5,5)));
	        return combo;
	    }
	  
	  private JTextField createStyledTextField(String placeholder) {
	        JTextField field = new JTextField(20);
	        field.setFont(new Font("Arial", Font.PLAIN, 16));
	        field.setForeground(Color.LIGHT_GRAY);
	        field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(10, 10, 10, 10)));
	        field.setMaximumSize(new Dimension(300, 40));
	        field.setAlignmentX(Component.CENTER_ALIGNMENT);
	        setPlaceHolder(field, placeholder);
	        return field;
	    }
	  
	  private void setPlaceHolder(final JTextComponent textComponent, String placeholder) {
	        textComponent.setText(placeholder);
	        textComponent.setForeground(Color.GRAY);
	        textComponent.addFocusListener(new FocusAdapter() {
	            public void focusGained(FocusEvent e) {
	                if (textComponent.getText().equals(placeholder)) {
	                    textComponent.setText("");
	                    textComponent.setForeground(Color.DARK_GRAY);
	                }
	            }
	            
	            public void focusLost(FocusEvent e) {
	                if (textComponent.getText().isEmpty()) {
	                    textComponent.setText(placeholder);
	                    textComponent.setForeground(Color.GRAY);
	                }
	            }
	        });
	    }
	  

	    private void handleSubmission(JTextField mobileField, JTextField userNameField, JComboBox<String> cityCombo) {
	        if (selectedPlan == null ) {
	            JOptionPane.showMessageDialog(this, "Please Select a plan first!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (mobileField.getText().isEmpty() || userNameField.getText().isEmpty() || cityCombo.getSelectedIndex() == 0) {
	            JOptionPane.showMessageDialog(this, "Please Fill All Fields Firstly!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        long enteredPhoneNo;
	        try {
	            enteredPhoneNo = Long.parseLong(mobileField.getText());
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Invalid Phone Number!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (enteredPhoneNo != phoneNo) {
	            JOptionPane.showMessageDialog(this, "Please Use The Same Phone Number To Register", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        String query;
	        String planType = selectedPlan.planType;
	        double planPrice =  selectedPlan.planPrice;
	        String planData =  selectedPlan.planData;
	        String planDuration =  selectedPlan.planDuration;
	        String speed =  selectedPlan.planSpeed;
	        
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
	        String formattedDate = currentDate.format(formatter);
	        
	        ConnnectionJDBC con = new ConnnectionJDBC();
	        generateAcccountNo();
	        query = "INSERT INTO broaddband_plans (mobilenumber, planType, planPrice, planData, speed, planDuration, accountNo,buyDate)" +
	                " VALUES ('" + phoneNo + "', '" + planType + "', '" + planPrice + "', '" + planData + "', '" + speed + "', '" + planDuration + "', '" + accountNo + "','"+formattedDate+"');";
	        
	        try {
	            con.s.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        JOptionPane.showMessageDialog(this, "Registration successful!\nPlan: " + planType +
	                "\nCity: " + cityCombo.getSelectedItem() + "\nPlease Re-Login To Load The New Plan");
	        
	        
	    }
	    
	    private void generateAcccountNo() {
	        accountNo = "ACC" + (int) (Math.random() * 100000);
	    }
	    
	    
	    
	    
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
	    
}
