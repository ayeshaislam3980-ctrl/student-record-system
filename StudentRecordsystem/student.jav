package StudentRecordsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;

class Student {
    int id;
    String name;
    int age;
    String course;
    double marks;

    Student(int id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    void display() {
        System.out.println("------------------------------");
        System.out.println("Student ID : " + id);
        System.out.println("Name       : " + name);
        System.out.println("Age        : " + age);
        System.out.println("Course     : " + course);
        System.out.println("Marks      : " + marks);
        System.out.println("------------------------------");
    }
}

public class student {

    // ArrayList stores student records
    static ArrayList<Student> students = new ArrayList<>();

    // LinkedList stores a separate list of student IDs
    static LinkedList<Integer> studentIds = new LinkedList<>();

    // Stack stores deleted students for undo functionality
    static Stack<Student> deletedStudents = new Stack<>();

    static Scanner sc = new Scanner(System.in);

    // Find student by ID
    static Student findStudent(int id) {
        for (Student s : students) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    // Add student
    static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();

        if (age <= 0 || marks < 0 || marks > 100) {
            System.out.println("Invalid age or marks! Marks must be 0-100.");
            return;
        }

        Student s = new Student(id, name, age, course, marks);

        students.add(s);
        studentIds.add(id);

        System.out.println("Student added successfully!");
    }

    // View all students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    // Search student
    static void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(id);

        if (s != null) {
            s.display();
        } else {
            System.out.println("Student not found!");
        }
    }

    // Update student
    static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Course: ");
        String course = sc.nextLine();

        System.out.print("Enter New Marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();

        if (age <= 0 || marks < 0 || marks > 100) {
            System.out.println("Invalid age or marks! Update cancelled.");
            return;
        }

        s.name = name;
        s.age = age;
        s.course = course;
        s.marks = marks;

        System.out.println("Student updated successfully!");
    }

    // Delete student and save it on the stack
    static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        students.remove(s);
        studentIds.remove(Integer.valueOf(id));
        deletedStudents.push(s);

        System.out.println("Student deleted successfully!");
    }

    // Undo the most recent deletion
    static void undoDelete() {
        if (deletedStudents.isEmpty()) {
            System.out.println("No deleted student to restore.");
            return;
        }

        Student s = deletedStudents.pop();

        // Avoid restoring a duplicate ID
        if (findStudent(s.id) != null) {
            System.out.println("Cannot restore: Student ID already exists.");
            deletedStudents.push(s);
            return;
        }

        students.add(s);
        studentIds.add(s.id);

        System.out.println("Student restored successfully!");
        s.display();
    }

    // Display IDs stored in LinkedList
    static void viewStudentIds() {
        if (studentIds.isEmpty()) {
            System.out.println("No student IDs available.");
            return;
        }

        System.out.println("Student IDs (LinkedList): " + studentIds);
    }

    // Display deleted student stack
    static void viewDeletedStack() {
        if (deletedStudents.isEmpty()) {
            System.out.println("Deleted student stack is empty.");
            return;
        }

        System.out.println("Deleted Students (Stack):");

        for (int i = deletedStudents.size() - 1; i >= 0; i--) {
            deletedStudents.get(i).display();
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== STUDENT RECORD SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Undo Last Deletion");
            System.out.println("7. View Student IDs (LinkedList)");
            System.out.println("8. View Deleted Students (Stack)");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    undoDelete();
                    break;

                case 7:
                    viewStudentIds();
                    break;

                case 8:
                    viewDeletedStack();
                    break;

                case 9:
                    System.out.println("Exiting Student Record System.");
                    break;

                default:
                    System.out.println("Invalid choice! Enter 1-9.");
            }

        } while (choice != 9);

        sc.close();
    }
}
