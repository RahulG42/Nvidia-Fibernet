package Intership_Project_2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class My_Service_Request extends JFrame {

    private JTextField requestIdField, userField;
    private JComboBox<String> requestTypeDropdown;
    String rid;
    private JSpinner dateSpinner;
    private static int counter = 1; 
    JButton SubmitButton, ClearFormButton, GoBack;
    private static final Color BUTTON_COLOR = new Color(41, 100, 145);
    private static final Color BUTTON_HOVER_COLOR = new Color(41, 128, 185);
    private static final Color BUTTON_PRESS_COLOR = new Color(41, 128, 225);
    
    public static void main(String[] args) {
        new My_Service_Request();
    }

    public My_Service_Request() {
        setUpFrame();
        initComponent();
        setUpListener();
        
        JPanel mainPanel = mainContent();
        setContentPane(gradientPanel());
        add(mainPanel);
        setVisible(true);
    }

    public void setUpFrame() {
        setTitle("Temp");
        setSize(1366, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel mainContent() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        
        
        JLabel temp=new JLabel("Service Request Form");
        temp.setFont(new Font("Segoe UI",Font.BOLD,25));
        temp.setForeground(Color.white);
        temp.setBorder( BorderFactory.createEmptyBorder(10, 0, 100, 0) );
        
        
       
      
        JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setOpaque(false);
		formPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		addFormRow("Request ID", requestIdField, formPanel);
		addFormRow("User", userField, formPanel);
		addFormRow("Request Type", requestTypeDropdown, formPanel);
		addFormRow("Date", dateSpinner, formPanel);
        
        mainPanel.add(temp);
        mainPanel.add(formPanel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
		buttonPanel.setOpaque(false);
		
		buttonPanel.add(SubmitButton);
		buttonPanel.add(ClearFormButton);
		buttonPanel.add(GoBack);

		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalGlue());
       
        return mainPanel;
    }
    
    public void setUpListener() {

    	SubmitButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	       
    	        Date selectedDate = (Date) dateSpinner.getValue();
    	    	        
    	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    	        
    	        String formattedDate = sdf.format(selectedDate);
    	        
    	        String requestType = (String) requestTypeDropdown.getSelectedItem();
   	        
    	        String status = "Pending";
   	     
    	        String query = "INSERT INTO service_request (request_id, user, type, status, date) VALUES ('" + rid + "', '" + SigninGUI.name + "', '" + requestType + "', '" + status + "', '" + formattedDate + "');";
    	           	       
    	        ConnnectionJDBC con = new ConnnectionJDBC();
    	        try {
    	            con.s.executeUpdate(query);
    	            JOptionPane.showMessageDialog(null, "Service Request Generated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    	            dispose();
    	        } catch (SQLException e1) {
    	        	 JOptionPane.showMessageDialog(null, "Unable to generate request please try again", "Success", JOptionPane.INFORMATION_MESSAGE);
    	            e1.printStackTrace();
    	        }
    	    }
    	});

    	ClearFormButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				requestIdField.setText("");
				userField.setText("");
				requestTypeDropdown.repaint();
				
				 dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
				dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
			}

		});
    	
    	GoBack.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			
    			dispose();
    		}
    		
    	});

	}
	
    public void initComponent()
    {
    	 requestIdField = new JTextField(40);
    	 requestIdField.setEditable(false);
    	 rid=generateID();
    	 requestIdField.setText(generateID());
    	 
         userField = new JTextField(40);
         userField.setText(SigninGUI.name);
         requestTypeDropdown = new JComboBox<>(new String[]{"Connection Issue", "Billing Issue", "Technical Support"});

         dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
         dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
         
         
         SubmitButton=createStyledButton("Submit Request");
         ClearFormButton=createStyledButton("Clear Form");
         GoBack=createStyledButton("Go Back");
         
         
    }
    private void addFormRow(String labletext, JComponent componant, JPanel formepanal) {

		JPanel rowpanal = new JPanel();
		rowpanal.setLayout(new BoxLayout(rowpanal, BoxLayout.X_AXIS));
		rowpanal.setOpaque(false);

		JLabel lable = new JLabel(labletext);
		lable.setForeground(Color.WHITE);
		lable.setFont(new Font("Arial", Font.PLAIN,22));
		lable.setPreferredSize(new Dimension(200, 25));
		rowpanal.add(lable);

	
		componant.setFont(new Font("Arial", Font.PLAIN, 22));
		rowpanal.add(componant);


		formepanal.add(rowpanal);
		formepanal.add(Box.createVerticalStrut(40));
		rowpanal.add(Box.createRigidArea(new Dimension(0, 10)));

	}

    public JPanel gradientPanel() {
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(41, 128, 185),
                        0, getHeight(), new Color(0, 30, 70)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setOpaque(true);
        return gradientPanel;
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
    
    public static String generateID() {
    	 Random random = new Random();
         return String.format("%05d", random.nextInt(100000));
        
    }

}