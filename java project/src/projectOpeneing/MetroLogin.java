package projectOpeneing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetroLogin extends JFrame implements ActionListener 
{
    private static final String CORRECT_PASSWORD = "controller2025";
    private JPasswordField passwordField;
    private JButton loginButton;
    private JTextField userField;

    public MetroLogin() 
    {
        setTitle("Metro Railway Limited - Controller Login");
        setSize(450, 250); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("Controller Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("User Role:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userField = new JTextField("Controller");
        userField.setEditable(false); // Lock the field as requested
        userField.setFont(new Font("Arial", Font.BOLD, 14));
        userField.setBackground(Color.LIGHT_GRAY);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(this);
        passwordField.addActionListener(this);
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        char[] passwordChars = passwordField.getPassword();
        String enteredPassword = new String(passwordChars);
        if (enteredPassword.equals(CORRECT_PASSWORD)) 
        {
            this.dispose(); 
            new ControllerDashboard();

        } 
        else 
        {
            JOptionPane.showMessageDialog(this,"Invalid Password. Please try again.","Login Error", JOptionPane.ERROR_MESSAGE );
            passwordField.setText("");
        }
        java.util.Arrays.fill(passwordChars, '0');
    }
    public static void main(String[] args) 
    {
    	//introduced to prevent gliches
        // Run the Swing application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MetroLogin();
            }
        });
        //lambda expression for the above
        // SwingUtilities.invokeLater(() -> new KochiMetroLogin());
    }
}
class ControllerDashboard extends JFrame {

    public ControllerDashboard() {
        setTitle("Metro - Controller Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Add a simple welcome message
        SelectOperations so=new SelectOperations();
        so.setVisible(true);
        this.dispose();
        
    }
}
