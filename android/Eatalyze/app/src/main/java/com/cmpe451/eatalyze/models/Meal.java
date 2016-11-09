package com.cmpe451.eatalyze.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ekrem on 27/10/2016.
 */

public class Meal implements Serializable {

    private Long id;
    private Long userId;
    private String name;
    private String imageURL;
    private String ingredients;
    private ArrayList<Float> amounts = new ArrayList<>();
    private String recipe;
    //What is meal type?
    //Implement tags here after semantic tags
    private int totalCalorie;


    public Meal(){
        this.name = "";
        this.id = -1l;
        this.userId = -1l;
        this.imageURL = "http://icons.iconarchive.com/icons/sonya/swarm/128/Fork-Knife-icon.png";
        this.recipe= "";
        this.ingredients = "";
        this.totalCalorie = 0;
    }

    public Meal(Long id,
                Long userId,
                String name,
                String imageURL,
                String ingredients,
                ArrayList<Float> amounts,
                String recipe,
                int totalCalorie){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
        this.amounts = amounts;
        this.recipe = recipe;
        this.totalCalorie = totalCalorie;
    }


    public Long getId() {

        return id;
    }

    public Long getUserId(){

        return userId;
    }

    public String getName() {
        return name;
    }

    public int getTotalCalorie() {
        return totalCalorie;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public ArrayList<Float> getAmounts() {
        return amounts;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



    public void setAmounts(ArrayList<Float> amounts) {
        this.amounts = amounts;
    }

    public void setTotalCalorie(int totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public String getImageURL() { return imageURL; }
}
