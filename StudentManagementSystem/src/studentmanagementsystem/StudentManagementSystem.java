/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * StudentManagementSystem handles all of the system's operations
 * adding finding updating and removing students, and saving and
 * loading student records to and from a text file.
 * 
 * @author carmenobregon
 */
public class StudentManagementSystem {
   
    // These characters separate values in the save file
    // so they can't be part of a name, ID or subject
    private static final String FORBIDDEN_CHARACTERS = ",;:";
    
    // All students looked up by their ID.
    // LinkedHashMap is a HashMap that also remembers the order
    // students were added, so lists come out in a sensible order.
    private final Map<String, Student> students = new LinkedHashMap<>();
    
    // --------- Core operations: add, find, update, remove ---------
    
    // Adds a new student. Throws an error if the ID is blank or
    // already used, or if the name is invalid.
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("No student was given to add.");
        }
        String id = cleanId(student.getId());
        requireSafeText(student.getName(), "Name");
        
        if (students.containsKey(id)) {
            throw new IllegalArgumentException("A student with ID " + id + " already exists.");
        }
        students.put(id, student);
    }
    
     // Looks up a student by ID. Returns null if there's no match.
    public Student findStudent(String id) {
        if (id == null) {
            return null;
        }
        return students.get(id.trim());
    }
    
    // Change a student's name
    public void updateStudentName(String id, String newName) {
        Student student = getExistingStudent(id);
        requireSafeText(newName, "Name");
        student.setName(newName.trim());
    }
    
    // Adds a grade for a subject, or changes it if the student
    // already has that subject
    public void setGrade(String id, String subjectName, double grade) {
        Student student = getExistingStudent(id);
        requireSafeText(subjectName, "Subject");
        validateGrade(grade);
        
        Subject existing = student.getSubject(subjectName.trim());
        if (existing != null) {
            existing.setGrade(grade);
        } else {
            student.addSubject(new Subject(subjectName.trim(), grade));
        }
    }
    
    // Removes a student and returns the one that was removed
    public Student removeStudent(String id) {
        Student student = getExistingStudent(id);
        students.remove(id.trim());
        return student;
    }
    
    // Returns a copy of the list of all students (the reports use this)
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    // Returns how many students are in the system
    public int getStudentCount() {
        return students.size();
    }
    
    // Finds a student or throws a friendly error if they don't exist
    private Student getExistingStudent(String id) {
        Student student = findStudent(id);
        if (student == null) {
            throw new IllegalArgumentException("No student found with ID " + id +".");
        }
        return student;
    }
    
    // --------- Saving to a file ---------
    
    // Saves every student to a text file, one student per line.
    // File errors are caught here, so the program keeps running.
    // Returns true if the save worked, false if it failed.
    public boolean saveToFile(String fileName) {
        // try-with-resources: Java closes the file automatically,
        // even if an error happens partway through
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (Student student : students.values()) {
                out.println(toFileLine(student));
            }
            System.out.println("Saved " + students.size() + " student(s) to " + fileName + ".");
            return true;
        } catch (IOException e) {
              System.out.println("Could not save to " + fileName + ": " + e.getMessage());
              return false;
                    
        }
    }
    
    // Turns one student into one line of the save file, like:
    // U,1001,Carmen Obregon,Math:92.5;English:90.0
    private String toFileLine(Student student) {
        String type;
        if (student instanceof GradStudent) {
            type = "G";
        } else {
            type = "U";
        }
        
        StringBuilder grades = new StringBuilder();
        for (Subject subject : student.getSubjects()) {
            if (grades.length() > 0) {
                grades.append(";");
            }
            grades.append(subject.getName()).append(":").append(subject.getGrade());
        }
        return type + "," + student.getId() + "," + student.getName() + "," + grades;
    }
    
    // --------- Rules for checking input ---------
    
    // Makes sure a grade is between 0 and 100
    static void validateGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException(
                    "Grade must be between 0 and 100. You entered " + grade +".");
        }
    }
    
    // Makes sure text isn't blank and doesn't contain , ; or :
    static void requireSafeText(String text, String fieldName) {
        if (text == null || text.trim(). isEmpty()) {
            throw new IllegalArgumentException (fieldName + " cannot be blank.");
        }
        for (char c : FORBIDDEN_CHARACTERS. toCharArray()) {
            if (text.indexOf(c) >= 0) {
                throw new IllegalArgumentException (
                        fieldName + " cannot contain a comma, semicolon, or colon.");
            }
        }
    }
    
    // Removes extra space from an ID and checks that it's valid
    private static String cleanId(String id) {
        requireSafeText(id, "Student ID");
        return id.trim();
    }
}
