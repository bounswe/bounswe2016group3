package com.cmpe451.eatalyze.models;

/**
 * Created by Behiye on 11/19/2016.
 */

public class NutritionalInfo {
    private float weight; //gr
    private float calories;
    private float totalFat; //gr
    private float saturatedFat;
    private float cholesterol;
    private float sodium;
    private float totalCarbohydrate; //gr
    private float dietaryFiber;
    private float sugars;
    private float protein; //gr
    private float potassium;
    private float phosphorus;

    public NutritionalInfo(){}

    public NutritionalInfo(float weight, float calories, float totalFat, float saturatedFat, float cholesterol, float sodium, float totalCarbohydrate, float dietaryFiber, float sugars, float protein, float potassium, float phosphorus) {
        this.weight = weight;
        this.calories = calories;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.totalCarbohydrate = totalCarbohydrate;
        this.dietaryFiber = dietaryFiber;
        this.sugars = sugars;
        this.protein = protein;
        this.potassium = potassium;
        this.phosphorus = phosphorus;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }

    public float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(float totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public float getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(float dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
    }
}
