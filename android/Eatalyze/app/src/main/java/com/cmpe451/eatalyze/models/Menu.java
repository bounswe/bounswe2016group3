package com.cmpe451.eatalyze.models;

/**
 * Created by Behiye on 11/18/2016.
 */

public class Menu {
    private Long id;
    private Long userId;
    private String name;

    public Menu(){}

    public Menu(Long id, Long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
