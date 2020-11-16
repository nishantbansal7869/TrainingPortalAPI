package com.trainingportalapi.trainingportal.domain;

public class User {
    private Integer userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(int userId, String firstName, String lastName, String email, String password) {
        this.userID = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this. password = password;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
