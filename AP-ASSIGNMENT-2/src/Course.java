import java.util.ArrayList;
import java.util.List;

public class Course {
    private String code;
    private String title;
    private String professor;
    private int credits;
    private String syllabus;
    private String classTimings;
    private int semester; // Added semester attribute
    private List<Student> enrolledStudents;
    private List<Grade> grades;
    private List<TeachingAssistant> teachingAssistants;
    private List<Feedback<?>> feedbackList;

    public Course(String code, String title, String professor, int credits, int semester) {
        this.code = code;
        this.title = title;
        this.professor = professor;
        this.credits = credits;
        this.semester = semester;  // Initialize semester
        this.enrolledStudents = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.teachingAssistants = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.syllabus = "";
        this.classTimings = "";
    }

    public void assignGrade(String studentEmail, String gradeValue) {
        for (Grade grade : grades) {
            if (grade.getStudentEmail().equals(studentEmail)) {
                grade.setGrade(gradeValue);
                return;
            }
        }
        grades.add(new Grade(studentEmail, gradeValue));
    }

    public String getGradeForStudent(String studentEmail) {
        for (Grade grade : grades) {
            if (grade.getStudentEmail().equals(studentEmail)) {
                return grade.getGrade();
            }
        }
        return "No grade assigned yet";
    }

    public boolean isFull() {
        return enrolledStudents.size() >= 2; // I have taken it "2" bcz it will be easy while demonstrating to someone .
    }

    public void addFeedback(Feedback<?> feedback) {
        feedbackList.add(feedback);
    }

    public List<Feedback<?>> getFeedbackList() {
        return feedbackList;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getCode() {
        return code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public String getClassTimings() {
        return classTimings;
    }

    public int getSemester() {
        return semester;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setClassTimings(String classTimings) {
        this.classTimings = classTimings;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String displayCourseDetails() {
        return "Course Code: " + code + ", Title: " + title + ", Professor: " + professor +
                ", Credits: " + credits + ", Semester: " + semester +
                ", Syllabus: " + syllabus + ", Class Timings: " + classTimings;
    }

    public void addTeachingAssistant(TeachingAssistant ta) {
        if (!teachingAssistants.contains(ta)) {
            teachingAssistants.add(ta);
            System.out.println("Teaching Assistant " + ta.getName() + " added to " + this.title);
        } else {
            System.out.println("Teaching Assistant " + ta.getName() + " is already assigned to this course.");
        }
    }

    public TeachingAssistant getTeachingAssistantByEmail(String email) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getEmail().equals(email)) {
                return ta;
            }
        }
        return null;
    }

    public List<TeachingAssistant> getTeachingAssistants() {
        return teachingAssistants;
    }
}
