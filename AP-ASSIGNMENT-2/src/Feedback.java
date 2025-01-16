// Generic Feedback class
public class Feedback<T> {
    private T feedback;
    private String studentEmail;
    private String comment;

    public Feedback(T feedback, String studentEmail,String comment) {
        this.feedback = feedback;
        this.studentEmail = studentEmail;
        this.comment=comment;
    }

    public T getFeedback() {
        return feedback;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getComment() {
        return comment;
    }
}
