package com.cmpe451.eatalyze.models;

/**
 * Created by behiye.avci on 20/12/2016.
 */

public class WeeklyMeal {
    private Long userId;
    private Long mealId;
    private Long creationDate;

    public WeeklyMeal(){}

    public WeeklyMeal(Long userId, Long mealId, Long creationDate) {
        this.userId = userId;
        this.mealId = mealId;
        this.creationDate = creationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }
}
