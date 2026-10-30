/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import java.util.LinkedHashMap;
import java.util.Map;

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
    // LinkedHashMap is a HashMap that also remebers the order
    // students were added, so lists come out in a sensible order.
    private final Map<String, Student> stents = new LinkedHashMap<>();
    
    // --------- Rules for checking input ---------
    
    // Makes sure a grade is between 0 and 100
    static void requireSafeText(String text, String fieldName) {
        if (text == null || text.trim(). isEmpty()) {
            throw new IllegalArgumentException (fieldName + "cannot be blank.");
        }
        for (char c : FORBIDDEN_CHARACTERS. toCharArray()) {
            if (text.indexOf(c) >= 0) {
                throw new IllegalArgumentException (
                        fieldName + "cannot contain a comma, semicolon, or colon.");
            }
        }
    }
    
    // Remves extra space from an ID and checks that it's valid
    private static String cleanId(String id) {
        requireSafeText(id, "Student ID");
        return id.trim();
    }
}
