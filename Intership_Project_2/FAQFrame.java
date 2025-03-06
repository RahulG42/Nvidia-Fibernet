package Intership_Project_2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FAQFrame extends JFrame {
    
    public FAQFrame() {
        setTitle("Frequently Asked Questions");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Scrollable FAQ panel
        JPanel faqPanel = new JPanel();
        faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
        faqPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add FAQ content
        addFAQ(faqPanel, "Q1: How do I generate a bill?", "    Go to the Bill Generator section and enter the required details.");
        addFAQ(faqPanel, "Q2: How can I update my profile?", "     Click on the Profile section and edit your details.");
        addFAQ(faqPanel, "Q3: How to view account details?", 
                         "Click on the Account Details button.");
        addFAQ(faqPanel, "Q4: What should I do if I face issues?", 
                         "Contact support via the Service Request section.");
        addFAQ(faqPanel, "Q5: How do I reset my password?", 
                         "Go to the Profile section and select 'Change Password'.");
        addFAQ(faqPanel, "Q6: Can I access my service request history?", 
                         "Yes, click on 'Service Request' to view your past requests.");
        addFAQ(faqPanel, "Q7: How can I contact customer support?", 
                         "Go to the Help section and find the contact details.");
        addFAQ(faqPanel, "Q8: How do I log out?", 
                         "Click on the 'Logout' button at the top right corner.");
        addFAQ(faqPanel, "Q9: What payment methods are accepted?", 
                         "You can pay using Credit/Debit cards, Net Banking, and UPI.");
        addFAQ(faqPanel, "Q10: How do I check my current plan details?", 
                         "Click on 'Account Details' to view your plan details.");
        addFAQ(faqPanel, "Q11: Can I change my subscription plan?", 
                         "Yes, go to 'Account Details' and select 'Change Plan'.");
        addFAQ(faqPanel, "Q12: What should I do if my bill amount is incorrect?", 
                         "Raise a dispute through 'Bill Generator' or contact support.");
        addFAQ(faqPanel, "Q13: Can I download my billing history?", 
                         "Yes, in the 'Bill Generator' section, click 'Download Bill'.");
        addFAQ(faqPanel, "Q14: How do I update my registered email or phone number?", 
                         "Go to 'Profile' and update your contact details.");
        addFAQ(faqPanel, "Q15: How do I delete my account permanently?", 
                         "Please contact customer support to request account deletion.");
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(faqPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Close");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close FAQ window
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    // Method to add a question and answer to the panel
    private void addFAQ(JPanel panel, String question, String answer) {
        JLabel questionLabel = new JLabel("<html><b>" + question + "</b></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel answerLabel = new JLabel("<html><p style='width:500px;'>" + answer + "</p></html>");
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(questionLabel);
        panel.add(answerLabel);
        panel.add(Box.createVerticalStrut(10)); // Space between FAQs
    }

    public static void main(String[] args) {
        new FAQFrame(); // Open FAQ window
    }
}
