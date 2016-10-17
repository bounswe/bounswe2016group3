package com.cmpe451.eatalyze.models;

import com.cmpe451.eatalyze.constants.DietType;
import com.cmpe451.eatalyze.constants.UserType;

/**
 * Created by ekrem on 13/10/2016.
 */

public class User {
    private Long id;

    //not null
    private String email;
    private String passwordHash;
    private String passwordSalt;

    private String bio;

    private String fullName;

    private UserType userType;

    private DietType dietType;

    //not null
    private String secretQuestion;

    //not null
    private String secretAnswerHash;

    //not null
    private String secretAnswerSalt;

    private String avatarUrl;

    private Boolean isBanned;

    public User() {
        this.id = -1l;

        this.bio = "";
        this.fullName = "";
        this.userType = UserType.REGULAR;
        this.dietType = DietType.OMNIVORE;
        this.avatarUrl = "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png";
        this.isBanned = false;
    }

    public User(Long id, String email, String passwordHash, String passwordSalt, String bio, String fullName, UserType userType, DietType dietType, String secretQuestion, String secretAnswerHash, String secretAnswerSalt, String avatarUrl, Boolean isBanned) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.bio = bio;
        this.fullName = fullName;
        this.userType = userType;
        this.dietType = dietType;
        this.secretQuestion = secretQuestion;
        this.secretAnswerHash = secretAnswerHash;
        this.secretAnswerSalt = secretAnswerSalt;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
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

    public String getSecretAnswerHash() {
        return secretAnswerHash;
    }

    public void setSecretAnswerHash(String secretAnswerHash) {
        this.secretAnswerHash = secretAnswerHash;
    }

    public String getSecretAnswerSalt() {
        return secretAnswerSalt;
    }

    public void setSecretAnswerSalt(String secretAnswerSalt) {
        this.secretAnswerSalt = secretAnswerSalt;
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
}
