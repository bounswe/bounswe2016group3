package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.LogAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 25.10.2016.
 */

public class LogActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_log;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
