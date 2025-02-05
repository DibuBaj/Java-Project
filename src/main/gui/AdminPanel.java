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
	private JTable viewAllTable;
	private JTable beginnerTable;
	private JTable intermediateTable;
	private JTable advanceTable;
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
	private void loadQuestions() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
			String sql = "SELECT * FROM question";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();


			DefaultTableModel allModel = new DefaultTableModel(columnNames, 0);
			DefaultTableModel beginnerModel = new DefaultTableModel(columnNames, 0);
			DefaultTableModel intermediateModel = new DefaultTableModel(columnNames, 0);
			DefaultTableModel advanceModel = new DefaultTableModel(columnNames, 0);

			while (rs.next()) {
				Object[] row = { rs.getInt("id"), rs.getString("level"), rs.getString("question"),
						rs.getString("option1"), rs.getString("option2"), rs.getString("option3"),
						rs.getString("option4"), rs.getString("answer") };

				allModel.addRow(row);

				String level = rs.getString("level");
				if (level.equalsIgnoreCase("BEGINNER")) {
					beginnerModel.addRow(row);
				} else if (level.equalsIgnoreCase("INTERMEDIATE")) {
					intermediateModel.addRow(row);
				} else if (level.equalsIgnoreCase("ADVANCED")) {
					advanceModel.addRow(row);
				}
			}

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
	private DefaultTableModel getNonEditableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
    }
	/**
	 * Create the frame.
	 */
	public AdminPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the screen size using Toolkit
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize); // Set the frame to cover the entire screen
		setLocationRelativeTo(null); // Center the window (optional)

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
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		updateBtn.setBounds(342, 384, 144, 56);
		question.add(updateBtn);

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setBounds(135, 467, 144, 56);
		question.add(btnNewButton_2);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(637, 39, 863, 634);
		question.add(tabbedPane_1);

		JPanel allView = new JPanel();
		tabbedPane_1.addTab("View All", null, allView, null);

		viewAllTable = new JTable();
		viewAllTable.setColumnSelectionAllowed(true);
		allView.add(viewAllTable);

		JPanel beginner = new JPanel();
		tabbedPane_1.addTab("Beginner", null, beginner, null);

		beginnerTable = new JTable();
		beginner.add(beginnerTable);

		JPanel intermediate = new JPanel();
		tabbedPane_1.addTab("Intermediate", null, intermediate, null);

		intermediateTable = new JTable();
		intermediate.add(intermediateTable);

		JPanel advanced = new JPanel();
		tabbedPane_1.addTab("Advance", null, advanced, null);

		advanceTable = new JTable();
		advanced.add(advanceTable);
		
		viewAllTable.setModel(getNonEditableModel(columnNames));
		beginnerTable.setModel(getNonEditableModel(columnNames));
		intermediateTable.setModel(getNonEditableModel(columnNames));
		advanceTable.setModel(getNonEditableModel(columnNames));
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		clearBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clearBtn.setBounds(342, 467, 144, 56);
		question.add(clearBtn);

		loadQuestions();

		// Panel 2: Competitor
		JPanel competitor = new JPanel();
		competitor.setBackground(Color.WHITE);
		tabbedPane.addTab("Competitor", null, competitor, null);

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
