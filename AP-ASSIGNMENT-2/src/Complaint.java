public class Complaint {
    private String description;
    private String status;
    private String studentEmail;
    private static int complaintCount = 0;
    private int complaintId;

    public Complaint(String studentEmail, String description) {
        this.studentEmail = studentEmail;
        this.description = description;
        this.status = "Pending";
        this.complaintId = ++complaintCount;  // Assigns a unique complaint ID for every complaint
    }

    public int getComplaintId() {
        return complaintId;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void resolveComplaint() {
        this.status = "Resolved";
    }

    public Object getStudentEmail() {
        return studentEmail;
    }
}

