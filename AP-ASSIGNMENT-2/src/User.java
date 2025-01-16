public interface User {
    void signUp(String email, String name, String password);
    boolean login(String email, String password);
}
