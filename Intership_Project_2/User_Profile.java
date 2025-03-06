package Intership_Project_2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class User_Profile extends JFrame {

    private JTextField nameField, mobileField, cityField;
    private JButton updateButton;
    private static final Color BUTTON_COLOR = new Color(41, 100, 145);
    private static final Color BUTTON_HOVER_COLOR = new Color(41, 128, 185);
    private static final Color BUTTON_PRESS_COLOR = new Color(41, 128, 225);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(User_Profile::new);
    }

    public User_Profile() {
        setTitle("User Profile");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        initComponent();
        fetchAdminData();
        
        addComponentsToFrame();
        
        setVisible(true);
    }

    private void addComponentsToFrame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("User Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        addFormRow("Name", nameField, formPanel);
        addFormRow("Mobile Number", mobileField, formPanel);
        addFormRow("City", cityField, formPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(updateButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initComponent() {
        nameField = new JTextField(20);
        mobileField = new JTextField(20);
        cityField = new JTextField(20);
        updateButton = createStyledButton("Update User Profile");
    }

    private void addFormRow(String labelText, JTextField field, JPanel formPanel) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText + ": ");
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        rowPanel.add(label);
        rowPanel.add(field);
        formPanel.add(rowPanel);
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
                g2.setPaint(bgColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics metrics = g2.getFontMetrics();
                int textX = (getWidth() - metrics.stringWidth(getText())) / 2;
                int textY = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.drawString(getText(), textX, textY);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> updateUserProfile());
        return button;
    }

    private void updateUserProfile() {
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String city = cityField.getText();
        String query = "UPDATE nvidia_user_sigin SET userName = '" + name + "', mobileNumber = '" + mobile + "', city = '" + city + "' WHERE mobileNumber = '" + User_DashBoard.phoneNo + "'";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qspider", "root", "1857752002");
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "User Profile Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to Update User Profile", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void fetchAdminData() {
        String query = "SELECT mobileNumber, userName, city FROM nvidia_user_sigin WHERE mobileNumber ='" + User_DashBoard.phoneNo + "'";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qspider", "root", "1857752002");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                nameField.setText(rs.getString("userName"));
                mobileField.setText(rs.getString("mobileNumber"));
                cityField.setText(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
