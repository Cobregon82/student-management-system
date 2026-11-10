/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagementsystem;
import java.util.Scanner;

/**
 *
 * @author carmenobregon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    StudentManagementSystem system = new StudentManagementSystem();

    while (true) {
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. View Students");
        System.out.println("4. Add or Update Grade");
        System.out.println("5. Reports");
        System.out.println("6. Save Students");
        System.out.println("7. Load Students");
        System.out.println("8. Update Student Name");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {


    case "1":
    System.out.print("Enter student ID: ");
    String id = scanner.nextLine();

    System.out.print("Enter student name: ");
    String name = scanner.nextLine();

    System.out.println("1. Undergraduate");
    System.out.println("2. Graduate");
    System.out.print("Choose student type: ");
    String type = scanner.nextLine();

    Student student;

    if (type.equals("1")) {
        student = new UndergradStudent(id, name);
    } else if (type.equals("2")) {
        student = new GradStudent(id, name);
    } else {
        System.out.println("Invalid student type.");
        break;
    }

try {
    system.addStudent(student);
    System.out.println("Student added successfully: " + student.getName());
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}    break;

  case "2":
    System.out.print("Enter student ID to remove: ");
    String removeId = scanner.nextLine();

    try {
        Student removedStudent = system.removeStudent(removeId);
        System.out.println("Student removed successfully: "
                + removedStudent.getName());
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    break;

   case "3":
    if (system.getAllStudents().isEmpty()) {
        System.out.println("No students found.");
    } else {
        System.out.println("STUDENT LIST");

        for (Student s : system.getAllStudents()) {
            System.out.println("ID: " + s.getId()
                    + " | Name: " + s.getName()
                    + " | Average: " + s.getAverage());
        }
    }
    break;

   case "4":
    System.out.print("Enter student ID: ");
    String gradeId = scanner.nextLine();

    System.out.print("Enter subject name: ");
    String subjectName = scanner.nextLine();

    System.out.print("Enter grade (0-100): ");
    String gradeInput = scanner.nextLine();

    try {
        double grade = Double.parseDouble(gradeInput);

        system.setGrade(gradeId, subjectName, grade);

        System.out.println("Grade added or updated successfully!");

    } catch (NumberFormatException e) {
        System.out.println("Error: Please enter a valid number.");

    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    break;

    case "5":
    System.out.println("STUDENT REPORTS");
    System.out.println("1. Student Averages");
    System.out.println("2. Highest and Lowest Averages");
    System.out.println("3. Students Sorted by Grade");
    System.out.println("4. Highest and Lowest Grades by Subject");
    System.out.print("Choose a report: ");

    String reportChoice = scanner.nextLine();

    switch (reportChoice) {
        case "1":
            Reports.showStudentAverages(system);
            break;

        case "2":
            Reports.showHighestAndLowest(system);
            break;

        case "3":
            Reports.showStudentsSortedByGrade(system);
            break;

        case "4":
            System.out.print("Enter subject name: ");
            String reportSubject = scanner.nextLine();
            Reports.showHighestAndLowest(system, reportSubject);
            break;

        default:
            System.out.println("Invalid report option.");
    }
    break;

    case "6":
    boolean saved = system.saveToFile("students.txt");

    if (saved) {
        System.out.println("Students saved successfully!");
    } else {
        System.out.println("Failed to save students.");
    }
    break;

   case "7":
    boolean loaded = system.loadFromFile("students.txt");

    if (loaded) {
        System.out.println("Students loaded successfully!");
    } else {
        System.out.println("Failed to load students.");
    }
    break;

    case "8":
        System.out.print("Enter student ID: ");
        String updateId = scanner.nextLine();
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        try{
            system.updateStudentName(updateId, newName);
            System.out.println("Name updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        break;
        
    case "0":
    System.out.println("Goodbye!");
    return;

    default:
        System.out.println("Invalid option.");
    }

} // Ends the while loop

    } // Ends main()

} // Ends Main class