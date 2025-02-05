package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.component.CompetitionLevel;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanel frame = new AdminPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	String[] columnNames = { "ID", "Level", "Question", "Option1", "Option2", "Option3", "Option4", "Answer" };
	private JTable viewAllTable;
	private JTable beginnerTable;
	private JTable intermediateTable;
	private JTable advanceTable;
	
	private void loadQuestions() {
		
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
	        String sql = "SELECT * FROM question";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();

	        // Make DefaultTableModel non-editable
	        DefaultTableModel allModel = new DefaultTableModel(columnNames, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // Prevent editing
	            }
	        };
	        DefaultTableModel beginnerModel = new DefaultTableModel(columnNames, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        DefaultTableModel intermediateModel = new DefaultTableModel(columnNames, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        DefaultTableModel advanceModel = new DefaultTableModel(columnNames, 0) {
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

	            // Add row to "View All"
	            allModel.addRow(row);

	            // Add row to specific level-based tables
	            String level = rs.getString("level");
	            if (level.equalsIgnoreCase("BEGINNER")) {
	                beginnerModel.addRow(row);
	            } else if (level.equalsIgnoreCase("INTERMEDIATE")) {
	                intermediateModel.addRow(row);
	            } else if (level.equalsIgnoreCase("ADVANCED")) {
	                advanceModel.addRow(row);
	            }
	        }

	        // Assign models to JTables (this was missing!)
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

	/**
	 * Create the frame.
	 */
	public AdminPanel() {
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
		
		viewAllTable = new JTable();
		tabbedPane_1.addTab("View All", null, viewAllTable, null);
		
		beginnerTable = new JTable();
		tabbedPane_1.addTab("Beginner", null, beginnerTable, null);
		
		intermediateTable = new JTable();
		tabbedPane_1.addTab("Intermediate", null, intermediateTable, null);
		
		advanceTable = new JTable();
		tabbedPane_1.addTab("Advance", null, advanceTable, null);
		
		// Add selection listener to viewAllTable
		viewAllTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && viewAllTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(viewAllTable);
		    }
		});

		// Add selection listener to beginnerTable
		beginnerTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && beginnerTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(beginnerTable);
		    }
		});

		// Add selection listener to intermediateTable
		intermediateTable.getSelectionModel().addListSelectionListener(event -> {
		    if (!event.getValueIsAdjusting() && intermediateTable.getSelectedRow() != -1) {
		        populateFieldsFromTable(intermediateTable);
		    }
		});

		// Add selection listener to advanceTable
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
