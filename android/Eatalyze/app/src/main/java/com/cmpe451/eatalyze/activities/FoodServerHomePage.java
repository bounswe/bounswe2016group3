package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmpe451.eatalyze.R;

public class FoodServerHomePage extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_home_page;
    }

    @Override
    public void onCreate(Bundle savedInstances){

        super.onCreate(savedInstances);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
