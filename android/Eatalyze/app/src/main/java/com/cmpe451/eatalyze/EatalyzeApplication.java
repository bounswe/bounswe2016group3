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
    private SharedPreferences sp;
    public static final String PREFS_NAME="EatalyzePrefs";
    public static final String ACCESS_TOKEN="accessToken";
    public void onCreate(){
        super.onCreate();

        sp=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonAccessToken = sp.getString(ACCESS_TOKEN, null);
        if (jsonAccessToken != null) {
            accessToken= (AccessToken) Utils.fromGson(jsonAccessToken,AccessToken.class);
        }

    }
    public AccessToken getAccessToken(){
        return accessToken;
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

}

