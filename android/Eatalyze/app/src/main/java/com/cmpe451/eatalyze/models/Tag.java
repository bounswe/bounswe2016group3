package com.cmpe451.eatalyze.models;

/**
 * Created by ekrem on 22/11/2016.
 */

public class Tag {
    private Long mealId;
    private String tag;

    public Tag() {
    }

    public Tag(Long mealId, String tag) {
        this.mealId = mealId;
        this.tag = tag;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
