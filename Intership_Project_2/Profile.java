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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Profile  extends JPanel{

	private JTextField  adminIdField,nameField,emailField,roleField;
	JButton updateButton;
	private static final Color BUTTON_COLOR = new Color(41, 100, 145);
	private static final Color BUTTON_HOVER_COLOR = new Color(41, 128, 185);
	private static final Color BUTTON_PRESS_COLOR = new Color(41, 128, 225);
	
	public Profile() {
		
		initComponent();
		fetchAdminData();
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

	        
	        
	        JLabel temp=new JLabel("Profile");
	        temp.setFont(new Font("Segoe UI",Font.BOLD,25));
	        temp.setForeground(Color.black);
	        temp.setBorder( BorderFactory.createEmptyBorder(10, 0, 100, 0) );
	        
	        
	       
	      
	        JPanel formPanel = new JPanel();
			formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
			formPanel.setOpaque(false);
			formPanel.setAlignmentX(CENTER_ALIGNMENT);
			
			addFormRow("Admin",adminIdField , formPanel);
			addFormRow("Name", nameField, formPanel);
			addFormRow("Email", emailField, formPanel);
			addFormRow("Role", roleField, formPanel);
		
	        
	        mainPanel.add(temp);
	        mainPanel.add(formPanel);
	        
	        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
			buttonPanel.setOpaque(false);
			
			buttonPanel.add(updateButton);
			
			
			

			mainPanel.add(buttonPanel);
			mainPanel.add(Box.createVerticalGlue());
	       
	        return mainPanel;
	    }
	    
	    
	    public void initComponent()
	    {
	    	adminIdField = new JTextField(40);
	    	adminIdField.setEditable(false);
	    	nameField = new JTextField(40);
	    	emailField = new JTextField(40);
	    	roleField = new JTextField(40);
	    	roleField.setEditable(false);
	
	    	updateButton=createStyledButton("Update Profile");
	    

	         
	    }
	    private void addFormRow(String labletext, JTextField field, JPanel formepanal) {

			JPanel rowpanal = new JPanel();
			rowpanal.setLayout(new BoxLayout(rowpanal, BoxLayout.X_AXIS));
			rowpanal.setOpaque(false);

			JLabel lable = new JLabel(labletext);
			lable.setForeground(Color.BLACK);
			lable.setFont(new Font("Arial", Font.PLAIN,22));
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
	        
	        button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					switch(text)
					{
					
						
						case "Update Profile":
						{
							String name=nameField.getText();
							String email=emailField.getText();
							System.out.println(name);
							System.out.println(email);
							String query ="UPDATE nvidia_admin_sigin SET adminName = '"+name+"',email='"+email+"' WHERE mobileNumber = "+admin_Dashboard.number1+";";
							
							 try {
								 	ConnnectionJDBC con=new ConnnectionJDBC();
									con.s.executeUpdate(query);
									JOptionPane.showMessageDialog(Profile.this, "Profile Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
									revalidate();
//									repaint();
									
									
								} catch (SQLException e1) {
									
									JOptionPane.showMessageDialog(Profile.this, "Failed Update Profile","Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
									}
						}
						
					}
					
				}
			});

	        return button;
	    }
	
	    
	    public void fetchAdminData()
	    {
	    	
	    	try {
	    		
	    		String query="select adminId,adminName,email,role from nvidia_admin_sigin where mobileNumber ="+admin_Dashboard.number1+";";
		    	ConnnectionJDBC co=new ConnnectionJDBC();
				ResultSet rs=co.s.executeQuery(query);
				
				if(rs.next())
				{
					System.out.println("kledskm");
					adminIdField.setText(rs.getString("adminId"));
					nameField.setText(rs.getString("adminName"));
					emailField.setText(rs.getString("email"));
					roleField.setText(rs.getString("role"));
					System.out.println("kledskm");
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	    }
	
}
