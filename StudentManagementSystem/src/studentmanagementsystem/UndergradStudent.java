/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

/**
 * An undergraduate student. Passes with an average of 60 or higher.
 * @author carmenobregon
 */
public class UndergradStudent extends Student {
    
    public UndergradStudent(String id, String name) {
        super(id, name);
    }
    
    @Override
    public boolean isPassing() {
        return getAverage() >= 60;
    }
}

