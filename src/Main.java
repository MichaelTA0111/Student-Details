import student.Student;

public class Main {
    // Initialise all student objects
    static Student student1 = new Student(1, "Andrew", "Jackson","Electrical & Electronic Engineering", 1);
    static Student student2 = new Student(2, "Ben", "Kraken", "Software & Electronic Systems Engineering", 2);
    static Student student3 = new Student(3, "Chris", "Lightning", "Computer Science", 3);

    public static void main(String[] args) {
        // Print all student details
        System.out.println("All student details:");
        student1.printDetails();
        student2.printDetails();
        student3.printDetails();

        // Increment the academic year
        System.out.println("\nThe academic year has finished!");
        student1.incrementYear();
        student2.incrementYear();
        student3.incrementYear();

        // Print the updated student details
        System.out.println("\nAll student details:");
        student1.printDetails();
        student2.printDetails();
        student3.printDetails();
    }
}