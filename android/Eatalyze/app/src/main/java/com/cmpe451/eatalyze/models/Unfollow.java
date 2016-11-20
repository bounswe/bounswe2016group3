package com.cmpe451.eatalyze.models;

/**
 * Created by ASUS on 19.11.2016.
 */

public class Unfollow {

    private int unfollowerId;
    private int unfolloweeId;


    public Unfollow(int unfollower_id, int unfollowee_id){
        this.unfollowerId = unfollower_id;
        this.unfolloweeId = unfollowee_id;
    }

    public int getUnfollower_id() {
        return unfollowerId;
    }
    public int getUnfollowee_id() {
        return unfolloweeId;
    }
    public void setUnfollower_id(int unfollower_id) {
        this.unfollowerId = unfollower_id;
    }
    public void setUnfollowee_id(int unfollowee_id) {
        this.unfolloweeId = unfollowee_id;
    }
}
