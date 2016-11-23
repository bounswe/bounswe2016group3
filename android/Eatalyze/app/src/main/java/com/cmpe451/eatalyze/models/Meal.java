package com.cmpe451.eatalyze.models;

import java.io.Serializable;

/**
 * Created by ekrem on 27/10/2016.
 */

public class Meal implements Serializable {

    private Long id;
    private Long menuId;
    private Long userId;
    private String name;
    private String description;
    private String ingredients;
    private String photoUrl;

    public Meal(){}

    public Meal(Long id, Long menuId, Long userId, String name, String description, String ingredients, String photoUrl) {
        this.id = id;
        this.menuId = menuId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
