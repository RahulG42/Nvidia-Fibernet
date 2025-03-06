package Intership_Project_2;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.GradientPaint ;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class User_DashBoard extends JFrame{
	
	static String name1;
	static String status ;
	static String planType ;
	static String planData ;
	static String Speed ;
	static String planDuration ;
	static String buyDate;
	
	static long phoneNo;
	static double planPrice;
	static String accountNo;
	static long afterPhone;
	
	private JButton pay_Bills_Button;
	
	
	public User_DashBoard() throws SQLException
	{
		name1=SigninGUI.name;
		setUpFrame();
		JPanel headerP=createHeader();
		add(headerP,BorderLayout.NORTH);
		
		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		
		JPanel leftPanel  =createPromotionalPanel();
		mainPanel.add(leftPanel,BorderLayout.WEST);
		
		JPanel rightPanel  =createAccountDetailsPanel();
		mainPanel.add(rightPanel,BorderLayout.CENTER);
		
		
//		JPanel leftPanel  =createAccountDetailsPanel();
		
		add(mainPanel,BorderLayout.CENTER);
		
		getContentPane().setBackground(new Color(200, 240, 240));
		
	}
	
	
	public void setUpFrame()
	{
		JFrame frame=new JFrame();
		setVisible(true);
		setSize(1920,1080);
		setLayout(new BorderLayout());
		setTitle("Nvidia-Fibernet");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//from down here is user Panel
	
	
	public boolean getStatus() throws SQLException
	{
		String query="Select * from broaddband_plans where mobileNumber='"+phoneNo+"'"+";";
		ConnnectionJDBC con =new ConnnectionJDBC();
		
		ResultSet rs=con.s.executeQuery(query);
		
		if(rs.next())
		{
			planType=rs.getString("planType");
			planPrice=rs.getDouble("planPrice");
			planData=rs.getString("planData");
			Speed=rs.getString("speed");
			planDuration=rs.getString("planDuration");
			accountNo=rs.getString("accountNo");
			buyDate=rs.getString("buyDate");
			
			return true;
		}
		else {
			return false;
		}
		
	}

	
	public JPanel createAccountDetailsPanel() throws SQLException {
	    JPanel detailsPanel = new JPanel();
	    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
	    detailsPanel.setBackground(Color.WHITE);
	    detailsPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(new Color(200, 200, 200)),
	            BorderFactory.createEmptyBorder(20, 20, 20, 20)
	    ));

	    if (getStatus()) {
	        status = "ACTIVE";
	    } else {
	        status = "INACTIVE";
	        planType = "NA";
	        Speed = "NA";
	        planData = "NA";
	    }

	    // Status Panel
	    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    statusPanel.setBackground(Color.WHITE);
	    JLabel statusLabel = new JLabel(status);
	    statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    statusLabel.setForeground(status.equals("ACTIVE") ? new Color(40, 167, 69) : Color.RED);
	    statusPanel.add(statusLabel);

	    // Plan Panel
	    JPanel planPanel = new JPanel();
	    planPanel.setLayout(new BoxLayout(planPanel, BoxLayout.Y_AXIS));
	    planPanel.setBackground(Color.WHITE);
	    
	    JLabel planName = new JLabel("Plan: " + planType);
	    planName.setFont(new Font("Arial", Font.BOLD, 18));
	    planPanel.add(planName);

	    JLabel speedLabel = new JLabel("Speed: " + Speed);
	    speedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	    planPanel.add(speedLabel);

	    JLabel dataLabel = new JLabel("Data: " + planData);
	    dataLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	    planPanel.add(dataLabel);

	    // Due Date Panel
	    JPanel duePanel = new JPanel();
	    duePanel.setLayout(new BoxLayout(duePanel, BoxLayout.Y_AXIS));
	    duePanel.setBackground(Color.WHITE);

	    JLabel dueDate = new JLabel("Actual Due Date: 1st");
	    dueDate.setFont(new Font("Arial", Font.PLAIN, 14));
	    duePanel.add(dueDate);

	    JLabel dueDateLabel = new JLabel("Grace Period: 2nd Due Date");
	    dueDateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
	    duePanel.add(dueDateLabel);

	    // Usage Panel
	    JPanel usagePanel = new JPanel();
	    usagePanel.setBackground(Color.WHITE);
	    JLabel usageLabel = new JLabel("Usage: 45 GB of 60 GB");
	    usageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
	    usagePanel.add(usageLabel);

	    // Upgrade Panel
	    JPanel upgradePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    upgradePanel.setBackground(new Color(255, 240, 240));
	    upgradePanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(new Color(200, 200, 200)),
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)
	    ));

	    JLabel upgradeLabel = new JLabel("Upgrade for Netflix and higher speeds!");
	    upgradeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
	    upgradePanel.add(upgradeLabel);

	    JButton upgradeButton = new JButton("Upgrade Now");
	    
	    
	    upgradeButton.setFont(new Font("Arial", Font.BOLD, 12));
