import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JFrame {

    public Splash() {
        // Set JFrame properties
        setTitle("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLocation(50, 50);

        // Create a panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a label for the heading
        JLabel headingLabel = new JLabel("Welcome To Business Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("MonoType Corsiva", Font.PLAIN, 30));
        headingLabel.setBounds(400,100, 500, 100 );
        mainPanel.add(headingLabel);

        // Create a panel for buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    } //main Constructor
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // 3 rows, 1 column, vertical gap 10
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(220, 480, 220, 480)); // Add padding

        // Create and add buttons
        JButton adminButton = createButton("Admin");
        JButton managerButton = createButton("Manager");
        JButton employeeButton = createButton("Employee");

        // Add buttons to the panel
        buttonPanel.add(adminButton);
        buttonPanel.add(managerButton);
        buttonPanel.add(employeeButton);

        return buttonPanel;
    }// Buttons Panel
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
        button.setMaximumSize(new Dimension(100, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        button.setBorder(new RoundedBorder(15));
        button.setBackground(new Color(173, 216, 230));
        button.addActionListener(new ButtonClickListener());
        return button;
    }//Create Button
    static class RoundedBorder implements Border {
        private  final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }//Static Method for Making Designing and Styling Buttons
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle button clicks (replace with actual login functionality)
            String sourceButton = ((JButton) e.getSource()).getText();
            if("Admin".equals(sourceButton)){
                SwingUtilities.invokeLater(() -> {
                    setVisible(false);
                    new Login().setVisible(true);
                });
            }else if("Manager".equals(sourceButton)){
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    setVisible(false);
                    new ManagerLogin().setVisible(true);
                });

            }else{
                SwingUtilities.invokeLater(() -> {
                    setVisible(false);
                    new EmployeeLogin().setVisible(true);
                });

            }
        }
    }// Action Performed on CLick of Each Button
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Splash().setVisible(true));
    }
}
