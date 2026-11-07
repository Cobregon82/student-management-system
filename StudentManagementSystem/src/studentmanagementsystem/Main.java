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
        System.out.println("You chose to remove a student.");
        break;

    case "3":
        System.out.println("You chose to view students.");
        break;

    case "4":
        System.out.println("You chose to add or update a grade.");
        break;

    case "5":
        System.out.println("You chose to view reports.");
        break;

    case "6":
        System.out.println("You chose to save students.");
        break;

    case "7":
        System.out.println("You chose to load students.");
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