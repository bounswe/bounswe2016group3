package com.cmpe451.eatalyze.models;

import org.joda.time.DateTime;

/**
 * Created by ekrem on 17/11/2016.
 */

public class Comment {
    private Long id;
    private Long mealId;
    private Long userId;
    private String content;
    private long creationTime;
    private long updateTime;

    public Comment() {

    }

    public Comment(Long id, Long mealId, Long userId, String content, long creationTime, long updateTime) {
        this.id = id;
        this.mealId = mealId;
        this.userId = userId;
        this.content = content;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
    }

    public Comment(Long mealId, Long userId, String content, long creationTime, long updateTime) {
        this.mealId = mealId;
        this.userId = userId;
        this.content = content;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

}