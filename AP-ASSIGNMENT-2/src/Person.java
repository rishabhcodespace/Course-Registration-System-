public abstract class Person {
    private String email;
    private String name;
    private String password;

    public Person(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    // Public method to set the name
    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
