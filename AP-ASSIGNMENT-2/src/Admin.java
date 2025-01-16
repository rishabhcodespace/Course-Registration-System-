import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class Admin extends Person implements User {
    private static final String FIXED_PASSWORD = "admin123";
    private CourseCatalog catalog;
    private List<Student> students;
    private List<Complaint> complaints;
    private LocalDate dropStartDate;
    private LocalDate dropEndDate;

    public Admin(String email, String name, CourseCatalog catalog, List<Student> students, List<Complaint> complaints) {
        super(email, name, FIXED_PASSWORD);
        this.catalog = catalog;
        this.students = students;
        this.complaints = complaints;
    }

    @Override
    public void signUp(String email, String name, String password) {
        // No sign-up is  needed for an Admin
    }

    @Override
    public boolean login(String email, String password) {
        if (this.getEmail().equals(email) && validatePassword(password)) {
            System.out.println("Admin logged in successfully.");
            manageMenu();
        } else {
//            System.out.println("Invalid credentials.");
        }
        return false;
    }

    private void manageMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professors to Courses");
            System.out.println("4. Handle Complaints");
            System.out.println("5. Assign Grades");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageCourseCatalog(scanner);
                    break;
                case 2:
                    manageStudentRecords(scanner);
                    break;
                case 3:
                    assignProfessorsToCourses(scanner);
                    break;
                case 4:
                    handleComplaints(scanner);
                    break;
                case 5:
                    assignGrades(scanner);
                    break;
                case 6:
                    System.out.println("log out successfully");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void manageCourseCatalog(Scanner scanner) {
        System.out.println("1. View All Courses");
        System.out.println("2. View Courses for a Specific Semester");
        System.out.println("3. Add Course");
        System.out.println("4. Remove Course");
        System.out.println("5. Update Course Details");
        int choice = scanner.nextInt();
        scanner.nextLine(); // for consuming new line

        switch (choice) {
            case 1:
                catalog.displayAvailableCourses();
                break;
            case 2:
                System.out.println("Enter semester number (1-8):");
                int semester = scanner.nextInt();
                scanner.nextLine();
                catalog.displayCoursesForSemester(semester);
                break;
            case 3:
                addCourse(scanner);
                break;
            case 4:
                removeCourse(scanner);
                break;
            case 5:
                updateCourseDetails(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addCourse(Scanner scanner) {
        System.out.println("Enter semester number (1-8):");
        int semester = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter course code:");
        String code = scanner.nextLine();
        System.out.println("Enter course title:");
        String title = scanner.nextLine();
        System.out.println("Enter professor name:");
        String professor = scanner.nextLine();
        System.out.println("Enter credits:");
        int credits = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Course newCourse = new Course(code, title, professor, credits,semester);
        newCourse.setSemester(semester);
        catalog.addCourse(semester, newCourse);
        System.out.println("Course added successfully.");
    }

    private void removeCourse(Scanner scanner) {
        System.out.println("Enter course code to remove:");
        String code = scanner.nextLine();
        catalog.removeCourse(code);
        System.out.println("Course removed successfully.");
    }

    private void updateCourseDetails(Scanner scanner) {
        System.out.println("Enter course code to update:");
        String code = scanner.nextLine();
        Course course = catalog.getCourseByCode(code);
        if (course != null) {
            System.out.println("Enter new course title (leave blank to keep the same title):");
            String title = scanner.nextLine();
            if (!title.isEmpty()) {
                course.setTitle(title);
            }
            System.out.println("Enter new credits (enter 0 to keeping the same):");
            int credits = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (credits > 0) {
                course.setCredits(credits);
            }
            System.out.println("Course details updated successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public void manageStudentRecords(Scanner scanner) {
        System.out.println("1. View Student Records");
        System.out.println("2. Update Student Records");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewStudentRecords();
                break;
            case 2:
                updateStudentRecords(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void viewStudentRecords() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println("Email: " + student.getEmail() + ", Name: " + student.getName());
            }
        }
    }

    private void updateStudentRecords(Scanner scanner) {
        System.out.println("Enter student email to update:");
        String email = scanner.nextLine();
        Student student = findStudentByEmail(email);
        if (student != null) {
            System.out.println("Enter new name (leave blank to keep current):");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                student.setName(name);  // Updating the student's name with the new name
            }
            System.out.println("Student record updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }


    private Student findStudentByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }

    public void assignProfessorsToCourses(Scanner scanner) {
        System.out.println("Enter course code to assign a professor:");
        String courseCode = scanner.nextLine();

        // Find the course in the catalog by its course code
        Course course = catalog.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        System.out.println("Enter professor email:");
        String professorEmail = scanner.nextLine(); // Use email for identification of a prof

        Professor professor = catalog.findProfessorByEmail(professorEmail);
        if (professor == null) {
            System.out.println("Professor not found!");
            return;
        }

        // Assign the professor to the course
        catalog.assignProfessorToCourse(courseCode, String.valueOf(professor));
        // Add the course to the professor's assigned list
        professor.addAssignedCourse(course);

        System.out.println("Professor assigned successfully to course " + courseCode + ".");
    }

    public void assignGrades(Scanner scanner) {
        System.out.println("Assigning Grades...");

        System.out.println("Enter the semester for which you want to assign grades: ");
        int semester = Integer.parseInt(scanner.nextLine());

        System.out.println("Courses available in semester " + semester + ": ");
        for (Course course :  catalog.getCourses()) {
            if (course.getSemester() == semester) {
                System.out.println(course.getCode() + ": " + course.getTitle());
            }
        }

        System.out.println("Enter the course code to assign grades for: ");
        String courseCode = scanner.nextLine();

        Course selectedCourse = null;
        for (Course course : catalog.getCourses()) {
            if (course.getCode().equals(courseCode)) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse == null) {
            System.out.println("Course not found!");
            return;
        }
        List<Student> enrolledStudents = selectedCourse.getEnrolledStudents();
        if (enrolledStudents.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
            return;
        }

        // Step 6: Assigning the  grades to each enrolled student in a course
        for (Student student : enrolledStudents) {
            System.out.println("Enter grade for student " + student.getName() + " (ID: " + student.getEmail() + "): ");
            String grade = scanner.nextLine();
            student.addGrade(courseCode, grade);
        }

        System.out.println("Grades assigned successfully for course " + courseCode + ".");
    }

    public void handleComplaints(Scanner scanner) {
        System.out.println("1. View Complaints");
        System.out.println("2. Resolve Complaint");
        System.out.println("3. Filter Complaints");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewComplaints();
                break;
            case 2:
                resolveComplaint(scanner);
                break;
            case 3:
                filterComplaints(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void viewComplaints() {
        System.out.println("Complaints:");
        for (int i = 0; i < complaints.size(); i++) {
            Complaint complaint = complaints.get(i);
            System.out.println(i + ". Description: " + complaint.getDescription() + ", Status: " + complaint.getStatus());
        }
    }

    private void resolveComplaint(Scanner scanner) {
        System.out.println("Enter complaint index to resolve (0 to " + (complaints.size() - 1) + "):");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < complaints.size()) {
            Complaint complaint = complaints.get(index);
            complaint.resolveComplaint();  // update the status of a compliant
            System.out.println("Complaint resolved.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void filterComplaints(Scanner scanner) {
        System.out.println("Enter status to filter (Pending/Resolved):");
        String status = scanner.nextLine();

        boolean found = false;  // Flag to track if any complaints were found or not

        for (Complaint complaint : complaints) {
            if (complaint.getStatus().equalsIgnoreCase(status)) {
                System.out.println("Description: " + complaint.getDescription() + ", Status: " + complaint.getStatus());
                found = true;  // Set flag to true if a complaint is found
            }
        }

        if (!found) {
            System.out.println("No complaints found with the status: " + status);
        }
    }

    public void setDropPeriod(LocalDate startDate, LocalDate endDate) {
        this.dropStartDate = startDate;
        this.dropEndDate = endDate;
    }

    public boolean isDropOpen(LocalDate currentDate) {
        // Check if the current date is within the deadline given by admin
        return (currentDate.isEqual(dropStartDate) || currentDate.isAfter(dropStartDate))
                && (currentDate.isEqual(dropEndDate) || currentDate.isBefore(dropEndDate));
    }
}
