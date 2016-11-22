package com.cmpe451.eatalyze.activities;

import android.os.Bundle;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;

/**
 * Created by ekrem on 22/11/2016.
 */

public class TagMealActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_meal_tag;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

    }
}
