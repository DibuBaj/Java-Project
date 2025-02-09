package main.component;

public class Question {
    private int id;
    private String level;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    // Constructor
    public Question(int id, String level, String question, String option1, String option2, String option3, String option4, String answer) {
        this.id = id;
        this.level = level;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    // Getters
    public int getId() { return id; }
    public String getLevel() { return level; }
    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getAnswer() { return answer; }

    @Override
    public String toString() {
        return "Question: " + question + "\n" +
               "A) " + option1 + " B) " + option2 + " C) " + option3 + " D) " + option4 + "Answer:"+answer+"\n";
    }
}

