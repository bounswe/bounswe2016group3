package com.cmpe451.eatalyze.models;

/**
 * Created by ASUS on 21.10.2016.
 */

public class Follow {

    private int followerId;
    private int followeeId;


    public Follow(int follower_id, int followee_id){
        this.followerId = follower_id;
        this.followeeId = followee_id;
    }

    public int getFollower_id() {
        return followerId;
    }
    public int getFollowee_id() {
        return followeeId;
    }
    public void setFollower_id(int follower_id) {
        this.followerId = follower_id;
    }
    public void setFollowee_id(int followee_id) {
        this.followeeId = followee_id;
    }


}
