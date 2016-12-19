package com.cmpe451.eatalyze.request;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.LoginCredentials;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Tag;
import com.cmpe451.eatalyze.models.Unfollow;
import com.cmpe451.eatalyze.models.Menu;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.Ratings;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.UserList;
import com.cmpe451.eatalyze.models.UserRequest;
import com.cmpe451.eatalyze.models.UserResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
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

    @GET("/api/meal/{id}/ratings")
    public void getRatings(@Query("accessToken") AccessToken token, @Path("id") Long id, Callback<Ratings> ratingsCallback);

    @POST("/api/user/{id}/follow")
    public void follow(@Path("id") Long id, Callback<Follow> followCallback);

    @POST("/api/user/{id}/unfollow")
    public void unfollow(@Path("id") Long id, Callback<Unfollow> unfollowCallback);

    @GET("/api/user/{id}/followers")
    public void getfollowers(@Path("id") Long id, Callback<List<User>> UserListCallback);

    @GET("/api/user/{id}/following")
    public void getfollowing(@Path("id") Long id, Callback<List<User>> UserListCallBack);

    @POST("/api/meal/{id}/checkeat")
    public void checkEat(@Query("accessToken") AccessToken token, @Path("id") Long id, Callback<ResponseBody> responseBodyCallback);

    @POST("/api/comment")
    public void createComment(@Query("accessToken") AccessToken token,@Body Comment comment, Callback<Comment> commentCallback);

    @GET("/api/user/{userId}/menus")
    public void getMenus(@Path("userId") Long userId, Callback<List<Menu>> menuListCallBack);

    @GET("/api/menu/{menuId}/meals")
    public void getMealsOfMenu(@Path("menuId") Long menuId, Callback<List<Meal>> mealListCallBack);

    //TODO test after ingredient intake
    @GET("/api/meal/{id}/nutrition")
    public void getNutrition(@Path("id") Long id, Callback<NutritionalInfo> nutritionalInfoCallback);

    @POST("/api/meal")
    public void addMeal(@Body Meal meal, Callback<ResponseBody> responseBodyCallback);

    @GET("/api/meal/{id}")
    public void getMealById(@Path("id") Long id, Callback<Meal> mealCallback);

    @GET("/api/meal/{id}/comments")
    public void commentsByMeal(@Path("id") Long id, Callback<List<Comment>> commentsListCallback);

    @GET("/api/user/search/{query}")
    public void userSearch(@Path("query") String query, Callback<List<User>> userListCallback);

    @GET("/api/meal/search/{query}")
    public void mealSearch(@Path("query") String query, Callback<List<Meal>> mealListCallback);


    @GET("/api/meal/{id}/tags")
    public void tagsByMeal(@Path("id") Long id, Callback<List<String>> tagListCallback);

    @POST("/api/meal/tag")
    public void tagMeal(@Query("accessToken") AccessToken token, @Body Tag tag, Callback<Tag> tagCallback);

    @POST("/api/meal/untag")
    public void untagMeal(@Query("accessToken") AccessToken token, @Body Tag tag, Callback<Tag> tagCallback);

    @GET("/api/user/{id}/comments")
    public void getUserComments(@Path("id") Long id, Callback<List<Comment>> userCommentList );

    @GET("/api/user")
    public void getUsers(Callback<List<User>> userListCallback);

    @GET("/api/home/lastweek")
    public void getWeeklyNutritionalInfo(Callback<NutritionalInfo> nutritionalInfoCallback);

    /*
    @GET("/api/home/lastweek/meals")
    public void getWeeklyMeals(Callback<List<Meal>> mealListCallback);
    */

    @POST("/api/user/{id}/include")
    public void updatedIncludes(@Path("id") Long id, @Body String[] includeList, Callback<ResponseBody> responseBodyCallback);

    @POST("/api/user/{id}/exclude")
    public void updatedExludes(@Path("id") Long id, @Body String[] excludeList, Callback<ResponseBody> responseBodyCallback);

    @GET("/api/user/{id}/include")
    public void getIncludes(@Path("id") Long id, Callback<String[]> includeListCallback);

    @GET("/api/user/{id}/exclude")
    public void getExcludes(@Path("id") Long id, Callback<String[]> excludeListCallback);

    @Multipart
    @POST("/api/user/avatar")
    public void uploadPhoto(@Part("avatar") InputStream file,@Part("description") String description, Callback<String> responseCallback);
}
