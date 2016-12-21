package com.cmpe451.eatalyze.models;

import com.cmpe451.eatalyze.constants.DietType;
import com.cmpe451.eatalyze.constants.UserType;

import java.io.Serializable;

/**
 * Created by ekrem on 13/10/2016.
 */

public class User implements Serializable{
    private Long id;
    private String email;
    private String bio;
    private String fullName;
    private int userType;
    private int dietType;
    //not null
    private String secretQuestion;
    //not null
    //not null
    private String avatarUrl;
    private String location;
    private Boolean isBanned;



    public User() {
        this.id = -1l;

        this.bio = "";
        this.fullName = "";

        this.userType = 0;
        this.dietType=0;
        this.avatarUrl = "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png";
        this.isBanned = false;
        location="";
    }

    public User(Long id, String email, String bio, String fullName, int userType, int dietType, String secretQuestion, String avatarUrl, Boolean isBanned) {
        this.id = id;
        this.email = email;

        this.bio = bio;
        this.fullName = fullName;
        this.userType = userType;
        this.dietType = dietType;
        this.secretQuestion = secretQuestion;
        this.avatarUrl = avatarUrl;
        this.isBanned = isBanned;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public int getDietType() {
        return dietType;
    }

    public void setDietType(int dietType) {
        this.dietType = dietType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
