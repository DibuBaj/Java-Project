package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import main.CompetitionLevel;
import main.Competitor;
import main.Name;

public class CompetitorTest {
	@BeforeEach
	public void startMsg() {
		System.out.println("Test Started.\n----------------------");
	}
	

	@Test
	public void testFullDetail() {
		Name name1 = new Name("Keith", "John", "Talbot");
        Competitor competitor1 = new Competitor(100, name1, CompetitionLevel.BEGINNER, "UK", 21);
        
        String expected = "Competitor number 100, name Keith John Talbot, country UK.\n"
                + "Keith is a beginner aged 21 and has an overall score of 5.0.";
        String actual = competitor1.getFullDetails();

        System.out.println("Expected: " + expected+"\n");
        System.out.println("Actual:   " + actual);  

        assertEquals(expected, actual, "Full detail should be same.");
	}
	
	@Test
	public void testShortDetail() {
		Name name2 = new Name("Hari","","Shrestha");
		Competitor competitor2 = new Competitor(102, name2, CompetitionLevel.ADVANCED, "NEP", 35);
		
		String expected = "CN 102 (HS) has overall score 5.0.";
		String actual = competitor2.getShortDetails();
		
		 System.out.println("Expected: " + expected+"\n");
	     System.out.println("Actual:   " + actual);  
	     
	     assertEquals(expected, actual, "Short detail should be same.");
	}
	
	@AfterEach
	public void finishMsg() {
		System.out.println("----------------------\nTest ended.");
		System.out.println("\n");
	}
}
