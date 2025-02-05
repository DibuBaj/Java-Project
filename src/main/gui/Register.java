package main.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.component.CompetitionLevel;

import javax.swing.JComboBox;

public class Register extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JTextField ageField;
    private JPasswordField passwordField;
    private JTextField countryField;
    private JLabel level;
    private JComboBox<CompetitionLevel> levelField;
    private JButton backBtn;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Register frame = new Register();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 794, 529);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        firstNameLabel.setBounds(55, 30, 150, 31);
        contentPane.add(firstNameLabel);

        JLabel middleNameLabel = new JLabel("Middle Name(optional):");
        middleNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        middleNameLabel.setBounds(55, 80, 217, 31);
        contentPane.add(middleNameLabel);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lastNameLabel.setBounds(55, 130, 150, 31);
        contentPane.add(lastNameLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        usernameLabel.setBounds(55, 182, 150, 31);
        contentPane.add(usernameLabel);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ageLabel.setBounds(55, 270, 150, 31);
        contentPane.add(ageLabel);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        countryLabel.setBounds(55, 312, 150, 31);
        contentPane.add(countryLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordLabel.setBounds(55, 350, 150, 31);
        contentPane.add(passwordLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(300, 30, 289, 31);
        contentPane.add(firstNameField);
        firstNameField.setColumns(10);

        middleNameField = new JTextField();
        middleNameField.setBounds(300, 80, 289, 31);
        contentPane.add(middleNameField);
        middleNameField.setColumns(10);

        lastNameField = new JTextField();
        lastNameField.setBounds(300, 130, 289, 31);
        contentPane.add(lastNameField);
        lastNameField.setColumns(10);

        usernameField = new JTextField();
        usernameField.setBounds(300, 182, 289, 31);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        ageField = new JTextField();
        ageField.setBounds(300, 270, 289, 31);
        contentPane.add(ageField);
        ageField.setColumns(10);

        countryField = new JTextField();
        countryField.setBounds(300, 312, 289, 31);
        contentPane.add(countryField);
        countryField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 353, 289, 32);
        contentPane.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        registerBtn.setBounds(242, 432, 150, 40);
        contentPane.add(registerBtn);
        
        level = new JLabel("Level:");
        level.setFont(new Font("Tahoma", Font.PLAIN, 20));
        level.setBounds(55, 224, 150, 31);
        contentPane.add(level);
        
        levelField = new JComboBox<>(CompetitionLevel.values());
        levelField.setBounds(300, 228, 289, 31);
        contentPane.add(levelField);
        
        backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LandingPage lp = new LandingPage();
        		lp.setVisible(true);
        		dispose();
        	}
        });
        backBtn.setBounds(461, 432, 150, 40);
        contentPane.add(backBtn);
    }

    private void registerUser() {
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String age = ageField.getText();
        String country = countryField.getText();
        String password = new String(passwordField.getPassword());
        CompetitionLevel level = (CompetitionLevel) levelField.getSelectedItem();
        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
            age.isEmpty() || country.isEmpty() || password.isEmpty() || level == null) {
            JOptionPane.showMessageDialog(null, "All other fields must be filled.", "Register", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Combine names
        String fullName = middleName.isEmpty() ? firstName + " " + lastName : firstName + " " + middleName + " " + lastName;

        try {
        	 // Establish connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");

            // Reset AUTO_INCREMENT if needed
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("ALTER TABLE user AUTO_INCREMENT = 1");
            stmt.close();

            // Insert data
            String sql = "INSERT INTO user (name, age,level, country, scores, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullName);
            pstmt.setInt(2, Integer.parseInt(age));
            pstmt.setString(3,level.name());
            pstmt.setString(4, country);
            pstmt.setString(5, "[]"); 
            pstmt.setString(6, username);
            pstmt.setString(7, password);
            pstmt.executeUpdate();

            System.out.println("Data Inserted Successfully.");

            // Close connections
            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Sign Up Successful.", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error during registration.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }
}
