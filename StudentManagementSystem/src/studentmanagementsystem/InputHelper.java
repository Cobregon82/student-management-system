/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;
import java.util.Scanner;

/**
 *InputHelper reads what the user types and keeps asking until
 * they type something valid, so bad input never crashes the program.
 * The menu (Main) uses these instead of calling Scanner directly.
 * 
 * @author carmenobregon
 */
public class InputHelper {
   
    // Nobody needs to create an InputHelper object,
    // because all the methods are static
    private InputHelper() {
    }
    
// Asks for a whole number between min and max.
// Keeps asking until the user types one.
public static int readInt(Scanner in, String prompt, int min, int max) {
    while (true) {
        System.out.print(prompt);
        String line = in.nextLine().trim();
        try {
            int value = Integer.parseInt(line);
            if (value <min || value > max) {
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            } else {
                return value;
            }
        } catch (NumberFormatException e) {
            System.out.println("'" + line + "' is not a whole number. Please try again.");
        }
    }
}   

// Asks for a grade between 0 and 100 (decimals like 88.5 are okay)
public static double readGrade(Scanner in, String prompt) {
    while (true) {
        System.out.print(prompt);
        String line = in.nextLine().trim();
        try {
            double grade = Double.parseDouble(line);
            StudentManagementSystem.validateGrade(grade);
            return grade;
        } catch (NumberFormatException e) {
            System.out.println("'" + line + "' is not a number. Please enter a grade like 85 or 92.5");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

// Asks for text like a name, ID or subject. It can't be blank
// and can't contain , ; or : (those would break the save file).
public static String readText(Scanner in, String prompt, String fieldName) {
    while (true) {
        System.out.print(prompt);
        String line = in.nextLine().trim();
        try {
            StudentManagementSystem.requireSafeText(line, fieldName);
            return line;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

// Asks a yes/no question. Accepts y, yes, n or no.
// Returns true for yes, false for no.
public static boolean readYesNo(Scanner in, String prompt) {
    while (true) {
        System.out.print(prompt + " (y/n): ");
        String line = in.nextLine().trim().toLowerCase();
        if (line.equals("y") || line.equals("yes")) {
            return true;
        }
        if (line.equals("n") || line.equals("no")) {
            return false;
        }
        System.out.println("Please type y or n.");
    }
}

}