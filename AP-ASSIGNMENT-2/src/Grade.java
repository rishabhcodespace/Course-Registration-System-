public class Grade {
    private String courseCode;
    private String grade;
    private int semester;
    private String studentEmail;
    private String gradeValue;

    public Grade(String courseCode, String grade, int semester) {
        this.courseCode = courseCode;
        this.grade = grade;
        this.semester = semester;
    }

    public Grade(String studentEmail, String gradeValue) {
        this.studentEmail = studentEmail;
        this.gradeValue = gradeValue;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setGrade(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public int getSemester() {
        return semester;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public String getGrade() {
        return grade;
    }
}
