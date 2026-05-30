public class Student {
    private String firstName;
    private String lastName;
    private double grade;

    public Student(String firstName, String lastName, double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName()  {
        return lastName;
    }
    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s | Grade: %.1f", firstName, lastName, grade);
    }
}
