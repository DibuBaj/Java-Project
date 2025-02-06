package main.gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import main.component.CompetitionLevel;
import main.component.Competitor;
import main.component.Name;

public class CompetitorPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel nameField, usernameField, levelField, ageField, countryField;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                CompetitorPanel frame = new CompetitorPanel(""); // Example username
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    /**
     * Create the frame and fetch user data.
     */
    public CompetitorPanel(String username) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 965, 539);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 949, 500);
        contentPane.add(tabbedPane);

        JPanel profile = new JPanel();
        tabbedPane.addTab("Profile", null, profile, null);
        profile.setLayout(null);

        JLabel fullNameLabel = new JLabel("Name:");
        fullNameLabel.setBounds(186, 81, 46, 14);
        profile.add(fullNameLabel);

        nameField = new JLabel("");
        nameField.setBounds(260, 81, 185, 14);
        profile.add(nameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(186, 113, 70, 14);
        profile.add(usernameLabel);

        usernameField = new JLabel("");
        usernameField.setBounds(260, 113, 185, 14);
        profile.add(usernameField);

        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setBounds(186, 142, 46, 14);
        profile.add(levelLabel);

        levelField = new JLabel("");
        levelField.setBounds(260, 142, 91, 14);
        profile.add(levelField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(186, 179, 46, 14);
        profile.add(ageLabel);

        ageField = new JLabel("");
        ageField.setBounds(260, 179, 46, 14);
        profile.add(ageField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(186, 204, 60, 14);
        profile.add(countryLabel);

        countryField = new JLabel("");
        countryField.setBounds(260, 204, 100, 14);
        profile.add(countryField);
        
        JPanel Game = new JPanel();
        tabbedPane.addTab("Game", null, Game, null);

        fetchDataFromDatabase(username);
    }

    /**
     * Fetch data from the database and set values in the UI.
     */
    private void fetchDataFromDatabase(String username) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "")) {
            String sql = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Extract user data
                    int id = rs.getInt("id");
                    String fullName = rs.getString("name");
                    int age = rs.getInt("age");
                    String levelStr = rs.getString("level");
                    String country = rs.getString("country");

                    // Convert level string to Enum
                    CompetitionLevel level = CompetitionLevel.valueOf(levelStr.toUpperCase());

                    // Parse name (Assuming format "First Middle Last")
                    String[] nameParts = fullName.split(" ");
                    String firstName = nameParts.length > 0 ? nameParts[0] : "";
                    String middleName = nameParts.length > 2 ? nameParts[1] : "";
                    String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

                    // Keep scores empty
                    int[] scores = new int[0]; // Empty array

                    // Create Name object
                    Name name = new Name(firstName, middleName, lastName);

                    // Create Competitor object
                    Competitor competitor = new Competitor(id, name, level, country, age, scores);

                    // Set UI fields
                    nameField.setText(competitor.getName());
                    usernameField.setText(username);
                    levelField.setText(competitor.getLevel().toString());
                    ageField.setText(String.valueOf(competitor.getAge()));
                    countryField.setText(competitor.getCountry());
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
