/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Student is the parent class for every type of student.
 * It's abstract: you can't create a plain Student, only an
 * UndergradStudent or a GradStudent.
 * @author carmenobregon
 */
public abstract class Student {
    
    private String id;
    private String name;
    private ArrayList<Subject> subjects = new ArrayList<> ();
    
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Return the subjects as a read-only list, so other classes 
    // can look at them but can't add or remove any directly
    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }
    
    // Finds a subject by name (ignoring capitals), or returns null
    public Subject getSubject(String subjectName) {
        for (Subject s : subjects) {
            if (s.getName(). equalsIgnoreCase (subjectName)) {
                return s;
            }
        }
        return null;
    }
    
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    // Average of all grades, or 0 if there are no grades yet
    public double getAverage() {
        if (subjects.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (Subject s : subjects) {
            total += s.getGrade();
        }
        return total / subjects.size();
    }
    
    // Each type of student decides what "passing" means (polymorphism)
    public abstract boolean isPassing();
}
