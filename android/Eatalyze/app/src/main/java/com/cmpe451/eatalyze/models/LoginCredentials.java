package com.cmpe451.eatalyze.models;

public class LoginCredentials {
    private String email;
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String username, String password) {
        super();
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}