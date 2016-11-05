package com.cmpe451.eatalyze.activities;

import android.os.Bundle;

import com.cmpe451.eatalyze.R;



/**
 * Created by ekrem on 05/11/2016.
 */

public class ViewMealActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_view_meal;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);
    }


}
