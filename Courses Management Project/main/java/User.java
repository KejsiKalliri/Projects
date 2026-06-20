public class User {

    String firstname;
    String lastname;
    String email;
    String password;

    public User() {
    }

    public User(int id, String name, String surname, String email, String password) {

        this.firstname = name;
        this.lastname=surname;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String password) {
        this.firstname = name;
        this.lastname=surname;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String name) {
        this.firstname = name;
    }
    
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String surname) {
        this.lastname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
