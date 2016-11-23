package com.cmpe451.eatalyze.models;


import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */

public class UserList{

    private List<User> listUser;

    public UserList(List<User> listUser) {
        this.listUser = listUser;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }



}
