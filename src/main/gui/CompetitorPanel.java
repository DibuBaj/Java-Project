package main.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.component.CompetitionLevel;
import main.component.Competitor;
import main.component.Name;
import main.component.Question;





public class CompetitorPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel competitorIdField,nameField, usernameField, levelField, ageField, countryField;
    private JTable scoreTable;
    private JTextPane questionField;
    public Competitor competitor;
    private int currentQuestionIndex = 0;
    private int playerScore = 0;
    List<Question> questionlist;
    private List<Question> selectedQuestions = new ArrayList<>();
    private JRadioButton option1Btn, option2Btn, option3Btn, option4Btn;
    private ButtonGroup optionGroup;
    private JButton btnPlay, btnSubmitAnswer;
    private JPanel gamePanel, initialPanel;
    private JTable highestScoreTable;
    private DefaultTableModel highestTableModel;
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
        nameField.setBounds(288, 81, 185, 14);
        profile.add(nameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(186, 113, 70, 14);
        profile.add(usernameLabel);

        usernameField = new JLabel("");
        usernameField.setBounds(288, 113, 185, 14);
        profile.add(usernameField);

        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setBounds(186, 142, 46, 14);
        profile.add(levelLabel);

        levelField = new JLabel("");
        levelField.setBounds(288, 142, 91, 14);
        profile.add(levelField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(186, 179, 46, 14);
        profile.add(ageLabel);

        ageField = new JLabel("");
        ageField.setBounds(288, 179, 46, 14);
        profile.add(ageField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(186, 204, 60, 14);
        profile.add(countryLabel);

        countryField = new JLabel("");
        countryField.setBounds(288, 204, 100, 14);
        profile.add(countryField);
        

     // Define column names
     String[] columnNames = {"Score1", "Score2", "Score3", "Score4", "Score5", "Overall Score"};

     // Create table model with column names
     DefaultTableModel scoreTableModel = new DefaultTableModel(columnNames, 0);
     scoreTable = new JTable(scoreTableModel);

     // Wrap table inside JScrollPane to ensure headers are displayed
     JScrollPane scrollPane = new JScrollPane(scoreTable);
     scrollPane.setBounds(188, 252, 516, 47);
     profile.add(scrollPane);
     
     JButton logoutBtn = new JButton("Logout");
     logoutBtn.addActionListener(new ActionListener() {
     	public void actionPerformed(ActionEvent e) {
     		Login login = new Login();
     		login.setVisible(true);
     		dispose();
     	}
     });
     logoutBtn.setBounds(809, 11, 89, 23);
     profile.add(logoutBtn);
     
     JLabel competitorId = new JLabel("Competitor Id:");
     competitorId.setBounds(186, 56, 92, 14);
     profile.add(competitorId);
     
     competitorIdField = new JLabel("");
     competitorIdField.setBounds(288, 56, 185, 14);
     profile.add(competitorIdField);
     
     JScrollPane scrollPane_1 = new JScrollPane();
     scrollPane_1.setBounds(186, 330, 268, 96);
     profile.add(scrollPane_1);
     
     
     String[] highScoreLabel = {"Highest Score", "Game No."};

     highestTableModel = new DefaultTableModel(highScoreLabel, 0);

     highestScoreTable = new JTable(highestTableModel);

     scrollPane_1.setViewportView(highestScoreTable);

        


     JPanel Game = new JPanel();
     Game.setLayout(null);
     tabbedPane.addTab("Game", null, Game, null);
     
  

     // Overlay Panel (Game Panel) - Initially Hidden
     gamePanel = new JPanel();
     gamePanel.setBounds(0, 0, 944, 472); // Ensure full overlay
     gamePanel.setBackground(Color.WHITE); // Solid white overlay
     gamePanel.setOpaque(true); // Ensure background color is visible
     gamePanel.setLayout(null);
     gamePanel.setVisible(false); // Initially hidden
     Game.add(gamePanel);
     
     option1Btn = new JRadioButton("");
     option1Btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
     option1Btn.addActionListener(new ActionListener() {
     	public void actionPerformed(ActionEvent e) {
     	}
     });
     gamePanel.add(option1Btn);
     
     option2Btn = new JRadioButton("");
     option2Btn.addActionListener(new ActionListener() {
     	public void actionPerformed(ActionEvent e) {
     	}
     });
     option2Btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
     gamePanel.add(option2Btn);
     
     option3Btn = new JRadioButton("");
     option3Btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
     gamePanel.add(option3Btn);
     
     option4Btn = new JRadioButton("");
     option4Btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
     gamePanel.add(option4Btn);
     
     option1Btn.setBounds(140, 228, 203, 40);
     option2Btn.setBounds(526, 228, 203, 40);
     option3Btn.setBounds(140, 342, 203, 40);
     option4Btn.setBounds(526, 342, 203, 40);
     
 
     optionGroup = new ButtonGroup();
     optionGroup.add(option1Btn);
     optionGroup.add(option2Btn);
     optionGroup.add(option3Btn);
     optionGroup.add(option4Btn);
     
     btnSubmitAnswer = new JButton("Submit Answer");
     btnSubmitAnswer.setFont(new Font("Tahoma", Font.PLAIN, 18));
     btnSubmitAnswer.setBounds(345, 420, 180, 50);
     gamePanel.add(btnSubmitAnswer);

     
     questionField = new JTextPane();
     questionField.setFont(new Font("Tahoma", Font.PLAIN, 22));
     questionField.setBounds(140, 65, 602, 149);
     questionField.setFocusable(false);
     gamePanel.add(questionField);
     
  // Initial Panel (Visible at Start)
     initialPanel = new JPanel();
     initialPanel.setBounds(0, 0, 944, 472);
     initialPanel.setLayout(null);
     initialPanel.setVisible(true); // Make sure it's visible
     Game.add(initialPanel);
     

     JLabel quizTitle = new JLabel("Sport Quiz");
     quizTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
     quizTitle.setBounds(361, 11, 123, 31);
     initialPanel.add(quizTitle);

     btnPlay = new JButton("Play");
     btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 20));
     btnPlay.setBounds(345, 174, 180, 51);
     initialPanel.add(btnPlay);

     btnSubmitAnswer.addActionListener(e -> {
    	    btnSubmitAnswer.setEnabled(false); 

    	    String selectedAnswer = getSelectedAnswer();
    	    if (selectedAnswer == null) {
    	        JOptionPane.showMessageDialog(null, "Please select an answer!", "Warning", JOptionPane.WARNING_MESSAGE);
    	        btnSubmitAnswer.setEnabled(true); 
    	        return;
    	    }

    	    checkAnswer(selectedAnswer); 

    	    btnSubmitAnswer.setEnabled(true);
    	});

     

     btnPlay.addActionListener(e -> {
         initialPanel.setVisible(false);
         startGame();
         gamePanel.setVisible(true);
     });

        
        fetchDataFromDatabase(username);
        questionlist = fetchQuestion(competitor);
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
                    int id = rs.getInt("id");
                    String fullName = rs.getString("name");
                    int age = rs.getInt("age");
                    String levelStr = rs.getString("level");
                    String country = rs.getString("country");
                    String scoresStr = rs.getString("scores");

                    CompetitionLevel level = CompetitionLevel.valueOf(levelStr.toUpperCase());

                    // Parse name
                    String[] nameParts = fullName.split(" ");
                    String firstName = nameParts.length > 0 ? nameParts[0] : "";
                    String middleName = nameParts.length > 2 ? nameParts[1] : "";
                    String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

                    int[] scores = parseScores(scoresStr);
                    Name name = new Name(firstName, middleName, lastName);
                    competitor = new Competitor(id, name, level, country, age, scores);

                    // ✅ Set UI fields
                    competitorIdField.setText(String.valueOf(competitor.getId()));
                    nameField.setText(competitor.getName());
                    usernameField.setText(username);
                    levelField.setText(competitor.getLevel().toString());
                    ageField.setText(String.valueOf(competitor.getAge()));
                    countryField.setText(competitor.getCountry());

                    // ✅ Update GUI score table
                    updateScoreTable(scores);

                    // ✅ **Update highest score table on login**
                    updateHighestScoreTable(scores);

                    // ✅ Check play limit
                    int playedGames = 0;
                    for (int score : scores) {
                        if (score > 0) {
                            playedGames++;
                        }
                    }

                    // Disable play button only if 5 actual games have been played
                    if (playedGames >= 5) {
                        btnPlay.setEnabled(false);
                        btnPlay.setText("Limit Reached");
                    }
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

    private int[] parseScores(String scoresStr) {
        int[] scores = new int[5]; // Default 5 zeros

        if (scoresStr == null || scoresStr.trim().isEmpty()) {
            return scores; // Return default [0,0,0,0,0] if empty
        }

        // Split the comma-separated string and convert to int array
        String[] parts = scoresStr.split(",");
        for (int i = 0; i < parts.length && i < 5; i++) {
            try {
                scores[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                scores[i] = 0; // Handle invalid values safely
            }
        }
        return scores;
    }

    private void updateScoreTable(int[] scores) {
        String[] columnNames = {"Score1", "Score2", "Score3", "Score4", "Score5", "Overall Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scoreTable.setModel(model);

        // Ensure the table has 5 score slots + overall score
        for (int i = 0; i < 5; i++) {
            scoreTable.setValueAt(i < scores.length ? scores[i] : 0, 0, i);
        }

        // Calculate and set the overall score
        int overallScore = 0;
        for (int score : scores) {
            overallScore += score;
        }
        scoreTable.setValueAt(overallScore, 0, 5);
    }

    private void updateHighestScoreTable(int[] scores) {
        highestTableModel.setRowCount(0); // Clear previous data before updating

        List<int[]> scoreList = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > 0) { // Only consider non-zero scores
                scoreList.add(new int[]{scores[i], i + 1}); // Store score and game number
            }
        }

        // Sort the list in descending order based on scores
        scoreList.sort((a, b) -> Integer.compare(b[0], a[0])); // Descending order

        // Populate the table with sorted scores
        for (int[] score : scoreList) {
            highestTableModel.addRow(new Object[]{score[0], "Game " + score[1]});
        }
    }


    
    public List<Question> fetchQuestion(Competitor comp) {
        List<Question> questionList = new ArrayList<>();

        if (comp.getLevel() == null) {
            JOptionPane.showMessageDialog(null, "Error detecting question level.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return questionList;
        }

        String url = "jdbc:mysql://localhost:3306/competitiondb";
        String user = "root";
        String password = "";

        String sql = "SELECT * FROM question WHERE level = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, comp.getLevel().toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                        rs.getInt("id"),
                        rs.getString("level"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answer")
                    );
                    questionList.add(question);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching questions from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return questionList;
    }
    
    private void startGame() {
        int[] scores = competitor.getScoreArray();

        // Count non-zero scores to check if the player has played 5 times
        int playedGames = 0;
        selectedQuestions.clear();
        
        for (int score : scores) {
            if (score > 0) {
                playedGames++;
            }
        }

        if (playedGames >= 5) {
            JOptionPane.showMessageDialog(null, "You have already played 5 times!", "Game Over", JOptionPane.WARNING_MESSAGE);
            btnPlay.setEnabled(false);
            btnPlay.setText("Limit Reached");
            return; // 🚀 **Ensure game does NOT start**
        }

        if (questionlist == null || questionlist.size() < 5) {
            JOptionPane.showMessageDialog(null, "Not enough questions in the database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Shuffle and pick 5 random questions
        Collections.shuffle(questionlist, new Random());
        selectedQuestions = questionlist.subList(0, 5);

        currentQuestionIndex = 0;
        playerScore = 0;
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= selectedQuestions.size()) {
            endGame();
            return;
        }

        Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
        questionField.setText(currentQuestion.getQuestion());

        optionGroup.clearSelection(); // Ensure no previous selection remains
        option1Btn.setText(currentQuestion.getOption1());
        option2Btn.setText(currentQuestion.getOption2());
        option3Btn.setText(currentQuestion.getOption3());
        option4Btn.setText(currentQuestion.getOption4());

    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer == null) {
            JOptionPane.showMessageDialog(null, "Please select an answer!", "Warning", JOptionPane.WARNING_MESSAGE);
            return; // Stop execution if no answer is selected
        }

        Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
        if (selectedAnswer.equals(currentQuestion.getAnswer())) {
            playerScore += 1;
        }

        currentQuestionIndex++;
        displayQuestion();
    }


    private void endGame() {
        // Show final score message
        JOptionPane.showMessageDialog(null, "Game Over! Your score: " + playerScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Save updated score to database
        updateScoreInDatabase(playerScore);

        // Count how many games have been played
        int playedGames = 0;
        for (int score : competitor.getScoreArray()) {
            if (score > 0) {
                playedGames++;
            }
        }

        // 🚀 **Check if the limit (5 games) is reached BEFORE asking to play again**
        if (playedGames >= 5) {
            JOptionPane.showMessageDialog(null, "You have reached the maximum play limit (5 games).", "Limit Reached", JOptionPane.WARNING_MESSAGE);
            btnPlay.setEnabled(false);
            btnPlay.setText("Limit Reached");
            return; // ⛔ Stop further execution, no more games allowed
        }

        // 🟢 **Ask the user if they want to play again**
        int response = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
        	initialPanel.setVisible(false);
            startGame();
            gamePanel.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(null, "Thanks for playing!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            gamePanel.setVisible(false);
            initialPanel.setVisible(true);
        }

        // ✅ Update the UI with new scores
        updateScoreTable(competitor.getScoreArray());
        updateHighestScoreTable(competitor.getScoreArray());
        
    }



    private void updateScoreInDatabase(int newScore) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "")) {
            int[] scores = competitor.getScoreArray();

            // Find first empty slot (0) and insert the new score
            boolean updated = false;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] == 0) {
                    scores[i] = newScore;
                    updated = true;
                    break;
                }
            }

            // If no slot was found, alert user
            if (!updated) {
                JOptionPane.showMessageDialog(null, "No available slot to store score.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert scores array to a comma-separated string
            StringBuilder scoreString = new StringBuilder();
            for (int i = 0; i < scores.length; i++) {
                if (i > 0) {
                    scoreString.append(",");
                }
                scoreString.append(scores[i]);
            }

            // Update the database
            String sql = "UPDATE user SET scores = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, scoreString.toString());
                pstmt.setInt(2, competitor.getId());
                pstmt.executeUpdate();
            }

            // ✅ **Update the `competitor` object with new scores**
            competitor.setScoreArray(scores);

            // ✅ **Refresh the UI immediately**
            updateScoreTable(scores);
            updateHighestScoreTable(scores);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating score in database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getSelectedAnswer() {
        if (option1Btn.isSelected()) {
            return option1Btn.getText();
        } else if (option2Btn.isSelected()) {
            return option2Btn.getText();
        } else if (option3Btn.isSelected()) {
            return option3Btn.getText();
        } else if (option4Btn.isSelected()) {
            return option4Btn.getText();
        } else {
            return null; // No option selected
        }
    }
}
