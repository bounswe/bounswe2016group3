package com.cmpe451.eatalyze.request;

import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.LoginCredentials;
import com.cmpe451.eatalyze.models.User;
import com.squareup.okhttp.Call;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by ekrem on 13/10/2016.
 */

public interface ApiService {

    @POST("/api/session/login")
    public void login(@Body LoginCredentials credentials, Callback<AccessToken> accessTokenCallback);


}
