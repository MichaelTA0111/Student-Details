package student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public class Student {
    // Constants
    static final int modulesPerYear = 6;
    static final int totalYears = 3;

    // Class attributes
    private final int id;
    private final String forename;
    private final String surname;
    private final String degreeTitle;
    private int year;
    private String degreeClassification;
    private final ArrayList<int[]> marks;

    /**
     * Constructor for the student class
     * @param id - The student ID
     * @param forename - The student forename
     * @param surname - The student surname
     * @param degreeTitle - The title of the degree being studied
     * @param year - The current year of study
     */
    public Student(int id, String forename, String surname, String degreeTitle, int year) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.degreeTitle = degreeTitle;
        this.year = year;
        this.degreeClassification = "Pending";
        this.marks = new ArrayList<>();

        // Generate all marks for previously completed years
        for (int i = 0; i < year - 1; i++) {
            this.marks.add(generateMarks());
        }
    }

    /**
     * Generate a list of random marks for a year of study
     * @return - An array of marks as integers
     */
    public int[] generateMarks() {
        int[] marks = new int[modulesPerYear];

        // Randomly assign each mark a value in the range of 40-100 according to a uniform distribution
        for (int j = 0; j < modulesPerYear; j++) {
            marks[j] = ThreadLocalRandom.current().nextInt(40, 101);
        }

        return marks;
    }

    /**
     * Getter for the full name of a student
     * @return - The full name of the student as a string
     */
    public String getFullName() {
        return String.format("%s %s", forename, surname);
    }

    /**
     * Print all available student details
     */
    public void printDetails() {
        // Print the personal details of a student
        String details;
        if (year > totalYears) {
            details = String.format("Student %d - %s, %s Graduate in %s", id, getFullName(), degreeClassification, degreeTitle);
        } else {
            details = String.format("Student %d - %s, Year %d in %s", id, getFullName(), year, degreeTitle);
        }
        System.out.println(details);

        // Print the list of marks the student has achieved
        printMarks();

        // Add a blank line after printing all details
        System.out.println();
    }

    /**
     * Increment the year of a student.
     * This involves automatically generating the year's module marks and graduating if applicable.
     */
    public void incrementYear() {
        // Increment the year attribute
        year++;

        // Generate all marks for the year completed
        this.marks.add(generateMarks());

        // Check if the student has completed all years in the degree
        if (year > totalYears) {
            graduate();
        }
    }

    /**
     * Private helper method to calculate the degree classification according to the total weighted marks achieved
     */
    public void calculateClassification() {
        int[] weightedAverageMarks = new int[totalYears];

        // Calculate the weighted average mark for each year.
        // Each year is worth double the previous year
        for (int i = 0; i < totalYears; i++) {
            // Get the array of marks for a year
            int[] yearlyMarks = marks.get(i);

            // Calculate the unweighted average mark for the year
            int cumulativeMarks = 0;
            for (int j = 0; j < modulesPerYear; j++) {
                cumulativeMarks += yearlyMarks[j];
            }
            int averageMark = cumulativeMarks / modulesPerYear;

            // Apply the weighting to the average mark and insert into the corresponding array.
            // The weighting is 2 raised to the power of the year (starting with 2^0 = 1)
            weightedAverageMarks[i] = (int) (averageMark * (Math.pow(2, i)));
        }

        // Calculate the weighted average of all marks
        int cumulativeMarks = 0;
        for (int i = 0; i < totalYears; i++) {
            cumulativeMarks += weightedAverageMarks[i];
        }
        // The divisor is (2^years - 1) to correctly account for the yearly weighting
        int averageMark = (int) (cumulativeMarks / (Math.pow(2, totalYears) - 1));

        // Determine the final classification based on the weighted average.
        if (averageMark >= 70) {
            degreeClassification = "First Class Honours";
        } else if (averageMark >= 60) {
            degreeClassification = "Second Class Honours (Upper Division)";
        } else if (averageMark >= 50) {
            degreeClassification = "Second Class Honours (Lower Division)";
        } else {
            degreeClassification = "Third Class Honours";
        }
    }

    /**
     * Private helper method to graduate a student who has completed their degree
     */
    public void graduate() {
        // Determine the final classification that the student achieved
        calculateClassification();

        // Print the graduation message
        System.out.printf("%s has graduated with a %s%n", getFullName(), degreeClassification);
    }

    /**
     * Private helper method to print all marks the student has achieved
     */
    public void printMarks() {
        // If the student is currently studying in their first year they have no marks to display
        if (year == 1) {
            System.out.printf("%s has not completed any modules yet%n", getFullName());
            return;
        }

        System.out.printf("All marks for %s:%n", getFullName());

        // Print the marks year by year
        for (int i = 0; i < year - 1; i++) {
            System.out.printf("Year %d - %s%n", i + 1, Arrays.toString(marks.get(i)));
        }
    }
}
