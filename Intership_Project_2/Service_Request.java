package Intership_Project_2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Date;
import javax.swing.border.LineBorder;
import javax.swing.SpinnerDateModel;

public class Service_Request extends JFrame {
    private JPanel backgroundPanel;
    private JLabel titleLabel;
    private JTextField requestIdField, userField;
    private JComboBox<String> requestTypeDropdown;
    private JSpinner dateSpinner;
    private JButton submitButton, clearButton, backButton;

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Service_Request::new);
//    }

    public Service_Request() {
        setTitle("Nvidia Broadband - Service Request");
        setSize(960, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window initially

        // Create custom background panel
        backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(null); // Absolute positioning
        add(backgroundPanel);

        // Title
        titleLabel = new JLabel("Service Request Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel);

        // Form Fields
        requestIdField = new JTextField("60267", 20);
        userField = new JTextField(20);
        requestTypeDropdown = new JComboBox<>(new String[]{"Connection Issue", "Billing Issue", "Technical Support"});
        
        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        backgroundPanel.add(addFormField("Request ID:", requestIdField));
        backgroundPanel.add(addFormField("User:", userField));
        backgroundPanel.add(addFormField("Request Type:", requestTypeDropdown));
        backgroundPanel.add(addFormField("Date:", dateSpinner));

        // Buttons
        submitButton = createStyledButton("Submit Request");
        clearButton = createStyledButton("Clear Form");
        backButton = createStyledButton("Go Back");

        backgroundPanel.add(submitButton);
        backgroundPanel.add(clearButton);
        backgroundPanel.add(backButton);

        // Resize handling for dynamic centering
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                positionComponents();
            }
        });

        setVisible(true);
        positionComponents(); // Initial positioning
    }

    // Method to create a form row (label + input field)
    private JPanel addFormField(String labelText, JComponent field) {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false); // Transparent background

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        
        label.setBounds(0, 0, 120, 30);
        field.setBounds(140, 0, 400, 30);
        
        if (field instanceof JTextField || field instanceof JSpinner) {
            field.setBorder(new LineBorder(Color.BLACK, 1));
        }

        panel.add(label);
        panel.add(field);
        return panel;
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 120, 200));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    // Dynamically position components based on window size
    private void positionComponents() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        
        int formWidth = 600;
        int startY = frameHeight / 6; // Keep some space at the top
        
        titleLabel.setBounds((frameWidth - 400) / 2, startY, 400, 40);
        
        int fieldY = startY + 60;
        for (Component comp : backgroundPanel.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBounds((frameWidth - formWidth) / 2, fieldY, formWidth, 30);
                fieldY += 60;
            }
        }
        
        // Position buttons
        int buttonY = fieldY + 20;
        int buttonWidth = 180;
        int buttonSpacing = 20;
        
        submitButton.setBounds((frameWidth - (3 * buttonWidth + 2 * buttonSpacing)) / 2, buttonY, buttonWidth, 40);
        clearButton.setBounds(submitButton.getX() + buttonWidth + buttonSpacing, buttonY, buttonWidth, 40);
        backButton.setBounds(clearButton.getX() + buttonWidth + buttonSpacing, buttonY, buttonWidth, 40);
    }

    // Custom JPanel for gradient background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(10, 50, 120),  // Dark Blue (Top)
                    0, getHeight(), new Color(5, 20, 60)  // Darker Blue (Bottom)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
