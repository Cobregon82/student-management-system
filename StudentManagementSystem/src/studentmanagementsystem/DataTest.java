/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

/**
 * DataTest checks the system and data code without the menu:
 * adding, updating, removing, error messages, and saving/loading.
 * To run it: right-click this file and choose "RunFile".
 * 
 * @author carmenobregon
 */
public class DataTest {

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        
        System.out.println("--- Adding students ---");
        sms.addStudent(new UndergradStudent("1001", "Carmen Obregon"));
        sms.addStudent(new GradStudent("2001", "Jessica Hernandez "));
        sms.addStudent(new UndergradStudent("1002", "Tara Cox"));
        System.out.println("Students in system: " + sms.getStudentCount());
        System.out.println("\n--- Adding and updating grades ---");
        sms.setGrade("1001", "Math", 92.5);
        sms.setGrade("1001", "English", 90);
        sms.setGrade("2001", "History", 95);
        sms.setGrade("1002", "Math", 71);
        sms.setGrade("1002", "math", 78);    // same subject, so it updates it
        System.out.println("Tara's Math grade: "
                + sms.findStudent("1002").getSubject("Math").getGrade());
        
Reports.showHighestAndLowest(sms);
Reports.showStudentsSortedByGrade(sms);
        System.out.println("\n--- Updating a name ---");
        sms.updateStudentName("1002", "Tara Cox-Smith");
        System.out.println("Student 1002 is now: " + sms.findStudent("1002").getName());
                
        System.out.println("\n--- Things that should be refused ---");
        try {
            sms.addStudent(new UndergradStudent("1001", "Someone Else"));
            System.out.println("Duplicate ID: NOT refused (problem!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Duplicate ID refused: " + e.getMessage());
        }
        try {
            sms.setGrade("1001", "Science", 105);
            System.out.println("Grade over 100: NOT refused (problem!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Grade over 100 refused: " + e.getMessage());
        }
        try {
            sms.removeStudent("9999");
            System.out.println("Missing student: NOT refused (problem!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Missing student refused: " + e.getMessage());
        }
        try {
            sms.updateStudentName("1001", "Obregon, Carmen");
            System.out.println("Name with comma: NOT refused (problem!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Name with comma refused: " + e.getMessage());
        }
        
        System.out.println("\n--- Removing a student ---");
        Student removed = sms.removeStudent("1002");
        System.out.println("Removed " + removed.getName()
                + ". Students left: " + sms.getStudentCount());
        
        System.out.println("\n--- Saving and loading ---");
        sms.saveToFile("test_students.txt");
        StudentManagementSystem copy = new StudentManagementSystem();
        copy.loadFromFile("test_students.txt");
        for (Student s : copy.getAllStudents()) {
            System.out.println(s.getId() + " " + s.getName()
                    + " average " + s.getAverage()
                    + " passing: " + s.isPassing());
        }
        
        System.out.println("\n--- Loading a file that doesn't exist ---");
        copy.loadFromFile("no_such_file.txt");
        System.out.println("Students still in system: " + copy.getStudentCount());
    }
}
