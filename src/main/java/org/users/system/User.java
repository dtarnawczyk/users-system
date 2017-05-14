package org.users.system;

public class User {

    private final String login;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;
    private final String password;
    private final boolean active;
    private final String group;

    public User(String login,
                String firstName,
                String lastName,
                String address,
                String email,
                String password,
                boolean active,
                String group) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.active = active;
        this.group = group;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public String getGroup() {
        return group;
    }
}
