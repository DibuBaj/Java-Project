package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.component.CompetitionLevel;
import main.component.Competitor;
import main.component.Name;

public class AdminPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField questionField;
	private JTextField answerField;
	private JTextField option1Field;
	private JTextField option2Field;
	private JTextField option3Field;
	private JTextField option4Field;
	private JComboBox<CompetitionLevel> levelField;

	
	String[] columnNames = { "ID", "Level", "Question", "Option1", "Option2", "Option3", "Option4", "Answer" };
	private JTable viewAllTable;
	private JTable beginnerTable;
	private JTable intermediateTable;
	private JTable advanceTable;
	private JTable allCompetitorTable;
	private JTextField searchField;
	private JLabel nameField, levelFieldComp ,ageField ,countryField;
	private JTable table_1;
	private JTable table_2;
	private JLabel bestPerformer;
	private void loadQuestions() {
		
		try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
	        String sql = "SELECT * FROM question";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();

	        // Create the models *with column names* and 0 rows:
	        DefaultTableModel allModel = new DefaultTableModel(columnNames, 0) { // Correct order
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        DefaultTableModel beginnerModel = new DefaultTableModel(columnNames, 0) { // Correct order
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        DefaultTableModel intermediateModel = new DefaultTableModel(columnNames, 0) { // Correct order
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        DefaultTableModel advanceModel = new DefaultTableModel(columnNames, 0) { // Correct order
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("id"),
	                rs.getString("level"),
	                rs.getString("question"),
	                rs.getString("option1"),
	                rs.getString("option2"),
	                rs.getString("option3"),
	                rs.getString("option4"),
	                rs.getString("answer")
	            };

	            String level = rs.getString("level");
	            allModel.addRow(row); // Add to allModel *first*

	            if (level.equalsIgnoreCase("BEGINNER")) {
	                beginnerModel.addRow(row);
	            } else if (level.equalsIgnoreCase("INTERMEDIATE")) {
	                intermediateModel.addRow(row);
	            } else if (level.equalsIgnoreCase("ADVANCED")) {
	                advanceModel.addRow(row);
	            }
	        }

	        // Assign models to JTables.  This is crucial!
	        viewAllTable.setModel(allModel);
	        beginnerTable.setModel(beginnerModel);
	        intermediateTable.setModel(intermediateModel);
	        advanceTable.setModel(advanceModel);

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error loading questions.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	public void loadCompetitor() {
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
	        String sql = "SELECT * FROM user";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();

	        // Define column names for competitors (without Total Score)
	        String[] competitorColumn = {"ID", "Name", "Age", "Level", "Country", "Score1", "Score2", "Score3", "Score4", "Score5"};

	        // Create table model with column names and non-editable rows
	        DefaultTableModel competitorModel = new DefaultTableModel(competitorColumn, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        System.out.println("=== Loading All Competitors from Database ===");

	        // Populate the table with data from the database
	        while (rs.next()) {
	            // Retrieve competitor details
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            int age = rs.getInt("age");
	            String level = rs.getString("level");
	            String country = rs.getString("country");
	            String scoreString = rs.getString("scores");
	            String[] scoreArray = scoreString.split(",");

	            // Convert score strings to integers, ensuring exactly 5 scores
	            int[] scores = new int[5];
	            for (int i = 0; i < 5; i++) {
	                try {
	                    scores[i] = (i < scoreArray.length) ? Integer.parseInt(scoreArray[i].trim()) : 0;
	                } catch (NumberFormatException e) {
	                    scores[i] = 0; // Default to 0 if parsing fails
	                }
	            }

	            // Create a row with all competitor data (excluding total score)
	            Object[] row = {id, name, age, level, country, scores[0], scores[1], scores[2], scores[3], scores[4]};

	            // Print full competitor details to the console
	            System.out.println("ID: " + id + " | Name: " + name + " | Age: " + age + " | Level: " + level + " | Country: " + country);
	            System.out.println("Scores: [" + scores[0] + ", " + scores[1] + ", " + scores[2] + ", " + scores[3] + ", " + scores[4] + "]");
	            System.out.println("------------------------------------------------");

	            // Add row to the table model
	            competitorModel.addRow(row);
	        }

	        // Assign model to JTable
	        allCompetitorTable.setModel(competitorModel);

	        System.out.println("=== Finished Loading Competitors ===");

	        // Close resources
	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error loading competitors.", "Error", JOptionPane.ERROR_MESSAGE);
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
	
	public Competitor searchCompetitorById(int compId) {
	    Competitor competitor = null; // Default to null if not found

	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
	        String sql = "SELECT * FROM user WHERE id = ?"; // Search by ID, not username
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, compId);
	        ResultSet rs = pstmt.executeQuery();

	        // Ensure there's a result before accessing data
	        if (rs.next()) {
	            int id = rs.getInt("id");
	            String fullName = rs.getString("name");
	            int age = rs.getInt("age");
	            String levelStr = rs.getString("level");
	            String country = rs.getString("country");
	            String scoreString = rs.getString("scores");

	            // Convert level to Enum
	            CompetitionLevel level = CompetitionLevel.valueOf(levelStr.toUpperCase());

	            // Parse name into first, middle, last
	            String[] nameParts = fullName.split(" ");
	            String firstName = nameParts.length > 0 ? nameParts[0] : "";
	            String middleName = nameParts.length > 2 ? nameParts[1] : "";
	            String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";
	            int[] scores = parseScores(scoreString);
	            
	            // Create the Name object
	            Name name = new Name(firstName, middleName, lastName);

	            // Create the Competitor object
	            competitor = new Competitor(id, name, level, country, age, scores);
	            System.out.println(competitor.getFullDetails());
	        } else {
	            JOptionPane.showMessageDialog(null, "No competitor found with ID: " + compId, "Not Found", JOptionPane.WARNING_MESSAGE);
	        }

	        // Close resources
	        rs.close();
	        pstmt.close();
	        conn.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }

	    return competitor; // Return the competitor (or null if not found)
	}

	
	private void clearFields() {
	    questionField.setText("");
	    option1Field.setText("");
	    option2Field.setText("");
	    option3Field.setText("");
	    option4Field.setText("");
	    answerField.setText("");
	    levelField.setSelectedIndex(0); // Reset dropdown to the first item
	}
	
	private void populateFieldsFromTable(JTable table) {
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow != -1) {
	        questionField.setText(table.getValueAt(selectedRow, 2).toString());
	        option1Field.setText(table.getValueAt(selectedRow, 3).toString());
	        option2Field.setText(table.getValueAt(selectedRow, 4).toString());
	        option3Field.setText(table.getValueAt(selectedRow, 5).toString());
	        option4Field.setText(table.getValueAt(selectedRow, 6).toString());
	        answerField.setText(table.getValueAt(selectedRow, 7).toString());

	        // Set the level in the combo box
	        String level = table.getValueAt(selectedRow, 1).toString();
	        levelField.setSelectedItem(CompetitionLevel.valueOf(level.toUpperCase()));
	    }
	}
	
	public void loadStatistics(boolean performer) {
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
        

        String sql = "SELECT name, level, scores FROM user"; // Fetch name as well
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        // Initialize score frequency and top scores
        int[] scoreFrequency = new int[6]; // Index 0 unused (1-5 scores)
        int topBeginner = 0, topIntermediate = 0, topAdvanced = 0;
        String topCompetitorName = "";  // Name of competitor with highest score
        int highestScore = 0;  // Track highest total score

        while (rs.next()) {
            String name = rs.getString("name"); // Fetch competitor's name
            String level = rs.getString("level");
            String scoresString = rs.getString("scores");

            int[] scores = parseScores(scoresString);

            // Count frequency of each score (1-5)
            for (int score : scores) {
                if (score >= 1 && score <= 5) {
                    scoreFrequency[score]++;
                }
            }

            // Calculate total score for competitor
            int totalScore = Arrays.stream(scores).sum();

            // Update top scores for each level
            if (level.equalsIgnoreCase("BEGINNER") && totalScore > topBeginner) {
                topBeginner = totalScore;
            } else if (level.equalsIgnoreCase("INTERMEDIATE") && totalScore > topIntermediate) {
                topIntermediate = totalScore;
            } else if (level.equalsIgnoreCase("ADVANCED") && totalScore > topAdvanced) {
                topAdvanced = totalScore;
            }

            // Track competitor with highest total score
            if (totalScore > highestScore) {
                highestScore = totalScore;
                topCompetitorName = name;
            }
        }

        // Print computed statistics
        if(performer == true) {      
        	System.out.println("Competitor with Highest Score: " + topCompetitorName + " (Score: " + highestScore + ")");
        }else if(performer == false) {        	
        	System.out.println("Score Frequency: " + Arrays.toString(scoreFrequency));
        	System.out.println("Top Beginner Score: " + topBeginner);
        	System.out.println("Top Intermediate Score: " + topIntermediate);
        	System.out.println("Top Advanced Score: " + topAdvanced);
        }

        // Populate Frequency Table (table_1)
        DefaultTableModel freqModel = (DefaultTableModel) table_1.getModel();
        freqModel.setRowCount(0); // Clear existing data
        Object[] frequencyRow = {
            scoreFrequency[1], scoreFrequency[2], scoreFrequency[3], scoreFrequency[4], scoreFrequency[5]
        };
        freqModel.addRow(frequencyRow);

        // Populate Top Score Table (table_2)
        DefaultTableModel topScoreModel = (DefaultTableModel) table_2.getModel();
        topScoreModel.setRowCount(0); // Clear existing data
        Object[] topScoreRow = { topBeginner, topIntermediate, topAdvanced };
        topScoreModel.addRow(topScoreRow);

        bestPerformer.setText("Competitor with Highest Score: " + topCompetitorName + " (Score: " + highestScore + ")");

        // Close resources
        rs.close();
        pstmt.close();
        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading statistics.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

	/**
	 * Create the frame.
	 */
	public AdminPanel(boolean console) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 1800, 789);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout()); // Use BorderLayout to fill the space
		setContentPane(contentPane);

		// Create JTabbedPane with tabs on the left
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER); // Make it fill the entire window

		// Panel 1: Questions
		JPanel question = new JPanel();
		question.setBackground(Color.WHITE);
		tabbedPane.addTab("Questions", null, question, null);
		question.setLayout(null);

		JLabel lblNewLabel = new JLabel("Question : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 38, 107, 25);
		question.add(lblNewLabel);

		JLabel lblLevel = new JLabel("Level : ");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLevel.setBounds(10, 78, 107, 25);
		question.add(lblLevel);

		JLabel lblOption = new JLabel("Option1 : ");
		lblOption.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOption.setBounds(10, 119, 107, 25);
		question.add(lblOption);

		JLabel lblOption_1 = new JLabel("Option2 :");
		lblOption_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOption_1.setBounds(10, 166, 107, 25);
		question.add(lblOption_1);

		JLabel lblOption_2 = new JLabel("Option3 : ");
		lblOption_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOption_2.setBounds(10, 212, 107, 25);
		question.add(lblOption_2);

		JLabel lblOption_3 = new JLabel("Option4 : ");
		lblOption_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOption_3.setBounds(10, 265, 107, 25);
		question.add(lblOption_3);

		JLabel lblAnswer = new JLabel("Answer : ");
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAnswer.setBounds(10, 313, 107, 25);
		question.add(lblAnswer);

		questionField = new JTextField();
		questionField.setColumns(10);
		questionField.setBounds(121, 38, 388, 26);
		question.add(questionField);

		answerField = new JTextField();
		answerField.setColumns(10);
		answerField.setBounds(121, 316, 388, 26);
		question.add(answerField);

		option1Field = new JTextField();
		option1Field.setColumns(10);
		option1Field.setBounds(121, 120, 388, 26);
		question.add(option1Field);

		option2Field = new JTextField();
		option2Field.setColumns(10);
		option2Field.setBounds(121, 165, 388, 26);
		question.add(option2Field);

		option3Field = new JTextField();
		option3Field.setColumns(10);
		option3Field.setBounds(121, 215, 388, 26);
		question.add(option3Field);

		option4Field = new JTextField();
		option4Field.setColumns(10);
		option4Field.setBounds(121, 264, 388, 26);
		question.add(option4Field);

		levelField = new JComboBox<>(CompetitionLevel.values());
		levelField.setBounds(120, 81, 389, 27);
		question.add(levelField);

		JButton createBtn = new JButton("Create");
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompetitionLevel level = (CompetitionLevel) levelField.getSelectedItem();
				String questions = questionField.getText();
				String option1 = option1Field.getText();
				String option2 = option2Field.getText();
				String option3 = option3Field.getText();
				String option4 = option4Field.getText();
				String answer = answerField.getText();

				if (questions.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty()
						|| option4.isEmpty() || answer.isEmpty() || level == null) {
					JOptionPane.showMessageDialog(null, "All fields must be filled.", "Empty Field",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					// Establish connection
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root",
							"");

					// Check if the question already exists
					String checkSql = "SELECT COUNT(*) FROM question WHERE question = ?";
					PreparedStatement checkStmt = conn.prepareStatement(checkSql);
					checkStmt.setString(1, questions);
					ResultSet rs = checkStmt.executeQuery();

					rs.next();
					int count = rs.getInt(1); // Get the count of matching questions
					rs.close();
					checkStmt.close();

					if (count > 0) {
						// Question already exists
						JOptionPane.showMessageDialog(null, "This question already exists.", "Duplicate Question",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// Reset AUTO_INCREMENT if needed
						Statement stmt = conn.createStatement();
						stmt.executeUpdate("ALTER TABLE question AUTO_INCREMENT = 1");
						stmt.close();

						// Insert data
						String sql = "INSERT INTO question (level, question, option1, option2, option3, option4, answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, level.name());
						pstmt.setString(2, questions);
						pstmt.setString(3, option1);
						pstmt.setString(4, option2);
						pstmt.setString(5, option3);
						pstmt.setString(6, option4);
						pstmt.setString(7, answer);
						pstmt.executeUpdate();

						System.out.println("Question Created Successfully.");
						JOptionPane.showMessageDialog(null, "Question Created Successfully.", "Create Question",
								JOptionPane.INFORMATION_MESSAGE);

						// Close the prepared statement
						pstmt.close();
						loadQuestions();
					}

					// Close the database connection
					conn.close();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error during creating question.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		createBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		createBtn.setBounds(135, 384, 144, 56);
		question.add(createBtn);

		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Determine which table has a selected row
		        JTable selectedTable = null;
		        if (viewAllTable.getSelectedRow() != -1) {
		            selectedTable = viewAllTable;
		        } else if (beginnerTable.getSelectedRow() != -1) {
		            selectedTable = beginnerTable;
		        } else if (intermediateTable.getSelectedRow() != -1) {
		            selectedTable = intermediateTable;
		        } else if (advanceTable.getSelectedRow() != -1) {
		            selectedTable = advanceTable;
		        }

		        if (selectedTable == null) {
		            JOptionPane.showMessageDialog(null, "Please select a row to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        int selectedRow = selectedTable.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "No row selected.", "Update Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Get the ID of the selected row
		        int id = Integer.parseInt(selectedTable.getValueAt(selectedRow, 0).toString());

		        // Get existing values from the table
		        String existingLevel = selectedTable.getValueAt(selectedRow, 1).toString();
		        String existingQuestion = selectedTable.getValueAt(selectedRow, 2).toString();
		        String existingOption1 = selectedTable.getValueAt(selectedRow, 3).toString();
		        String existingOption2 = selectedTable.getValueAt(selectedRow, 4).toString();
		        String existingOption3 = selectedTable.getValueAt(selectedRow, 5).toString();
		        String existingOption4 = selectedTable.getValueAt(selectedRow, 6).toString();
		        String existingAnswer = selectedTable.getValueAt(selectedRow, 7).toString();

		        // Get updated values from input fields
		        String updatedLevel = levelField.getSelectedItem().toString();
		        String updatedQuestion = questionField.getText().trim();
		        String updatedOption1 = option1Field.getText().trim();
		        String updatedOption2 = option2Field.getText().trim();
		        String updatedOption3 = option3Field.getText().trim();
		        String updatedOption4 = option4Field.getText().trim();
		        String updatedAnswer = answerField.getText().trim();

		        // Check if any field has changed
		        if (existingLevel.equals(updatedLevel) &&
		            existingQuestion.equals(updatedQuestion) &&
		            existingOption1.equals(updatedOption1) &&
		            existingOption2.equals(updatedOption2) &&
		            existingOption3.equals(updatedOption3) &&
		            existingOption4.equals(updatedOption4) &&
		            existingAnswer.equals(updatedAnswer)) {
		            
		            JOptionPane.showMessageDialog(null, "No fields were updated.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
		            return;
		        }

		        // If fields are changed, update the database
		        try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");

		            String sql = "UPDATE question SET level = ?, question = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, answer = ? WHERE id = ?";
		            PreparedStatement pstmt = conn.prepareStatement(sql);
		            pstmt.setString(1, updatedLevel);
		            pstmt.setString(2, updatedQuestion);
		            pstmt.setString(3, updatedOption1);
		            pstmt.setString(4, updatedOption2);
		            pstmt.setString(5, updatedOption3);
		            pstmt.setString(6, updatedOption4);
		            pstmt.setString(7, updatedAnswer);
		            pstmt.setInt(8, id);

		            int rowsUpdated = pstmt.executeUpdate();
		            pstmt.close();
		            conn.close();

		            if (rowsUpdated > 0) {
		                JOptionPane.showMessageDialog(null, "Question updated successfully.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
		                loadQuestions(); // Refresh table data
		            } else {
		                JOptionPane.showMessageDialog(null, "Update failed. Try again.", "Update Error", JOptionPane.ERROR_MESSAGE);
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error updating question.", "Database Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		updateBtn.setBounds(342, 384, 144, 56);
		question.add(updateBtn);

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Determine which table has a selected row
		        JTable selectedTable = null;
		        if (viewAllTable.getSelectedRow() != -1) {
		            selectedTable = viewAllTable;
		        } else if (beginnerTable.getSelectedRow() != -1) {
		            selectedTable = beginnerTable;
		        } else if (intermediateTable.getSelectedRow() != -1) {
		            selectedTable = intermediateTable;
		        } else if (advanceTable.getSelectedRow() != -1) {
		            selectedTable = advanceTable;
		        }

		        if (selectedTable == null) {
		            JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        int selectedRow = selectedTable.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "No row selected.", "Delete Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Get the ID of the selected row
		        int id = Integer.parseInt(selectedTable.getValueAt(selectedRow, 0).toString());

		        // Ask for confirmation
		        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this question?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
		        if (confirm != JOptionPane.YES_OPTION) {
		            return;
		        }

		        // Delete from the database
		        try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
		            String sql = "DELETE FROM question WHERE id = ?";
		            PreparedStatement pstmt = conn.prepareStatement(sql);
		            pstmt.setInt(1, id);

		            int rowsDeleted = pstmt.executeUpdate();
		            pstmt.close();
		            conn.close();

		            if (rowsDeleted > 0) {
		                JOptionPane.showMessageDialog(null, "Question deleted successfully.", "Delete Success", JOptionPane.INFORMATION_MESSAGE);
		                loadQuestions(); // Refresh table data
		            } else {
		                JOptionPane.showMessageDialog(null, "Delete failed. Try again.", "Delete Error", JOptionPane.ERROR_MESSAGE);
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error deleting question.", "Database Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setBounds(135, 467, 144, 56);
		question.add(btnNewButton_2);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(601, 38, 956, 634);
		question.add(tabbedPane_1);

		// Wrap each table inside a JScrollPane
		viewAllTable = new JTable();
		JScrollPane viewAllScrollPane = new JScrollPane(viewAllTable);
		tabbedPane_1.addTab("View All", null, viewAllScrollPane, null);

		beginnerTable = new JTable();
		JScrollPane beginnerScrollPane = new JScrollPane(beginnerTable);
		tabbedPane_1.addTab("Beginner", null, beginnerScrollPane, null);

		intermediateTable = new JTable();
		JScrollPane intermediateScrollPane = new JScrollPane(intermediateTable);
		tabbedPane_1.addTab("Intermediate", null, intermediateScrollPane, null);

		advanceTable = new JTable();
		JScrollPane advanceScrollPane = new JScrollPane(advanceTable);
		tabbedPane_1.addTab("Advance", null, advanceScrollPane, null);

		// Add selection listeners to each table
		viewAllTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && viewAllTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(viewAllTable);
		    }
		});

		beginnerTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && beginnerTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(beginnerTable);
		    }
		});

		intermediateTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && intermediateTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(intermediateTable);
		    }
		});

		advanceTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && advanceTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(advanceTable);
		    }
		});
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		clearBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clearBtn.setBounds(342, 467, 144, 56);
		question.add(clearBtn);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage lp = new LandingPage();
        		lp.setVisible(true);
        		dispose();
			}
		});
		logoutBtn.setBounds(1501, 11, 89, 23);
		question.add(logoutBtn);

		loadQuestions();

		// Panel 2: Competitor
		JPanel competitor = new JPanel();
		competitor.setBackground(Color.WHITE);
		tabbedPane.addTab("Competitor", null, competitor, null);
		competitor.setLayout(null);
		
		JButton logoutBtn_1 = new JButton("Logout");
		logoutBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingPage lp = new LandingPage();
        		lp.setVisible(true);
        		dispose();
			}
		});
		logoutBtn_1.setBounds(1502, 11, 82, 23);
		competitor.add(logoutBtn_1);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(90, 61, 1399, 604);
		competitor.add(tabbedPane_2);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_2.addTab("All Competitor", null, scrollPane, null);
		
		String[] competitorColumn = {"ID","Name","Age","Level","Country","Score1","Score2","Score3","Score4","Score5"};
		DefaultTableModel allCompetitorModel = new DefaultTableModel(competitorColumn, 0) { 
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	     };
		allCompetitorTable = new JTable(allCompetitorModel);
		scrollPane.setViewportView(allCompetitorTable);
		
		JPanel searchPanel = new JPanel();
		tabbedPane_2.addTab("Search Competitor", null, searchPanel, null);
		searchPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Competitor ID :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(94, 56, 141, 42);
		searchPanel.add(lblNewLabel_1);
		
		searchField = new JTextField();
		searchField.setBounds(264, 63, 260, 33);
		searchPanel.add(searchField);
		searchField.setColumns(10);
		
		JLabel fullNameLabel = new JLabel("Name:");
        fullNameLabel.setBounds(94, 126, 46, 14);
        searchPanel.add(fullNameLabel);

        nameField = new JLabel("");
        nameField.setBounds(186, 126, 185, 14);
        searchPanel.add(nameField);

        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setBounds(94, 165, 46, 14);
        searchPanel.add(levelLabel);

        levelFieldComp = new JLabel("");
        levelFieldComp.setBounds(186, 165, 91, 14);
        searchPanel.add(levelFieldComp);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(94, 204, 46, 14);
        searchPanel.add(ageLabel);

        ageField = new JLabel("");
        ageField.setBounds(186, 204, 46, 14);
        searchPanel.add(ageField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(94, 241, 60, 14);
        searchPanel.add(countryLabel);

        countryField = new JLabel("");
        countryField.setBounds(186, 241, 100, 14);
        searchPanel.add(countryField);
        
        JLabel lblNewLabel_2 = new JLabel("Full Details:");
        lblNewLabel_2.setBounds(94, 281, 80, 14);
        searchPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("Short Details:");
        lblNewLabel_2_1.setBounds(94, 348, 80, 14);
        searchPanel.add(lblNewLabel_2_1);
        
        JTextPane fullDetailField = new JTextPane();
		fullDetailField.setBackground(new Color(240, 240, 240));
		fullDetailField.setBounds(186, 266, 338, 54);
		searchPanel.add(fullDetailField);
		
		JTextPane shortDetailField = new JTextPane();
		shortDetailField.setBackground(new Color(240, 240, 240));
		shortDetailField.setBounds(186, 342, 338, 20);
		searchPanel.add(shortDetailField);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = searchField.getText().trim();
                    if (input.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a competitor ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int competitorId = Integer.parseInt(input); 
                    Competitor comp = searchCompetitorById(competitorId);

                    if (comp != null) {
                        nameField.setText(comp.getName()); 
                        levelFieldComp.setText(comp.getLevel().toString());  
                        ageField.setText(String.valueOf(comp.getAge())); 
                        countryField.setText(comp.getCountry());
                        fullDetailField.setText(comp.getFullDetails());
                        shortDetailField.setText(comp.getShortDetails());
                    } else {
                        JOptionPane.showMessageDialog(null, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                        // Clear fields if no competitor found
                        nameField.setText(""); 
                        levelFieldComp.setText("");  
                        ageField.setText(""); 
                        countryField.setText("");
                        fullDetailField.setText("");
                        shortDetailField.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

		searchBtn.setBounds(560, 62, 89, 35);
		searchPanel.add(searchBtn);
		
		JPanel statistics = new JPanel();
		tabbedPane_2.addTab("Statistics", null, statistics, null);
		statistics.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(69, 87, 295, 49);
		statistics.add(scrollPane_1);
		
		String statlabel1[] = {"1","2","3","4","5"};
		
		DefaultTableModel statmodel1 = new DefaultTableModel(statlabel1,0);
		table_1 = new JTable(statmodel1);
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblNewLabel_3 = new JLabel("Frequency for each score:");
		lblNewLabel_3.setBounds(69, 62, 191, 14);
		statistics.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Top Score for each level:");
		lblNewLabel_3_1.setBounds(69, 174, 191, 14);
		statistics.add(lblNewLabel_3_1);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(69, 222, 295, 49);
		statistics.add(scrollPane_1_1);
		String statlabel2[] = {"BEGINNER","INTERMEDIATE","ADVANCE"};
		
		DefaultTableModel statmodel2 = new DefaultTableModel(statlabel2,0);
		table_2 = new JTable(statmodel2);
		scrollPane_1_1.setViewportView(table_2);
		
		JLabel lblNewLabel_4 = new JLabel("Best Performer: ");
		lblNewLabel_4.setBounds(69, 316, 92, 14);
		statistics.add(lblNewLabel_4);
		
		bestPerformer = new JLabel("");
		bestPerformer.setBounds(158, 316, 306, 14);
		statistics.add(bestPerformer);
		
		
		if(console == false) {			
			loadCompetitor();
			loadStatistics(false);
		}

		// Adjust the size of the outer tabs
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			JLabel label = new JLabel(tabbedPane.getTitleAt(i), JLabel.CENTER);
			label.setPreferredSize(new Dimension(150, 180)); // Adjust tab size
			tabbedPane.setTabComponentAt(i, label);
		}
		// Adjust the size of the outer tabs
		for (int i = 0; i < tabbedPane_1.getTabCount(); i++) {
			JLabel label = new JLabel(tabbedPane_1.getTitleAt(i), JLabel.CENTER);
			label.setPreferredSize(new Dimension(150, 50)); // Adjust tab size
			tabbedPane_1.setTabComponentAt(i, label);
		}

	}
}
