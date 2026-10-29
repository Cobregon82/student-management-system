/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

/**
 *
 * @author carmenobregon
 */
public class Subject {
    
    // private = only this class can change these (encapsulation)
    private String name;
    private double grade;
    
    // Constructor: runs when a new Subject is created
    public Subject(String name, double grade) {
        this.name = name;
        setGrade(grade);  //uses the setter so the 0-100 check runs
    }
    
    public String getName() {
        return name;
    }
    
    public double getGrade() {
        return grade;
    }
    
    // Change the grade, but only if it's between 0 and 100
    public void setGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        this.grade = grade;
    }
}
