package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;

import butterknife.Bind;
import butterknife.OnClick;

public class FoodServerProfilePageActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.id_profile_photo)
    ImageView idProfilePhoto;
    @Bind(R.id.id_follow_button)
    Button idFollowButton;
    @Bind(R.id.full_name)
    TextView fullName;
    @Bind(R.id.id_followers)
    TextView idFollowers;
    @Bind(R.id.id_top_layout)
    LinearLayout idTopLayout;
    @Bind(R.id.id_bottom_layout)
    LinearLayout idBottomLayout;
    @Bind(R.id.id_big_layout)
    LinearLayout idBigLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_profile_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FoodServerProfilePageActivity.this, FoodServerHomePage.class));
        finish();
    }

    @OnClick(R.id.id_profile_photo)
    public void onClick() {
        startActivity(new Intent(FoodServerProfilePageActivity.this,AddMealActivity.class));
    }
}
