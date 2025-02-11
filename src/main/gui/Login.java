package main.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
        setBounds(100, 100, 1067, 590);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Tahoma", Font.PLAIN, 20));
        username.setBounds(208, 147, 113, 25);
        contentPane.add(username);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        password.setBounds(208, 225, 113, 25);
        contentPane.add(password);

        usernameField = new JTextField();
        usernameField.setBounds(398, 143, 309, 41);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField(); // Changed from JTextField to JPasswordField
        passwordField.setBounds(398, 221, 309, 41);
        contentPane.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener((ActionEvent e) -> {
            String usernameInput = usernameField.getText();
            String passwordInput = new String(passwordField.getPassword()); // Securely retrieve password

            // Basic validation
            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this, "Please enter both username and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(usernameInput.equals("admin") && passwordInput.equals("admin") ) {
            	JOptionPane.showMessageDialog(Login.this, "Admin Login Successful!", "Admin Login", JOptionPane.INFORMATION_MESSAGE);
            	AdminPanel adminpal = new AdminPanel(false);
            	adminpal.setVisible(true);
            	dispose();
            } else {
            	 // Database authentication
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
                    String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, usernameInput);
                    pstmt.setString(2, passwordInput);

                    // Execute the query
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        // Login successful
                        JOptionPane.showMessageDialog(Login.this, "Login Successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                        CompetitorPanel cp = new CompetitorPanel(usernameInput);
                        cp.setVisible(true);
                         dispose();
                    } else {
                        // Invalid credentials
                        JOptionPane.showMessageDialog(Login.this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close resources
                    rs.close();
                    pstmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Login.this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
           
        });

        loginBtn.setBounds(332, 318, 150, 40);
        contentPane.add(loginBtn);
        
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LandingPage lp = new LandingPage();
        		lp.setVisible(true);
        		dispose();
        	}
        });
        backBtn.setBounds(633, 318, 150, 40);
        contentPane.add(backBtn);
    }
}
