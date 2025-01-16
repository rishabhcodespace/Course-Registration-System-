import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Professor extends Person implements User {
    private List<Course> assignedCourses; // List to hold assigned courses
    private CourseCatalog catalog;

    public Professor(String email, String name, String password, CourseCatalog catalog) {
        super(email, name, password);
        this.assignedCourses = new ArrayList<>(); // Initializing the list
        this.catalog = catalog; // Initialize the CourseCatalog
    }

    @Override
    public void signUp(String email, String name, String password) {
    }

    @Override
    public boolean login(String email, String password) {
        if (this.getEmail().equals(email) && validatePassword(password)) {
            System.out.println("Professor logged in successfully.");
            showMenu();
            return true;
        } else {
//            System.out.println("Invalid credentials.");
            return false;
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. View Assigned Courses");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Assign Grades");
            System.out.println("4. Update Course Details");
            System.out.println("5. Assign Teaching Assistant (TA)");
            System.out.println("6. Remove Teaching Assistant (TA)");
            System.out.println("7. View Feedback");
            System.out.println("8. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    viewEnrolledStudents(catalog);
                    break;
                case 3:
                    assignGrades(scanner);
                    break;
                case 4:
                    updateCourseDetails(scanner);
                    break;
                case 5:
                    assignTeachingAssistant(scanner);
                    break;
                case 6:
                    removeTeachingAssistant(scanner);
                    break;
                case 7:
                    viewFeedback(scanner);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void viewCourses() {
        if (assignedCourses.isEmpty()) {
            System.out.println("No courses assigned.");
        } else {
            System.out.println("Courses assigned to you:");
            for (Course course : assignedCourses) {
                System.out.println(course.displayCourseDetails());
            }
        }
    }

    public void viewEnrolledStudents(CourseCatalog catalog) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course code to view enrolled students:");
        String code = scanner.nextLine();

        Course course = catalog.getCourseByCode(code);
        if (course != null) {
            List<Student> enrolledStudents = course.getEnrolledStudents();
            if (enrolledStudents.isEmpty()) {
                System.out.println("No students enrolled in this course.");
            } else {
                for (Student student : enrolledStudents) {
                    System.out.println("Student Email: " + student.getEmail() + ", Name: " + student.getName());
                }
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public void assignGrades(Scanner scanner) {
        System.out.println("Assigning Grades...");

        try {
            // Step 1: Ask for the semester
            System.out.println("Enter the semester for which you want to assign grades: ");
            int semester = Integer.parseInt(scanner.nextLine());

            // Step 2: Display courses assigned to the professor for the selected semester
            System.out.println("Courses assigned to you in semester " + semester + ": ");
            for (Course course : assignedCourses) {
                if (course.getSemester() == semester) {
                    System.out.println(course.getCode() + ": " + course.getTitle());
                }
            }
            System.out.println("Enter the course code to assign grades for: ");
            String courseCode = scanner.nextLine();

            Course selectedCourse = null;
            for (Course course : assignedCourses) {
                if (course.getCode().equals(courseCode)) {
                    selectedCourse = course;
                    break;
                }
            }

            // Check if the course was found or not
            if (selectedCourse == null) {
                System.out.println("You are not assigned to this course!");
                return;
            }

            //getting the enrolled student in a particular course
            List<Student> enrolledStudents = selectedCourse.getEnrolledStudents();
            if (enrolledStudents.isEmpty()) {
                System.out.println("No students are enrolled in this course.");
                return;
            }

            // Step 6: Assigning grades to each enrolled student
            for (Student student : enrolledStudents) {
                System.out.println("Enter grade for student " + student.getName() + " (ID: " + student.getEmail() + "): ");
                String grade = scanner.nextLine();
                student.addGrade(courseCode, grade);  // Assuming addGrade method is implemented
            }

            System.out.println("Grades assigned successfully for course " + courseCode + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while assigning grades: " + e.getMessage());
        }
    }

    private void updateCourseDetails(Scanner scanner) {
        System.out.println("Enter course code to update details:");
        String courseCode = scanner.nextLine();
        Course course = catalog.getCourseByCode(courseCode);
        if (course != null) {
            System.out.println("Updating details for course: " + course.getCode());

            // Updating the  syllabus of a course
            System.out.println("Enter new syllabus (leave blank to keep current):");
            String syllabus = scanner.nextLine();
            if (!syllabus.isEmpty()) {
                course.setSyllabus(syllabus);
            }

            // Update class timings of a course
            System.out.println("Enter new class timings (leave blank to keep current):");
            String classTimings = scanner.nextLine();
            if (!classTimings.isEmpty()) {
                course.setClassTimings(classTimings);
            }

            // Updating the credits of a cousre
            System.out.println("Enter new number of credits (enter -1 to keep current):");
            int credits = scanner.nextInt();
            scanner.nextLine();
            if (credits != -1) {
                course.setCredits(credits);
            }

            System.out.println("Details updated successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public void addAssignedCourse(Course course) {
        assignedCourses.add(course);
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void viewFeedback(Scanner scanner) {
        if (assignedCourses.isEmpty()) {
            System.out.println("No courses assigned to view feedback.");
            return;
        }

        System.out.println("Courses you can view feedback for:");
        for (int i = 0; i < assignedCourses.size(); i++) {
            Course course = assignedCourses.get(i);
            System.out.println((i + 1) + ": " + course.getTitle());
        }

        System.out.print("Select a course to view feedback (1-" + assignedCourses.size() + "): ");
        int courseIndex = scanner.nextInt() - 1; // Adjust for 0-based index
        scanner.nextLine(); // Consume newline

        // Validating the course selected by prof
        if (courseIndex < 0 || courseIndex >= assignedCourses.size()) {
            System.out.println("Invalid course selection.");
            return;
        }

        Course selectedCourse = assignedCourses.get(courseIndex);
        List<Feedback<?>> feedbackList = selectedCourse.getFeedbackList();

        System.out.println("Feedback for course: " + selectedCourse.getTitle());
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available.");
        } else {
            for (Feedback<?> feedback : feedbackList) {
                System.out.println(feedback);
            }
        }
    }

    // Assigning  TA by prof
    public void assignTeachingAssistant(Scanner scanner) {
        System.out.println("Courses assigned to you:");
        for (int i = 0; i < assignedCourses.size(); i++) {
            Course course = assignedCourses.get(i);
            System.out.println((i + 1) + ": " + course.getTitle());
        }

        System.out.print("Select a course to assign a TA (enter number 1-" + assignedCourses.size() + "): ");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (courseIndex < 0 || courseIndex >= assignedCourses.size()) {
            System.out.println("Invalid course selection.");
            return;
        }

        Course selectedCourse = assignedCourses.get(courseIndex);

        System.out.println("Enter email of the student to assign as TA:");
        String email = scanner.nextLine();
        Student student = Main.getStudentByEmail(email);

        if (student != null) {
            // Check if student is already a TA for the course
            if (selectedCourse.getTeachingAssistants().contains(student)) {
                System.out.println("Student is already a TA for this course.");
                return;
            }

            // assigning ta to a particular course
            TeachingAssistant ta = new TeachingAssistant(student.getEmail(), student.getName(), student.getPassword(), selectedCourse);
            selectedCourse.addTeachingAssistant(ta);

           // Main.catalog().addTeachingAssistant(ta);

            System.out.println("Teaching Assistant " + ta.getEmail() + " added to " + selectedCourse.getTitle());
        } else {
            System.out.println("No valid student found.");
        }
    }

    // Removing TA from a course
    private void removeTeachingAssistant(Scanner scanner) {
        System.out.println("Enter course code to remove TA from:");
        String courseCode = scanner.nextLine();
        Course course = catalog.getCourseByCode(courseCode);

        if (course != null) {
            System.out.println("Enter email of the TA to remove:");
            String taEmail = scanner.nextLine();

            TeachingAssistant ta = course.getTeachingAssistantByEmail(taEmail);

            if (ta != null) {
                course.getTeachingAssistants().remove(ta);
                System.out.println("Teaching Assistant " + ta.getName() + " has been removed.");
            } else {
                System.out.println("No Teaching Assistant found with email: " + taEmail);
            }
        } else {
            System.out.println("Course not found.");
        }
    }
}
