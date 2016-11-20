package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FoodServerProfilePageActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.id_top_layout)
    LinearLayout idTopLayout;
    @Bind(R.id.id_bottom_layout)
    LinearLayout idBottomLayout;
    @Bind(R.id.id_big_layout)
    LinearLayout idBigLayout;
    @Bind(R.id.id_profile_photo)
    ImageView idProfilePhoto;
    @Bind(R.id.id_follow_button)
    Button idFollowButton;
    @Bind(R.id.full_name)
    TextView fullName;
    @Bind(R.id.id_followers)
    TextView idFollowers;
    @Bind(R.id.bio)
    TextView bio;
    @Bind(R.id.btn_add_meal)
    Button btnAddMeal;

    static long userid;
    static Bundle bundle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_profile_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);


        bundle = getIntent().getExtras();
        userid = -1;
        if(bundle != null) {
            userid = bundle.getLong("userid");

            apiService.getUserByID(userid, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    bio.setText(user.getBio());
                    fullName.setText(user.getFullName());
                    Picasso.with(FoodServerProfilePageActivity.this).load(user.getAvatarUrl()).into(idProfilePhoto);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getMenu(userid, new Callback<Meal>() {
                @Override
                public void success(Meal meal, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getfollowers(userid, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    idFollowers.setText("Followers: " + userList.size());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            idFollowButton.setText("FOLLOW");
            idFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiService.follow(userid, new Callback<Follow>() {
                        @Override
                        public void success(Follow follow, Response response) {
                            idFollowButton.setText("FOLLOWING");
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            });
        }
        else {

            apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    Log.d("Suc User Page", "Suc");
                    bio.setText(user.getBio());
                    fullName.setText(user.getFullName());
                    Picasso.with(FoodServerProfilePageActivity.this).load(user.getAvatarUrl()).into(idProfilePhoto);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed User Page", error.toString());
                }
            });

            apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    idFollowers.setText("Followers: " + userList.size());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getMenu(eatalyzeApplication.getAccessToken().getUserId(), new Callback<Meal>() {
                @Override
                public void success(Meal meal, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(bundle==null || userid == eatalyzeApplication.getUser().getId()) {
            super.onBackPressed();
            startActivity(new Intent(FoodServerProfilePageActivity.this, FoodServerHomePage.class));
            finish();
        }
        else {

            super.onBackPressed();
            Intent i = new Intent(FoodServerProfilePageActivity.this, FoodServerHomePage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @OnClick(R.id.btn_add_meal)
    public void onClick() {
        startActivity(new Intent(FoodServerProfilePageActivity.this, AddMealActivity.class));
    }

    @OnClick({R.id.id_profile_photo, R.id.id_follow_button, R.id.full_name, R.id.id_followers})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_profile_photo:
                break;
            case R.id.id_follow_button:
                break;
            case R.id.full_name:
                break;
            case R.id.id_followers:
                startActivity(new Intent(FoodServerProfilePageActivity.this, FollowersListActivity.class));
                break;
        }
    }
}
