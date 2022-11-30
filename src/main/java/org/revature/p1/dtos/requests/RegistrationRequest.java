package org.revature.p1.dtos.requests;

import org.revature.p1.utils.enums.ClientUserType;

public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String forename;
    private String surname;
    private ClientUserType role;

    public RegistrationRequest() {
        super();
    }

    public RegistrationRequest(String username,
                               String password,
                               String email,
                               String forename,
                               String surname,
                               ClientUserType role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.role = role;
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

    public ClientUserType getRole() {
        return role;
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

    public void setRole(ClientUserType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                '}';
    }
}
