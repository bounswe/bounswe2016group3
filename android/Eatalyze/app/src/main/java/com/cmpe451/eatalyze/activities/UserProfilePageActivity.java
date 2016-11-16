package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.UserList;
import com.cmpe451.eatalyze.views.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Muharrem on 21.10.2016.
 */

public class UserProfilePageActivity extends BaseActivity {

    @Bind(R.id.id_profile_photo)
    ImageView profil_pic;
    @Bind(R.id.id_followers)
    TextView followers;
    @Bind(R.id.id_following)
    TextView following;
    @Bind(R.id.bio)
    TextView bio;
    @Bind(R.id.full_name)
    TextView fullName;
    @Bind(R.id.id_follow_button)
    Button btn_follow;
    @Bind(R.id.id_expandabletextView)
    ExpandableTextView expandableTextView;
    @Bind(R.id.btn_log)
    Button btnLog;
    @Bind(R.id.btn_diet)
    Button btnDiet ;
    @Bind(R.id.id_includes)
    TextView includes;
    @Bind(R.id.id_excludes)
    TextView excludes;
    @Bind(R.id.id_preferences)
    TextView preferences;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profil_page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                    @Override
                    public void success(List<User> users, Response response) {

                        followers.setText("Followers: "+ users.size() );
                        Log.d("Number of users: ", users.size()+"");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Failed", error.toString());
                    }
                });


                Log.d("Access token control", eatalyzeApplication.getAccessToken() + "");
        apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("Suc User Page","Suc");
                bio.setText(user.getBio());
                fullName.setText(user.getFullName());
                Picasso.with(UserProfilePageActivity.this).load(user.getAvatarUrl()).into(profil_pic);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed User Page",error.toString());
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow the user
                apiService.follow(eatalyzeApplication.getUser().getId(), new Callback<Follow>() {
                    @Override
                    public void success(Follow follow, Response response) {
                        Log.d("Follow success", "Followee id: " + follow.getFollowee_id() +" "+ follow.getFollower_id());
                        btn_follow.setText("FOLLOWING");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Follow failed", error.toString());
                    }
                });

            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfilePageActivity.this, FollowersListActivity.class));
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfilePageActivity.this, FollowingListActivity.class));
            }
        });
        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start diet activity
                startActivity(new Intent(UserProfilePageActivity.this, DietActivity.class));
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start log activity
                startActivity(new Intent(UserProfilePageActivity.this, LogActivity.class));
            }
        });

        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit preferences activity
                startActivity(new Intent(UserProfilePageActivity.this, EditPreferencesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserProfilePageActivity.this, UserHomepageActivity.class));
        finish();
    }
}


