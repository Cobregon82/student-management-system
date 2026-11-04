package studentmanagementsystem;

import java.util.List;

public class Reports {

    // Report 1: Display each student's average grade
    public static void showStudentAverages(
            StudentManagementSystem system) {

        List<Student> students = system.getAllStudents();

        System.out.println("\n--- STUDENT AVERAGES ---");

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.printf(
                "ID: %s | Name: %s | Average: %.2f%n",
                student.getId(),
                student.getName(),
                student.getAverage()
            );
        }
   }
    // Report 2: Highest and lowest student averages
    public static void showHighestAndLowest(
            StudentManagementSystem system) {

        List<Student> students = system.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        Student highest = students.get(0);
        Student lowest = students.get(0);

        for (Student student : students) {
            if (student.getAverage() > highest.getAverage()) {
                highest = student;
            }

            if (student.getAverage() < lowest.getAverage()) {
                lowest = student;
            }
        }

        System.out.println("\n--- HIGHEST AND LOWEST AVERAGES ---");

        System.out.printf("Highest: %s | Average: %.2f%n",
                highest.getName(), highest.getAverage());

        System.out.printf("Lowest: %s | Average: %.2f%n",
                lowest.getName(), lowest.getAverage());
    }

    // Report 3: Sort students by average grade
    public static void showStudentsSortedByGrade(
            StudentManagementSystem system) {

        List<Student> students =
                new java.util.ArrayList<>(system.getAllStudents());

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        students.sort((a, b) ->
                Double.compare(b.getAverage(), a.getAverage()));

        System.out.println("\n--- STUDENTS SORTED BY GRADE ---");

        for (Student student : students) {
            System.out.printf(
                    "ID: %s | Name: %s | Average: %.2f%n",
                    student.getId(),
                    student.getName(),
                    student.getAverage()
            );
        }
    }


}
