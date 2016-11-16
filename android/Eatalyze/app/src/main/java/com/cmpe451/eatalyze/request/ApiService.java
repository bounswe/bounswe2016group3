package com.cmpe451.eatalyze.request;

import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.LoginCredentials;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.UserList;
import com.cmpe451.eatalyze.models.UserRequest;
import com.cmpe451.eatalyze.models.UserResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

/**
 * Created by ekrem on 13/10/2016.
 */

public interface ApiService {

    @POST("/api/session/login")
    public void login(@Body LoginCredentials credentials, Callback<AccessToken> accessTokenCallback);

    @POST("/api/user")
    public void signup(@Body UserRequest userRequest, Callback<UserResponse> userResponseCallback);

    @GET("/api/session/currentUser")
    public void getCurrentUser(@Query("accessToken") AccessToken accessToken, Callback<User> userCallback);

    @GET("/api/user/{id}")
    public void getUserByID(@Path("id") Long id , Callback<User> userCallback);

    //TODO meal will be turned into a list
    @GET("/api/menu/{id}")
    public void getMenu(@Path("id") Long id, Callback<Meal> mealCallback);

    @POST("/api/meal")
    public void createMeal(@Query("accessToken") AccessToken accessToken,@Query("meal") Meal meal);

    @POST("/api/meal/{id}/rate/{rating}")
    public void rateMeal(@Query("accessToken") AccessToken token, @Path("id") Long id, @Path("rating") Float rating, Callback<ResponseBody> responseBodyCallback);


    @POST("/api/user/{id}/follow")
    public void follow(@Path("id") Long id, Callback<Follow> followCallback);

    @POST("/api/user/{id}/unfollow")
    public void unfollow(@Path("id") Long id, Callback<Follow> followCallback);

    @GET("/api/user/{id}/followers")
    public void getfollowers(@Path("id") Long id, Callback<List<User>> UserListCallback);
}
