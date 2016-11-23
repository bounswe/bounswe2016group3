package com.cmpe451.eatalyze;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.utils.Utils;

/**
 * Created by Behiye on 10/14/2016.
 */

public class EatalyzeApplication extends Application {
    private AccessToken accessToken;
    private User user;
    private SharedPreferences sp;
    public static final String PREFS_NAME="EatalyzePrefs";
    public static final String ACCESS_TOKEN="accessToken";
    public static final String USER="User";
    public void onCreate(){
        super.onCreate();

        sp=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String jsonAccessToken = sp.getString(ACCESS_TOKEN, null);
        if (jsonAccessToken != null) {
            accessToken= (AccessToken) Utils.fromGson(jsonAccessToken,AccessToken.class);
        }

        String jsonUser=sp.getString(USER,null);
        if(jsonUser!=null){
            user=(User) Utils.fromGson(jsonUser,User.class);
        }

    }
    public AccessToken getAccessToken(){
        return accessToken;
    }

    public User getUser() {
        return user;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    public void setAccessToken(AccessToken accessToken){

        if (accessToken != null) {
            String str=Utils.toGson(accessToken);
            sp.edit().putString(ACCESS_TOKEN,str).commit();
        }
        else{
            sp.edit().remove(ACCESS_TOKEN).commit();
        }

        this.accessToken=accessToken;
    }

    public void setUser(User user){

        if (user != null) {
            String str=Utils.toGson(user);
            sp.edit().putString(USER,str).commit();
        }
        else{
            sp.edit().remove(USER).commit();
        }

        this.user=user;
    }
}

