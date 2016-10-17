package com.cmpe451.eatalyze.models;

import android.text.method.DateTimeKeyListener;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by Behiye on 10/17/2016.
 */


public class AccessToken {
    private UUID accessToken;
    private Long userId;
    private Long creationTime;
    private Long lastAccessTime;

    public AccessToken(){}

    public AccessToken(UUID accessToken, Long userId, Long creationTime, Long lastAccessTime) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
    }

    public UUID getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
