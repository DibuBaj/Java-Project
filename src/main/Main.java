package main;

import java.awt.EventQueue;
import java.util.Scanner;

import main.gui.AdminPanel;
import main.gui.LandingPage;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);   

        while (true) { 
            System.out.println("--- Competitor Management System ---\n1. GUI\n2. Console");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {  
                System.out.println("Invalid input. Please enter a number.");
                sc.next();   
                continue;
            }

            int choice1 = sc.nextInt();

            switch (choice1) {
                case 1:
                    EventQueue.invokeLater(() -> {
                        try {
                            LandingPage frame = new LandingPage();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    sc.close();   
                    return;   

                case 2:
                    while (true) {   
                        System.out.println("\n--- Console Mode ---");
                        System.out.println("1. Generate Full Report");
                        System.out.println("2. Display Top Performer");
                        System.out.println("3. Generate Statistics");
                        System.out.println("4. Search Competitor by ID");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");

                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.next();  
                            continue;
                        }
                        AdminPanel ap = new AdminPanel(true);
                        int choice2 = sc.nextInt();

                        switch (choice2) {
                            case 1:
                                System.out.println("Generating Full Report...");
                                ap.loadCompetitor();
                                break;
                            case 2:
                                System.out.println("Displaying Top Performer...");
                                ap.loadStatistics(true);
                                 break;
                            case 3:
                                System.out.println("Generating Statistics...");
                                ap.loadStatistics(false);
                                 break;
                            case 4:
                                System.out.print("Enter Competitor ID: ");
                                if (!sc.hasNextInt()) {
                                    System.out.println("Invalid ID. Please enter a number.");
                                    sc.next(); 
                                    break;
                                }
                                int competitorId = sc.nextInt();
                                System.out.println(ap.searchCompetitorById(competitorId));
                                break;
                            case 5:
                                System.out.println("Exiting Console");
                                sc.close();   
                                return;   
                            default:
                                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        }
                    }
                    
                default:
                    System.out.println("Invalid input. Please enter 1 or 2.");
            }
        }
    }

}
