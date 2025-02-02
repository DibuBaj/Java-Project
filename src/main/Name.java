package main;

public class Name {
    private String firstName;
    private String middleName;    
    private String lastName;
    
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;        
    }
    
    public String getFullName() {
        if (middleName == null || middleName.isEmpty()) {
            return firstName + " " + lastName;
        }
        return firstName + " " + middleName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInitials() {
        if (middleName == null || middleName.isEmpty()) {
            return "" + firstName.charAt(0) + lastName.charAt(0);
        }
        return "" + firstName.charAt(0) + middleName.charAt(0) + lastName.charAt(0);
    }
}
