package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.LogAdapter;
import com.cmpe451.eatalyze.models.NutritionalInfo;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ASUS on 25.10.2016.
 */

public class LogActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    public NutritionalInfo nutritionalInfo=new NutritionalInfo();

    @Override
    public int getLayoutId() {
        return R.layout.activity_log;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService.getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                Log.d("Suc Nutritional Info",nutritionalInfo.getCalories()+"");
                LogActivity.this.nutritionalInfo=nutritionalInfo;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Nutritional Info",error.toString());
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Calories"));
        tabLayout.addTab(tabLayout.newTab().setText("Nutrients"));
        tabLayout.addTab(tabLayout.newTab().setText("Macros"));

        final LogAdapter adapter = new LogAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
