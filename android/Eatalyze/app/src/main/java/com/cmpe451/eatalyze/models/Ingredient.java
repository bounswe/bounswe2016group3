package com.cmpe451.eatalyze.models;

/**
 * Created by ekrem on 27/10/2016.
 */

public class Ingredient {
    private Long id;
    private String name;

    //micro nutrition
    //macro nutrition

    public Ingredient(){
        this.id = -1l;
        this.name = "";
    }

    public Ingredient(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