//	    upgradeButton.setBackground(new Color(255, 69, 0));
	
	    upgradePanel.add(upgradeButton);

	    // Adding all panels with proper spacing
	    
	    detailsPanel.add(statusPanel);
	    detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    detailsPanel.add(planPanel);
	    detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	    detailsPanel.add(duePanel);
	    detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    detailsPanel.add(usagePanel);
//	    JPanel graphPanel = createGraphPanel(planPanel,duePanel);
//	    detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
//	    detailsPanel.add(graphPanel);
	    detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	    detailsPanel.add(upgradePanel);

	    
	    
	    
	    
	    return detailsPanel;
	}

	
	
	//left Panel
	public JPanel createPromotionalPanel()
	{
		
		
//		JPanel promotionPanel=new JPanel();
//		promotionPanel.setBackground(Color.WHITE);
		
		JPanel gradientPanel = new JPanel() {
	         @Override
	         protected void paintComponent(Graphics g) {
	             super.paintComponent(g);
	             Graphics2D g2d = (Graphics2D) g;
	             GradientPaint gradient = new GradientPaint(
	                     0, 0, new Color(255, 100, 150),
	                     getWidth(), getHeight(), new Color(255, 150, 200)
	             );
	             g2d.setPaint(gradient);
	             g2d.fillRect(0, 0, getWidth(), getHeight());
	         }
	     };
	     
	     
	     JLabel promoText = new JLabel("<html><div style='text-align: center;'>" +
	             "Pay your Nvidia Fibernet<br/>bill via CRED UPI and Get<br/>" +
	             "<span style='font-size: 24px;'>up to Rs. 250*</span><br/>" +
	             "Cashback</div></html>");
	     promoText.setHorizontalAlignment(JLabel.CENTER);
	     promoText.setFont(new Font("Arial", Font.BOLD, 18));
	     promoText.setForeground(Color.WHITE);
	     gradientPanel.setLayout(new GridBagLayout());
	     gradientPanel.add(promoText);
	     
	     
//	     promotionPanel.add(gradientPanel,BorderLayout.CENTER);
		return gradientPanel;
	     
	    		
	}

	//below id menuBar
	public JButton createStyledButton (String text)
	{
		JButton button=new JButton(text);
		button.setFont(new Font("Arial",Font.PLAIN,14));
		button.setForeground(new Color( 51,51,51));
		button.setBackground(Color.white);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setFont(new Font("Arial",Font.PLAIN,14));
				button.setForeground(new Color(255, 51, 85));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				button.setFont(new Font("Arial",Font.PLAIN,14));
				button.setForeground(new Color(51, 51, 51));
				;
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (text) {
	               case "Pay Bills":
	                   SwingUtilities.invokeLater(new Runnable() {
	                       public void run() {
	                           try {
	                               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                           } catch (Exception ex) {
	                               ex.printStackTrace();
	                           }
	                           
	                           new PaymentGUI(accountNo).setVisible(true);
	                       }
	                   });
	                   break;
	               case "Service Requests":
	                   if (User_DashBoard.status == "INACTIVE")
	                   {
	                       JOptionPane.showMessageDialog(User_DashBoard.this,"for service request you need first buy connection ","Failed",JOptionPane.INFORMATION_MESSAGE);
	                   } else {
	                       try {
	                           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                           new My_Service_Request();
	                       } catch (Exception ex) {
	                           ex.printStackTrace();
	                       }
	                       
//	                       
//	                       if(status=="INACTIVE")
//	                       {
//	                    	   new ServiceRequest();
//	                       }else {
//	                    	   JOptionPane.showMessageDialog(User_DashBoard.this,"for service request you need first buy connection ","Error",JOptionPane.ERROR_MESSAGE);
//	                       }
	                      
	                   }
	                   break;
	               case "New Connection":
	                   try {
	                       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                   } catch (ClassNotFoundException | InstantiationException |
	                           IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                       ex.printStackTrace();
	                   }
	                   new BuyConnection();
	                   break;
	               case "FAQ":
	            	   new FAQFrame();
	                   
	                   break;
	           }
			}
		});
		return  button;
	}
	
	
	
	public JPanel createHeader()
	{
		
		JPanel headerPanel=new JPanel();
		headerPanel.setLayout(new BorderLayout());
		
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		JLabel titleLabel=new JLabel("Nvidia Fibernet");
		titleLabel.setForeground(new Color(255,51,85));
		titleLabel.setFont(new Font("Arial",Font.BOLD,24));
		
		JPanel navMenu=new JPanel();
		navMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		navMenu.setBackground(Color.WHITE);
		
		String [] menuItems= {"Pay Bills","Service Requests","New Connection","FAQ"};
		
		for(String items:menuItems)
		{
			JButton menuButton=createStyledButton(items);
			navMenu.add(menuButton);
		}
		
		JPanel dropDownPanel=new JPanel();
		dropDownPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dropDownPanel.setBackground(Color.WHITE);
		
		JButton dropdownButton=new JButton("Options");
		dropdownButton.setFont(new Font("Arial",Font.PLAIN,14));
		dropdownButton.setForeground(new Color(51,51,51));
		dropdownButton.setBackground(Color.WHITE);
		dropdownButton.setFocusPainted(false);
//		dropdownButton.setBorderPainted(false);
		dropdownButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JPopupMenu menus=new JPopupMenu();
		JMenuItem profileOption=new JMenuItem("Profile");
		JMenuItem logoutOption=new JMenuItem("Logout");
		
		
		profileOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new User_Profile();
				
			}
		});
		logoutOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice=JOptionPane.showConfirmDialog(null, "Are you sure want to logout","Logout", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				
				if(choice==JOptionPane.YES_OPTION)
				{
					dispose();
					new Home();
				}
			}
		});
		
		menus.add(profileOption);
		menus.add(logoutOption);
		
		dropdownButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            menus.show(dropdownButton, 0, dropdownButton.getHeight());
	        }
	    });
		
		dropDownPanel.add(dropdownButton);
		
		headerPanel.add(titleLabel,BorderLayout.WEST);
		headerPanel.add(navMenu,BorderLayout.CENTER);
		headerPanel.add(dropDownPanel,BorderLayout.EAST);
		
		return headerPanel;
	}
	
	public static void main(String[] args) {
		try {
			new User_DashBoard();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public JPanel createGraphPanel(JPanel data,JPanel data2) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

		    // Handling buyDate
		    if (buyDate == null || buyDate.trim().isEmpty()) {
		        System.err.println("Error: buyDate is null or empty!");
		        buyDate = "01 Jan 2024";  // Default fallback date
		    }

		    LocalDate buyDateParsed;
		    try {
		        buyDateParsed = LocalDate.parse(buyDate, formatter);
		    } catch (Exception e) {
		        System.err.println("Invalid buyDate format: " + buyDate);
		        buyDateParsed = LocalDate.now();  // Use today's date if parsing fails
		    }

		    // Calculate used days
		    long usedDays = ChronoUnit.DAYS.between(buyDateParsed, LocalDate.now());

		    // Handling planDuration (Extract numeric value from "30 Days")
		    long planDuration1 = 30; // Default 30 days if parsing fails
		    try {
		        planDuration1 = Long.parseLong(planDuration.replaceAll("\\D+", ""));
		    } catch (Exception e) {
		        System.err.println("Invalid planDuration: " + planDuration);
		    }

		    // Calculate remaining days
		    long remainingDays = planDuration1 - usedDays;
		    if (remainingDays < 0) remainingDays = 0; // Prevent negative values

		    // Create dataset for Pie Chart
		    DefaultPieDataset dataset = new DefaultPieDataset();
		    dataset.setValue("Days Used", usedDays);
		    dataset.setValue("Days Remaining", remainingDays);

		    // Create Pie Chart with anti-aliasing for high-quality rendering
		    JFreeChart pieChart = ChartFactory.createPieChart(
		            "Subscription Duration", dataset, true, true, false);
		    
		    pieChart.setAntiAlias(true); // Enable smooth rendering
		    pieChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18)); // Increase title font size

		    // Customize Pie Chart appearance
		    PiePlot plot = (PiePlot) pieChart.getPlot();
		    plot.setSectionPaint("Days Used "+usedDays, new Color(255, 102, 0)); // Brighter orange
		    plot.setSectionPaint("Days Remaining "+remainingDays, new Color(0, 102, 204)); // Darker blue for contrast
		    plot.setSimpleLabels(true);
		    plot.setLabelFont(new Font("Arial", Font.BOLD, 14)); // Increase label font size
		    plot.setBackgroundPaint(Color.WHITE); // Set background color to white for clarity
		    plot.setOutlineVisible(false); // Remove border outline for better aesthetics

		    // Wrap in JPanel
		    ChartPanel chartPanel = new ChartPanel(pieChart);
		    chartPanel.setPreferredSize(new Dimension(500, 500)); // Increase resolution

		    JPanel panel = new JPanel(new BorderLayout());
	
		    panel.add(chartPanel, BorderLayout.CENTER);

		    return panel;
	}
}
