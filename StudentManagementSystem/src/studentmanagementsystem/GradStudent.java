
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

/**
 * GradStudent is a graduate student.
 * Grad students need an average of 70 or higher to pass.
 * @author carmenobregon
 */
public class GradStudent extends Student {

    // Constructor: passes the ID and name up to the Student class
    public GradStudent(String id, String name) {
        super(id, name);
    }
    
    //Grad students own rule for passing (70 or higher)
    @Override
    public boolean isPassing() {
        return getAverage() >= 70;
    }
}

