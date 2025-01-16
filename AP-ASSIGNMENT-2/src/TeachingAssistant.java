import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeachingAssistant extends Student {
    private List<Course> assignedCourses;
    private Course assignedCourse;

    public TeachingAssistant(String email, String name, String password, Course assignedCourse) {
        super(email, name, password);
        this.assignedCourses = new ArrayList<>();
        this.assignedCourse = assignedCourse;
    }

    public Course getAssignedCourse() {
        return assignedCourse;
    }

    @Override
    public boolean login(String email, String password) {
        if (this.getEmail().equals(email) && validatePassword(password)) {
            System.out.println("Teaching Assistant logged in successfully.");
            showMenu();
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTeaching Assistant Menu:");
            System.out.println("1. View Assigned Courses");
            System.out.println("2. Update Student Grades");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    viewAssignedCourses();
                    break;
                case 2:
                    updateStudentGrades(scanner);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAssignedCourses() {
        System.out.println("\nAssigned Courses:");
        // Get the list of assigned courses to the TA for a particular course
        List<Course> taCourses = getAssignedCourses();  // Fetch for the assigned courses for the TA
        boolean found = false;
        for (Course course : taCourses) {
            System.out.println("Course Code: " + course.getCode() + ", Course Title: " + course.getTitle());
            found = true;
        }
        if (!found) {
            System.out.println("No courses assigned to you.");
        }
    }

    private void updateStudentGrades(Scanner scanner) {
        System.out.println("\nSelect a course to update grades:");

        viewAssignedCourses();

        System.out.print("Enter the course code: ");
        String courseCode = scanner.nextLine();

        Course course = Main.catalog.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (!course.getTeachingAssistants().contains(this)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        System.out.println("Students enrolled in " + course.getTitle() + ":");

        List<Student> enrolledStudents = course.getEnrolledStudents();
        if (enrolledStudents.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
            return;
        }

        System.out.print("Enter student email to view or assign grade: ");
        String studentEmail = scanner.nextLine();

        Student selectedStudent = null;
        for (Student student : enrolledStudents) {
            if (student.getEmail().equals(studentEmail)) {
                selectedStudent = student;
                break;
            }
        }

        if (selectedStudent == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter the grade for " + selectedStudent.getName() + ": ");
        String grade = scanner.nextLine();

        selectedStudent.addGrade(courseCode, grade);
        System.out.println("Grade " + grade + " assigned to " + selectedStudent.getName() + " for course " + courseCode + ".");
    }

    // this will ensure valid integer input
    private int getValidInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

}
