import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseCatalog {
    private Map<Integer, List<Course>> semesterCourses;
    private List<TeachingAssistant> teachingAssistants = new ArrayList<>();
    private LocalDate dropDeadline;

    public CourseCatalog() {
        semesterCourses = new HashMap<>();
        initializeCourses();
    }

    private void initializeCourses() {
        semesterCourses.put(1, new ArrayList<>(List.of(
                new Course("CS101", "Intro to Computer Science", "Dr.shad", 4, 1),
                new Course("MTH101", "linear algebra", "Dr.subhojit", 4, 1),
                new Course("DC101", "digital circuit", "Dr. tillo", 4, 1),
                new Course("HCI101", "human comp interaction", "Dr. sonal", 4, 1),
                new Course("COM101", "communication", "Dr. payal", 4, 1),
                new Course("SSH101", "World History", "Dr. pal", 4, 1)
        )));

        semesterCourses.put(2, new ArrayList<>(List.of(
                new Course("CS102", "Data Structures", "Dr. debaraka", 4, 2),
                new Course("MTH102", "p and s", "Dr. sanjit kaul", 4, 2),
                new Course("BS102", "BASIC ELECTRONICS", "Dr. tillo", 4, 2),
                new Course("CO102", "Computer organisation", "Dr. tillo", 4, 2),
                new Course("ENG102", "Literature", "Dr. Green", 3, 2),
                new Course("SSH102", "money and banking", "Dr. kiriti", 4, 2)
        )));

        semesterCourses.put(3, new ArrayList<>(List.of(
                new Course("CS201", "Algorithms", "Dr. debaraka", 4, 3),
                new Course("MATH201", "Linear Algebra", "Dr. Johnson", 4, 3),
                new Course("CS202", "Computer Organization", "Dr. bhaloo", 4, 3),
                new Course("BIO201", "Biology II", "Dr. Brown", 4, 3),
                new Course("ENG201", "Technical Writing", "Dr. jiyan", 3, 3),
                new Course("SOC101", "Sociology", "Dr. sujuka", 3, 3)
        )));

        semesterCourses.put(4, new ArrayList<>(List.of(
                new Course("CS301", "Operating Systems", "Dr. vivek", 4, 4),
                new Course("MATH301", "Probability and Statistics", "Dr. kaul", 4, 4),
                new Course("CS302", "Database Systems", "Dr. Lee", 4, 4),
                new Course("BIO301", "Genetics", "Dr. alok", 4, 4),
                new Course("CHEM301", "Organic Chemistry", "Dr. Brown", 4, 4),
                new Course("ENG301", "Advanced Composition", "Dr. nobita", 3, 4)
        )));

        semesterCourses.put(5, new ArrayList<>(List.of(
                new Course("CS401", "Software Engineering", "Dr. jiyaan", 4, 5),
                new Course("MATH401", "Discrete Mathematics", "Dr. bapi ", 4, 5),
                new Course("CS402", "Computer Networks", "Dr. tillo", 4, 5),
                new Course("BIO401", "Ecology", "Dr. modi", 4, 5),
                new Course("CHEM401", "Inorganic Chemistry", "Dr. amitabh", 4, 5),
                new Course("SOC201", "Psychology", "Dr. payal", 3, 5)
        )));

        semesterCourses.put(6, new ArrayList<>(List.of(
                new Course("CS501", "Machine Learning", "Dr. pankaj", 4, 6),
                new Course("MATH501", "Numerical Methods", "Dr. bapi", 4, 6),
                new Course("CS502", "Artificial Intelligence", "Dr. debaraka", 4, 6),
                new Course("BIO501", "Biotechnology", "Dr. jiyaan", 4, 6),
                new Course("CHEM501", "Physical Chemistry", "Dr. indumati", 4, 6),
                new Course("ENG401", "Creative Writing", "Dr. kaliya", 3, 6)
        )));

        semesterCourses.put(7, new ArrayList<>(List.of(
                new Course("CS601", "Cybersecurity", "Dr. rahul", 4, 7),
                new Course("MATH601", "Operations Research", "Dr. soniya", 4, 7),
                new Course("CS602", "Human-Computer Interaction", "Dr. sonal", 4, 7),
                new Course("BIO601", "Environmental Science", "Dr. modi", 4, 7),
                new Course("CHEM601", "Medicinal Chemistry", "Dr. mbbs", 4, 7),
                new Course("SOC301", "Anthropology", "Dr. deepak", 3, 7)
        )));

        semesterCourses.put(8, new ArrayList<>(List.of(
                new Course("CS701", "Capstone Project", "Dr. cyfuse", 4, 8),
                new Course("MATH701", "Advanced Topics in Mathematics", "Dr. satish pandey", 4, 8),
                new Course("CS702", "Cloud Computing", "Dr. shad", 4, 8),
                new Course("BIO701", "Marine Biology", "Dr. jiyaan", 4, 8),
                new Course("CHEM701", "Advanced Organic Chemistry", "Dr. indumati", 4, 8),
                new Course("ENG501", "Business Communication", "Dr. nobita", 4, 8)
        )));
    }

    public void displayAvailableCourses() {
        for (Map.Entry<Integer, List<Course>> entry : semesterCourses.entrySet()) {
            int semester = entry.getKey();
            System.out.println("Semester " + semester + ":");
            displayCoursesForSemester(semester);
        }
    }

    public void displayCoursesForSemester(int semester) {
        System.out.println("Displaying courses for semester: " + semester);
        List<Course> courses = semesterCourses.get(semester);
        if (courses != null) {
            for (Course course : courses) {
                System.out.println(course.displayCourseDetails());
            }
        } else {
            System.out.println("No courses available for semester " + semester);
        }
    }

    public Course getCourseByCode(String code) {
        for (List<Course> courses : semesterCourses.values()) {
            for (Course course : courses) {
                if (course.getCode().equals(code)) {
                    return course;
                }
            }
        }
        return null;
    }

    public void assignProfessorToCourse(String courseCode, String professor) {
        Course course = getCourseByCode(courseCode);
        if (course != null) {
            course.setProfessor(professor);
            System.out.println("Professor assigned successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public Professor findProfessorByEmail(String email) {
        for (Professor professor : Main.professors) { // Assuming professors is a list of all professors
            if (professor.getEmail().equals(email)) {
                return professor;
            }
        }
        return null;
    }

    public List<Course> getCoursesByProfessor(String professorName) {
        List<Course> assignedCourses = new ArrayList<>();
        for (List<Course> courses : semesterCourses.values()) {
            for (Course course : courses) {
                if (course.getProfessor().equals(professorName)) {
                    assignedCourses.add(course);
                }
            }
        }
        return assignedCourses;
    }

    public void addCourse(int semester, Course course) {
        semesterCourses.computeIfAbsent(semester, k -> new ArrayList<>()).add(course);
    }

    public void removeCourse(String courseCode) {
        boolean courseFound = false;
        for (List<Course> courses : semesterCourses.values()) {
            if (courses.removeIf(course -> course.getCode().equals(courseCode))) {
                courseFound = true;
            }
        }
        if (courseFound) {
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public List<Course> getCourses() {
        List<Course> allCourses = new ArrayList<>();
        for (List<Course> courses : semesterCourses.values()) {
            allCourses.addAll(courses);
        }
        return allCourses;
    }


    public CourseCatalog(LocalDate dropDeadline) {
        this.dropDeadline = dropDeadline;
    }

    public LocalDate getDropDeadline() {
        return dropDeadline;
    }

    public List<Course> getCoursesByTeachingAssistantEmail(String email) {
        TeachingAssistant ta = getTeachingAssistantByEmail(email);
        if (ta == null) {
            System.out.println("No Teaching Assistant found with email: " + email);
            return new ArrayList<>(); // Return empty list if no TA found
        }

        List<Course> assignedCourses = new ArrayList<>();
        for (List<Course> courses : semesterCourses.values()) {
            for (Course course : courses) {
                if (course.getTeachingAssistants().contains(ta)) {
                    assignedCourses.add(course);
                }
            }
        }
        return assignedCourses;
    }
    // Method to get a Teaching Assistant by email
    public TeachingAssistant getTeachingAssistantByEmail(String email) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getEmail().equals(email)) {
                return ta;
            }
        }
        return null;
    }
}
