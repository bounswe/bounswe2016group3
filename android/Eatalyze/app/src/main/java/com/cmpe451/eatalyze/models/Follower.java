package com.cmpe451.eatalyze.models;

/**
 * Created by ASUS on 21.10.2016.
 */

public class Follower {

    private int follower_id;
    private int followee_id;


    public Follower(int follower_id, int followee_id){
        this.follower_id = follower_id;
        this.followee_id = followee_id;
    }

    public int getFollower_id() {
        return follower_id;
    }
    public int getFollowee_id() {
        return followee_id;
    }
    public void setFollower_id(int follower_id) {
        this.follower_id = follower_id;
    }
    public void setFollowee_id(int followee_id) {
        this.followee_id = followee_id;
    }


}
