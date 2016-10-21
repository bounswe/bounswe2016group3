package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;

import butterknife.Bind;

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
    @Bind(R.id.id_bio)
    TextView bio;
    @Bind(R.id.id_users_name)
    TextView users_name;
    @Bind(R.id.id_follow_button)
    Button follow_btn;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profil_page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow the user

                follow_btn.setText("Following");
                //follow_btn.setBackgroundColor();

            }
        });


    }
}


