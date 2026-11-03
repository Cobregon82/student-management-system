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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    
    // --------- Loading from a file ---------
    
    // Loads students from a text file, replacing the students
    // currently in the system. Lines that can't be read are skipped
    // with a warning, so one bad line doesn't stop the rest.
    // Returns true if the file was read, false if it was missing
    // or couldn't be opened.
    public boolean loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("No saved file named " + fileName
                    + " was found. Starting with an empty list.");
            return false;
        }
        
        // Put the students from the file in a temporary spot first
        // That way if the file has a problem, we don't lose the students we already have. 
        Map<String, Student> loaded = new LinkedHashMap<>();
        int lineNumber = 0;
        int skipped = 0;
        
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;  // skip blank lines
                }
                try {
                    Student student = parseFileLine(line);
                    if (loaded.containsKey(student.getId())) {
                        throw new IllegalArgumentException("duplicate ID " + student.getId());
                    }
                    loaded.put(student.getId(), student);
                } catch (IllegalArgumentException e) {
                    skipped++;
                    System.out.println("Skipped line " + lineNumber + " (" + e.getMessage() + ")");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + fileName + ": " + e.getMessage());
            return false;
        }
        
        students.clear();
        students.putAll(loaded);
        System.out.println("Loaded " + loaded.size() + " student(s) from " + fileName
                + " (" + skipped + " line(s) skipped).");
                return true;
            }
    
    // Turns one line of the save file back into a student.
    // Throws an error if the line isn't in the right format.
    private Student parseFileLine(String line) {
        // The -1 keeps an empty last part, for a student with no grades
        String[] parts = line.split(",", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException( 
                    "expected 4 parts separated by commas, found " + parts.length);
        }
        String type = parts[0].trim();
        String id = cleanId(parts[1]);
        String name = parts[2].trim();
        requireSafeText(name, "Name");
        
        Student student;
        if (type.equals("U")) {
            student = new UndergradStudent(id, name);
        } else if (type.equals("G")) {
            student = new GradStudent(id, name);
        } else {
            throw new IllegalArgumentException("unknown student type '" + type + "'");
        }
        
        String gradesPart = parts[3].trim();
        if (!gradesPart.isEmpty()) {
            for (String entry : gradesPart.split(";")) {
                String[] pair = entry.split(":");
                if (pair.length != 2) {
                    throw new IllegalArgumentException(
                            "grade entry '" + entry + "' should look like Math:92.5");
                }
                double grade;
                try {
                    grade = Double.parseDouble(pair[1].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "grade '" + pair[1].trim() + "' is not a number");
                }
                validateGrade(grade);
                student.addSubject(new Subject(pair[0].trim(), grade));
            }
        }
        return student;
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
