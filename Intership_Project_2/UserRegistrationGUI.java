package Intership_Project_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UserRegistrationGUI extends JFrame {

	JTextField userNameField, mobileNumberFiled;

	JButton signupButton, signinButton;

	JComboBox<String> city;

	JCheckBox termsBox;

	public UserRegistrationGUI() {
		setUpFrame();
		initalizeComponents();
		addComponent();
		setUpListeners();
		setVisible(true);

	}

	private void addComponent() {

		JLabel title = new JLabel("User Registration");
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setForeground(Color.white);
		title.setAlignmentX(CENTER_ALIGNMENT);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);

		mainPanel.add(Box.createVerticalStrut(260));
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(title);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setOpaque(false);
		formPanel.setAlignmentX(CENTER_ALIGNMENT);

		addFormRow("User Name", userNameField, formPanel);
		addFormRow("Mobile Number", mobileNumberFiled, formPanel);
		addFormRow("City", city, formPanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		formPanel.add(termsBox);

		mainPanel.add(formPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
		buttonPanel.setOpaque(false);
		buttonPanel.add(signupButton);
		buttonPanel.add(signinButton);

		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalGlue());

		add(mainPanel);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

			}
		});

	}

	private void showError(String msg) {

		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void initalizeComponents() {

		userNameField = createStyledTextField(20);

		mobileNumberFiled = createStyledTextField(20);

		String[] cities = { "Select Location", "Austin", "Texas", "Raleigh", "North Corolina", "Missouri","NewYork city","Manhattan" };

		city = new JComboBox<String>(cities);

		createStyledComboBox(city);

		termsBox = new JCheckBox("Agree Term and Conditions");

		createStyledCheckBox(termsBox);

		signupButton = createStyledButton("Sign-Up", new Color(30, 144, 255));

		signinButton = createStyledButton("Sign-In", new Color(220, 20, 60));

	}

	private void addFormRow(String labletext, JComponent componant, JPanel formepanal) {

		JPanel rowpanal = new JPanel();
		rowpanal.setLayout(new BoxLayout(rowpanal, BoxLayout.X_AXIS));
		rowpanal.setOpaque(false);

		JLabel lable = new JLabel(labletext);
		lable.setForeground(Color.WHITE);
		lable.setFont(new Font("Arial", Font.PLAIN, 14));
		lable.setPreferredSize(new Dimension(120, 25));
		rowpanal.add(lable);

		rowpanal.add(Box.createRigidArea(new Dimension(10, 0)));
		rowpanal.add(componant);

		formepanal.add(rowpanal);
		formepanal.add(Box.createVerticalStrut(50));

		rowpanal.add(Box.createRigidArea(new Dimension(0, 10)));

	}

	private void createStyledCheckBox(JCheckBox boxx) {

		JCheckBox box = new JCheckBox();
		box.setForeground(Color.white);
		box.setFont(new Font("Arial", Font.PLAIN, 14));
		box.setFocusPainted(false);
		box.setOpaque(false);

	}

	private void createStyledComboBox(JComboBox<String> city) {

		city.setFont(new Font("Arial", Font.BOLD, 18));
		city.setBackground(Color.white);
		city.setForeground(Color.black);
	}

	private JButton createStyledButton(String text, Color background) {

		JButton button = new JButton(text);
		button.setBackground(background);
		button.setForeground(Color.black);
		button.setFont(new Font("Arial", Font.BOLD, 15));

		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createEmptyBorder(12, 25, 12, 25)));

		button.setOpaque(true);
		button.setFocusPainted(false);
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(background.brighter());

			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(background);
			}

		});
		button.setAlignmentX(CENTER_ALIGNMENT);

		return button;
	}

	private JTextField createStyledTextField(int col) {

		JTextField field = new JTextField(col);
		field.setBackground(new Color(255, 255, 255));
		field.setForeground(new Color(33, 33, 33));
		field.setFont(new Font("Arial", Font.PLAIN, 24));
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));

		return field;
	}

	private JPanel createBackground() {

		return new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {

				ImageIcon icon = new ImageIcon(getClass().getResource("/icons/nvidiabackground.jpg"));

				Image image = icon.getImage();

				double panalwidth = getWidth();

				double panalhight = getHeight();

				double imagewidh = image.getWidth(this);

				double imagehight = image.getHeight(this);

				double scale = (Math.max(panalwidth / imagewidh, panalhight / imagehight));

				int scaledWidth = ((int) (imagewidh * scale));

				int scaledHight = ((int) (imagehight * scale));

				int p = ((int) (panalwidth - scaledWidth) / 2);

				int q = ((int) (panalhight - scaledHight) / 2);

				g.drawImage(image, p, q, scaledWidth, scaledHight, this);

				g.setColor(new Color(0, 0, 0, 100));

				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
	}

	private void setUpFrame() {

		JFrame frame = new JFrame();
		setSize(1366, 768);
		setContentPane(createBackground());

		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void setUpListeners() {

		signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				handleSignUp();

			}
		});
		
		signinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				handleSignin();

			}

			
		});

	}
	
	private void handleSignin() {
		new SigninGUI() ;
		dispose();
		
	}

	private void handleSignUp() {

		String userName = userNameField.getText();
		String mobileNumber = mobileNumberFiled.getText();
		long phno;
		String loc = (String) city.getSelectedItem();

		if (mobileNumber.isEmpty()) {
			phno = 0;
		} else {
			phno = Long.parseLong(mobileNumber);
		}

		if (phno == 0 || userName.isEmpty() || loc.equals("Select Designation")) {
			showError("Fill the required Fields ");
			return;
		}

		if (!termsBox.isSelected()) {
			showError("Agree terms and Conditions");
			return;
		}


		String query = "INSERT INTO nvidia_user_sigin (mobileNumber, userName, city) VALUES (" 
	              + phno + ", '" + userName + "', '" + loc + "');";


		ConnnectionJDBC con = new ConnnectionJDBC();

		try {
			con.s.executeUpdate(query);

			JOptionPane.showMessageDialog(this, "Account Created Successfully ", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {

			showError("invalid Credentilas ");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new UserRegistrationGUI();

	}

}