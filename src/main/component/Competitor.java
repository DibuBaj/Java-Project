package main.component;

public class Competitor {
    private int id;
    private Name name;
    private CompetitionLevel level;
    private String country;
    private int age;
    private int[] scores;
    
    // Constructor
    public Competitor(int id, Name name,CompetitionLevel level,String country, int age,int[] scores) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.country = country;
        this.age = age;
        this.scores = scores;
    }
    
    // Getter and Setter Methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.getFullName();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    public CompetitionLevel getLevel() {
        return level;
    }
    
    public void setLevel(CompetitionLevel level) {
        this.level = level;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int[] getScoreArray() {
        return scores;
    }
    
    public void setScoreArray(int[] scores) {
    	this.scores = scores;
    }
    
    
//    Get overall score
    public double getOverallScore() {
    	    if (scores == null || scores.length == 0) {
    	        return 0;
    	    }
    	    double sum = 0;
    	    
    	    for (int score : scores) {
    	        sum += score;
    	    }
    	    return sum / scores.length;
    }
    
//    Get competitor detail
    public String getFullDetails() {
        String fullName = (name != null) ? name.getFullName() : "Unknown fullname";
        String firstName = (name != null) ? name.getFirstName() : "Unknown firstname";
        String levelString = (level != null) ? level.toString().toLowerCase() : "unknown level";
        String countryString = (country != null) ? country : "Unknown";

        // Ensure overall score is handled correctly
        double overallScore = getOverallScore();

        return "Competitor number " + id + ", name " + fullName + ", country " + countryString + ".\n" +
               firstName + " is a " + levelString + " aged " + age + 
               " and has an overall score of " + overallScore + ".";
    }


    
    public String getShortDetails() {
        String initials = (name != null) ? name.getInitials() : "N/A";
        
        return "CN " + id + " (" + initials + ") has overall score " + getOverallScore() + ".";
    }
   
    
}
