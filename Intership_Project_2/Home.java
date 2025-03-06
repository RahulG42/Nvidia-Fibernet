package Intership_Project_2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Home extends JFrame {

	private JButton existingCustomer, newConnection, adminSignIn;

	public Home() {
		setUpFrame();
		initilizeComponent();
		addComponent();
		setUpListener();
	}

	private void addComponent() {

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlePanel.setOpaque(false);

		JLabel title = new JLabel("Welcome To Nvidia-Fibernet");
		title.setFont(new Font("Arial", Font.BOLD, 40));
		title.setForeground(Color.white);

		titlePanel.add(title);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);

		mainPanel.add(Box.createVerticalStrut(100));
		mainPanel.add(titlePanel);
		mainPanel.add(Box.createVerticalStrut(0));

		JPanel buttonPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(0, 0, 0, 100));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
			}
		};
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);

		buttonPanel.add(Box.createVerticalStrut(15));
		buttonPanel.add(existingCustomer);
		buttonPanel.add(Box.createVerticalStrut(15));
		buttonPanel.add(newConnection);
		buttonPanel.add(Box.createVerticalStrut(15));
		buttonPanel.add(adminSignIn);
		buttonPanel.add(Box.createVerticalStrut(15));

		mainPanel.add(buttonPanel);

		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(mainPanel);
		contentPane.add(Box.createVerticalGlue());
		contentPane.setOpaque(false);

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

	public void setUpListener() {

		existingCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleExistingCustomer();
			}

		});

		newConnection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handlenewConnection();
			}

		});

		adminSignIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleAdminSignIn();
			}

		});

	}

	private void handleExistingCustomer() {
		int choice = JOptionPane.showConfirmDialog(this, "Are you Existing User ", "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (choice == JOptionPane.YES_OPTION) {
			new SigninGUI();
			dispose();
		}
		else {
			new UserRegistrationGUI ();
		}
	}

	private void handlenewConnection() {
		int choice = JOptionPane.showConfirmDialog(this, "Want to join  ", "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (choice == JOptionPane.YES_OPTION) {
			new UserRegistrationGUI();
			dispose();
		}

	}

	private void handleAdminSignIn() {
			
		int choice = JOptionPane.showConfirmDialog(this, "Want to join  ", "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (choice == JOptionPane.YES_OPTION) {
			new Admin_SignIn();
			dispose();
		}

	}

	private JButton createStyledButton(String buttonTitle, Color color) {

		JButton button = new JButton(buttonTitle);
		button.setFont(new Font("Arial", Font.BOLD, 24));
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createEmptyBorder(12, 25, 12, 25)));
		button.setFocusPainted(false);
		button.setSize(300, 50);
		button.setBackground(color);
		;
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(color.brighter());

			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(color);
			}

		});
		button.setAlignmentX(CENTER_ALIGNMENT);

		return button;

	}

	private void initilizeComponent() {

		existingCustomer = createStyledButton("Existing Customer", new Color(30, 144, 255));

		newConnection = createStyledButton("New Connection", new Color(220, 20, 60));

		adminSignIn = createStyledButton("Admin Sign-in", new Color(50, 205, 50));

	}

	private JPanel createBackground() {

		return new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {

				ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.jpeg"));

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
		setVisible(true);
		setSize(1366, 768);
		setContentPane(createBackground());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {

		new Home();
	}
}