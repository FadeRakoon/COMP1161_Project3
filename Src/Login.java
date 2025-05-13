import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

/**
 * A simple login window
 */
public class Login extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    public static boolean loginSuccessful;
    private Dimension Psize = new Dimension(350,200);
    /**
     * Constructor to set up the login window
     */
    public Login() {
        // Set up the frame properties

        super((Frame) null, "Login", true);
        
        setTitle("Login");
        setSize(350,200);
        setPreferredSize(Psize);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a panel with a simple layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        
        // Add username field
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        panel.add(usernameField);
        
        // Add password field
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        panel.add(passwordField);
        
        // Add message label for errors
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel);
        
        // Add login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton);
        
        // Add cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());
        panel.add(cancelButton);
        
        // Add empty label (for layout)
        panel.add(new JLabel(""));
        
        // Add the panel to the frame
        getContentPane().add(panel);
        // setVisible(true);
        pack();
    }
    
    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            // Check credentials
            if (checkLogin(username, password)) {
                loginSuccessful = true;
                dispose(); // Close the window
            } else {
                messageLabel.setText("Invalid login credentials.");
                passwordField.setText(""); // Clear password field
            }
        }
    }
    
    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginSuccessful = false;
            dispose(); // Close the window
        }
    }
    
    /**
     *Checks if the provided credentials are valid
     */
    private boolean checkLogin(String username, String password) {
        try {
            File file = new File("login.dat");
            
            // Create default login if file doesn't exist
            if (!file.exists()) {
                createDefaultLoginFile();
            }
            
            // Read credentials from file
            Scanner scanner = new Scanner(file);
            String storedUsername = "";
            String storedPassword = "";
            
            if (scanner.hasNextLine()) {
                storedUsername = scanner.nextLine();
            }
            if (scanner.hasNextLine()) {
                storedPassword = scanner.nextLine();
            }
            scanner.close();
            
            // Compare credentials
            return username.equals(storedUsername) && password.equals(storedPassword);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Creates a default login file with credentials
     */
    private void createDefaultLoginFile() {
        try {
            FileWriter writer = new FileWriter("login.dat");
            writer.write("CookieMonsta\n");
            writer.write("ILuvCookies\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating login file: " + e.getMessage());
        }
    }
}