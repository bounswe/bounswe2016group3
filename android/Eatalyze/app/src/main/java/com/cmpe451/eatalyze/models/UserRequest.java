package com.cmpe451.eatalyze.models;

import com.cmpe451.eatalyze.constants.DietType;
import com.cmpe451.eatalyze.constants.UserType;

/**
 * Created by Behiye on 10/27/2016.
 */

public class UserRequest {
    Long id;
    String email;
    String password;
    String bio;
    String fullName;
    UserType userType;
    DietType dietType;
    String secretQuestion;
    String secretAnswer;
    String avatarUrl;

    public UserRequest(){}

    public UserRequest(Long id, String email, String password, String bio, String fullName, UserType userType, DietType dietType, String secretQuestion, String secretAnswer, String avatarUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.fullName = fullName;
        this.userType = userType;
        this.dietType = dietType;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
