package org.revature.p1.models;

import org.revature.p1.utils.enums.ClientUserType;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String forename;
    private String surname;
    private boolean active;
    private ClientUserType role;

    public User() {
        super();
    }

    public User(String id,
                String username,
                String password,
                String email,
                String forename,
                String surname,
                boolean active,
                ClientUserType role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.active = active;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isActive() {
        return active;
    }

    public ClientUserType getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRole(ClientUserType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", active=" + active +
                ", role=" + role +
                '}';
    }
}
