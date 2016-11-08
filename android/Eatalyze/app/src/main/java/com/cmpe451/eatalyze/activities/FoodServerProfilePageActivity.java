package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;

import butterknife.Bind;
import butterknife.OnClick;

public class FoodServerProfilePageActivity extends BaseActivity {

    @Bind(R.id.tv_add_meal)
    TextView tvAddMeal;

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_profile_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

    }

    @OnClick(R.id.tv_add_meal)
    public void onClick() {
        startActivity(new Intent(FoodServerProfilePageActivity.this, AddMealActivity.class));
    }
}
