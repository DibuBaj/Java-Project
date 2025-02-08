package main.component;

public class Competitor {
    private int id;
    private Name name;
    private CompetitionLevel level;
    private String country;
    private int age;
    private int[] scores;
//    private String username;
//    private String password;
    
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
    
//    public String getUsername() {
//    	return username;
//    }
//    
//    public void setUsername(String username) {
//    	this.username = username; 
//    }
//    
//    public void setPassword(String password) {
//    	this.password = password;
//    }
    
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
        return "Competitor number " + id + ", name " + name.getFullName() + ", country " + country + ".\n" +
               name.getFirstName() + " is a " + level.toString().toLowerCase() + " aged " + age + 
               " and has an overall score of " + getOverallScore() + ".";
    }
    
    public String getShortDetails() {
        return "CN " + id + " (" + name.getInitials() + ") has overall score " + getOverallScore() + ".";
    }
    
    public static void main(String[] args) {
    	Name name1 = new Name("Keith", "John", "Talbot");
    	int[] scores = {5,4,5,5,5};
        Competitor competitor1 = new Competitor(100, name1,CompetitionLevel.BEGINNER, "UK", 21,scores);
        System.out.println(competitor1.getFullDetails());
        System.out.println(competitor1.getShortDetails());
    }
    
    
}
