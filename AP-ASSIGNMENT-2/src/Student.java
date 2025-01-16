import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Person implements User {
    private static List<Student> students = new ArrayList<>();
    private List<Course> enrolledCourses;
    private List<Grade> grades;
    private List<Complaint> complaints;
    private int semester;

    public Student(String email, String name, String password,int semester) {
        super(email, name, password);
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.semester=semester;
    }

    public Student(String email, String name, String password) {
        super(email, name, password);
    }

    @Override
    public void signUp(String email, String name, String password) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                System.out.println("Email already registered.");
                return;
            }
        }
        students.add(this);
        System.out.println("Student signed up successfully.");
    }

    @Override
    public boolean login(String email, String password) {
        if (this.getEmail().equals(email) && validatePassword(password)) {
            System.out.println("Student logged in successfully.");
            showMenu();
        } else {
//            System.out.println("Invalid credentials.");
        }
        return false;
    }

    public int getSemester() {
        return semester;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Track Academic Progress");
            System.out.println("4. Drop a Course");
            System.out.println("5. Submit Complaint");
            System.out.println("6. View Complaint Status");
            System.out.println("7. Give Feedback on Completed Courses");
            System.out.println("8. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter your semester number (1-8):");
                    int semester = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    Main.catalog.displayCoursesForSemester(semester);
                    break;
                case 2:
                    System.out.println("You can register for the following courses in semester " + this.getSemester() + ":");
                    Main.catalog.displayCoursesForSemester(this.getSemester());
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.nextLine();
                    registerForCourse(Main.catalog,courseCode);
                    break;
                case 3:
                    trackAcademicProgress();
                    break;
                case 4:
                    System.out.print("Enter course code to drop: ");
                    String dropCourseCode = scanner.nextLine();
                    System.out.print("Enter the current date (YYYY-MM-DD): ");
                    String currentDate = scanner.nextLine();
                    dropCourse(Main.catalog);
                    break;
                case 5:
                    submitComplaint(scanner);
                    break;
                case 6:
                    // Passing the list of all the complaints from the main class
                    viewComplaintStatus(Main.complaints);
                    break;
                case 7:
                    giveFeedback(scanner);
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void registerForCourse(CourseCatalog courseCatalog, String courseCode) {
        Course course = courseCatalog.getCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        // Check if the course belongs to the student's semester
        if (course.getSemester() != this.semester) {
            System.out.println("You cannot register for this courseIt ,  is not available for your semester.");
            return;
        }

        try {
            // Check if the course is full
            if (course.isFull()) {
                throw new CourseFullException("The course " + course.getTitle() + " is full and cannot be registered.");
            }

            // Check if the student is already registered for the course
            if (enrolledCourses.contains(course)) {
                System.out.println("You are already registered for this course.");
                return;
            }

            // Register the student
            enrolledCourses.add(course);
            course.addStudent(this);
            System.out.println("You have successfully registered for the course: " + course.getTitle());

        } catch (CourseFullException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean canRegister(Course course) {
        return true;
    }

    public void viewSchedule() {
        System.out.println("Viewing schedule for enrolled courses...");
        for (Course course : enrolledCourses) {
            System.out.println(course.displayCourseDetails());
        }
    }

    private void trackAcademicProgress() {
        System.out.println("Tracking academic progress...");
        for (Grade grade : grades) {
            System.out.println("Course: " + grade.getCourseCode() + ", Grade: " + grade.getGrade());
        }
    }

    public void dropCourse(CourseCatalog catalog) {
        try {
            LocalDate dropDeadline = LocalDate.of(2024, 10, 15);

            if (enrolledCourses.isEmpty()) {
                System.out.println("You are not enrolled in any courses.");
                return;
            }

            System.out.println("Your enrolled courses:");
            for (Course course : enrolledCourses) {
                System.out.println(course.getCode() + ": " + course.getTitle());
            }

            System.out.print("Enter the course code to drop: ");
            String courseCode = new Scanner(System.in).nextLine();

            Course courseToDrop = null;
            for (Course course : enrolledCourses) {
                if (course.getCode().equals(courseCode)) {
                    courseToDrop = course;
                    break;
                }
            }

            if (courseToDrop == null) {
                System.out.println("You are not enrolled in this course.");
                return;
            }


            System.out.print("Enter the current date (YYYY-MM-DD): ");
            String currentDateStr = new Scanner(System.in).nextLine();
            LocalDate currentDate = LocalDate.parse(currentDateStr);

            // Comparing the current date with the deadline
            if (currentDate.isAfter(dropDeadline)) {
                throw new DropDeadlinePassedException("You cannot drop the course after the deadline.");
            }

            enrolledCourses.remove(courseToDrop);
            courseToDrop.removeStudent(this);

            System.out.println("You have successfully dropped the course: " + courseToDrop.getTitle());
        } catch (DropDeadlinePassedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    public void submitComplaint(Scanner scanner) {
        System.out.print("Enter your complaint: ");
        String complaintText = scanner.nextLine();
        Complaint complaint = new Complaint(this.getEmail(), complaintText);
        Main.complaints.add(complaint);
        System.out.println("Complaint submitted successfully.");
    }

    public void addGrade(String courseCode, String grade) {
        Grade newGrade = new Grade(courseCode, grade,semester);
        grades.add(newGrade); // Assuming 'grades' is a List<Grade> in the Student class
    }

    public static List<Student> getStudents() {
        return students;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void viewSGPACGPA() {
        double sgpa = calculateSGPA();
        double cgpa = calculateCGPA();
        System.out.println("SGPA: " + sgpa);
        System.out.println("CGPA: " + cgpa);
    }

    private double calculateSGPA() {
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            double gradePoints = getGradePoints(grade.getGrade());
            totalGradePoints += gradePoints;
            totalCredits += 4;
        }

        if (totalCredits == 0) {
            return 0;
        }

        return totalGradePoints / totalCredits;
    }

    private double calculateCGPA() {
        return calculateSGPA(); // for now displaying sgpa of a course
    }

    private double getGradePoints(String grade) {
        switch (grade) {
            case "A": return 10.0;
            case "B": return 8.0;
            case "C": return 6.0;
            case "D": return 4.0;
            case "F": return 2.0;
            default: return 0.0;
        }
    }

    public void viewComplaintStatus(List<Complaint> complaints) {
        boolean found = false;
        System.out.println("Your Complaints Status:");
        for (Complaint complaint : complaints) {
            if (complaint.getStudentEmail().equals(this.getEmail())) {
                found = true;
                System.out.println("Description: " + complaint.getDescription() + ", Status: " + complaint.getStatus());
            }
        }
        if (!found) {
            System.out.println("No complaints found.");
        }
    }

    private void giveFeedback(Scanner scanner) {
        System.out.println("You can only give feedback for courses you have completed.");

        // Filtering the courses where the student is already assigned a garde and now can give feedback to that course
        List<Course> completedCourses = new ArrayList<>();
        for (Grade grade : grades) {
            Course course = Main.catalog.getCourseByCode(grade.getCourseCode());
            if (course != null) {
                completedCourses.add(course);
            }
        }
        if (completedCourses.isEmpty()) {
            System.out.println("No completed courses available for feedback.");
            return;
        }
        System.out.println("Courses available for feedback:");
        for (Course course : completedCourses) {
            System.out.println(course.getCode() + ": " + course.getTitle());
        }
        System.out.print("Enter the course code you want to give feedback for: ");
        String courseCode = scanner.nextLine();
        Course selectedCourse = Main.catalog.getCourseByCode(courseCode);

        if (selectedCourse != null && completedCourses.contains(selectedCourse)) {
            System.out.print("Enter your numeric rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter your textual feedback (or press Enter to skip): ");
            String comment = scanner.nextLine();

            // Adding numeric rating with additional comment
            Feedback<Integer> numericFeedback = new Feedback<>(rating, this.getEmail(), comment.isEmpty() ? null : comment);
            selectedCourse.addFeedback(numericFeedback);

            //textual feedback if given by the student
            if (!comment.isEmpty()) {
                Feedback<String> textualFeedback = new Feedback<>(comment, this.getEmail(), comment);
                selectedCourse.addFeedback(textualFeedback);
            }
            System.out.println("Thank you for your feedback!");
        } else {
            System.out.println("Invalid course code or you haven't completed this course.");
        }
    }
}
