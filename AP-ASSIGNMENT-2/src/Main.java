import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static CourseCatalog catalog = new CourseCatalog();
    public static List<Complaint> complaints = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();
    public static List<Professor> professors = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin@iiitd", "Admin", catalog, students, complaints);

        while (true) {
            System.out.println("Welcome to the University Course Registration System");
            System.out.println("1. Sign Up as Student");
            System.out.println("2. Sign Up as Professor");
            System.out.println("3. Login as Admin");
            System.out.println("4. Login as Student");
            System.out.println("5. Login as Professor");
            System.out.println("6. Login as Teaching Assistant");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    signUpStudent(scanner);
                    break;
                case 2:
                    signUpProfessor(scanner);
                    break;
                case 3:
                    loginAdmin(scanner, admin);
                    break;
                case 4:
                    loginStudent(scanner);
                    break;
                case 5:
                    loginProfessor(scanner);
                    break;
                case 6:
                    loginTeachingAssistant(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void signUpStudent(Scanner scanner) {
        System.out.println("Sign up as Student:");
        System.out.print("Email: ");
        String studentEmail = scanner.nextLine();
        System.out.print("Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Password: ");
        String studentPassword = scanner.nextLine();
        System.out.print("Enter your semester (1-8): ");
        int studentSemester = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(studentEmail, studentName, studentPassword, studentSemester);
        students.add(student);
        System.out.println("Student signed up successfully!");
    }

    private static void signUpProfessor(Scanner scanner) {
        System.out.println("Sign up as Professor:");
        System.out.print("Email: ");
        String professorEmail = scanner.nextLine();
        System.out.print("Name: ");
        String professorName = scanner.nextLine();
        System.out.print("Password: ");
        String professorPassword = scanner.nextLine();
        Professor professor = new Professor(professorEmail, professorName, professorPassword, catalog);
        professors.add(professor);
        System.out.println("Professor signed up successfully!");
    }

    private static void loginAdmin(Scanner scanner, Admin admin) {
        System.out.println("Login as Admin:");
        System.out.print("Email: ");
        String adminEmail = scanner.nextLine();
        System.out.print("Password: ");
        String adminPassword = scanner.nextLine();

        try {
            // This will now throw an exception if login fails
            if (admin.login(adminEmail, adminPassword)) {
                adminMenu(scanner, admin);
            }
            throw new InvalidLoginException("please give valid admin credentials");
        } catch (InvalidLoginException e) {
            // Catch the exception and print the error message
            System.out.println(e.getMessage());
        }
    }



    private static void adminMenu(Scanner scanner, Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professors to Courses");
            System.out.println("4. Handle Complaints");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.manageCourseCatalog(scanner);
                    break;
                case 2:
                    admin.manageStudentRecords(scanner);
                    break;
                case 3:
                    admin.assignProfessorsToCourses(scanner);
                    break;
                case 4:
                    admin.handleComplaints(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void loginStudent(Scanner scanner) {
        System.out.println("Login as a Student:");
        System.out.print("Email: ");
        String loginStudentEmail = scanner.nextLine();
        System.out.print("Password: ");
        String loginStudentPassword = scanner.nextLine();

        try {
            for (Student student : students) {
                if (student.login(loginStudentEmail, loginStudentPassword)) {
                    return;
                }
            }
            throw new InvalidLoginException("No student found with the provided email , please give valid login credential.");
        } catch (InvalidLoginException e) {
            // Handling the  invalid login exception
            System.out.println(e.getMessage());
        }
    }


    private static void loginTeachingAssistant(Scanner scanner) {
        System.out.println("Login as Teaching Assistant:");
        System.out.print("Email: ");
        String loginTAEmail = scanner.nextLine();
        System.out.print("Password: ");
        String loginTAPassword = scanner.nextLine();

        try {
            // Iterating through the list of students to find a matching TA
            for (Student student : students) {
                if (student.getEmail().equals(loginTAEmail)) {
                    if (student.getPassword().equals(loginTAPassword)) {
                        // Get all courses where this TA is assigned to the courses
                        List<Course> taCourses = catalog.getCoursesByTeachingAssistantEmail(loginTAEmail);
                        if (!taCourses.isEmpty()) {
                            System.out.println("Teaching Assistant logged in successfully.");
                            ((TeachingAssistant) student).showMenu();  // Show TA-specific menu
                        } else {
                            System.out.println("You are not assigned as a Teaching Assistant for any course.");
                        }
                        return;  // Exit if login is successful
                    } else {
                        throw new InvalidLoginException("Invalid password for the Teaching Assistant.");
                    }
                }
            }
            throw new InvalidLoginException("No Teaching Assistant found with the provided email.");
        } catch (InvalidLoginException e) {
            System.out.println(e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Error: Could not access Teaching Assistant functionalities.");
        }
    }

    private static void studentMenu(Scanner scanner, Student student) {
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Schedule");
            System.out.println("5. View SGPA/CGPA");
            System.out.println("6. Submit Complaint");
            System.out.println("7. View Complaint Status");
            System.out.println("8. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter your semester number (1-8):");
                    int semester = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    catalog.displayCoursesForSemester(semester);
                    break;
                case 2:
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.nextLine();
                    student.registerForCourse(catalog, courseCode);
                    break;
                case 3:
                    System.out.print("Enter course code to drop: ");
                    String dropCourseCode = scanner.nextLine();
                    System.out.print("Enter the current date (YYYY-MM-DD): ");
                    String currentDate = scanner.next();
                    student.dropCourse(catalog);
                    break;
                case 4:
                    student.viewSchedule();
                    break;
                case 5:
                    student.viewSGPACGPA();
                    break;
                case 6:
                    System.out.print("Enter your complaint: ");
                    String complaintText = scanner.nextLine();
                    Complaint complaint = new Complaint(student.getEmail(), complaintText);
                    complaints.add(complaint);
                    System.out.println("Complaint submitted successfully.");
                    break;
                case 7:
                    student.viewComplaintStatus(complaints);
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void loginProfessor(Scanner scanner) {
        System.out.println("Login as Professor:");
        System.out.print("Email: ");
        String loginProfessorEmail = scanner.nextLine();
        System.out.print("Password: ");
        String loginProfessorPassword = scanner.nextLine();

        try {
            for (Professor professor : professors) {
                if (professor.login(loginProfessorEmail, loginProfessorPassword)) {
                    professorMenu(scanner, professor);
                    return; // Exit the method on successful login
                }
            }
            throw new InvalidLoginException("No professor found with the provided email, please give valid login credential.");
        } catch (InvalidLoginException e) {
            // Handle the invalid login exception
            System.out.println(e.getMessage());
        }
    }

    private static void professorMenu(Scanner scanner, Professor professor) {
        while (true) {
            System.out.println("\nProfessor Menu:");
            System.out.println("1. View Assigned Courses");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    professor.viewCourses();
                    break;
                case 2:
                    professor.viewEnrolledStudents(catalog);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }

    public static Student getStudentByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }
}
