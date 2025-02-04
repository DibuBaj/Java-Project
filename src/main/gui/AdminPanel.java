package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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

    /**
     * Create the frame.
     */
    public AdminPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 936, 548);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Create JTabbedPane with tabs on the left
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBounds(0, 0, 920, 509);
        contentPane.add(tabbedPane);

        // Panel 1: Questions
        JPanel question = new JPanel();
        question.setBackground(Color.WHITE);
        tabbedPane.addTab("Questions", null, question, null);
        question.setLayout(null);

        // Panel 2: Competitor
        JPanel competitor = new JPanel();
        competitor.setBackground(Color.WHITE);
        tabbedPane.addTab("Competitor", null, competitor, null);

        // Make tabs larger by customizing the tab component
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            JLabel label = new JLabel(tabbedPane.getTitleAt(i), JLabel.CENTER);
            label.setPreferredSize(new Dimension(150, 50)); // Adjust tab size here (Width x Height)
            tabbedPane.setTabComponentAt(i, label);
        }
    }
}
