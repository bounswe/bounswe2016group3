package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.EatalyzeApplication;
import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.request.ApiService;
import com.squareup.picasso.Picasso;

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



    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profil_page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {

                bio.setText(user.getBio());
                fullName.setText(user.getFullName());
                Picasso.with(UserProfilePageActivity.this).load(user.getAvatarUrl()).into(profil_pic);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow the user
                btn_follow.setText("Following");
                btn_follow.setBackgroundColor(R.color.blue);
                //Add the users following list

            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfilePageActivity.this, FollowersListActivity.class));
            }
        });


    }
}


