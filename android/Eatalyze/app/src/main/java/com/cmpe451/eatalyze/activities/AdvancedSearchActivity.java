package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.AdvancedSearchAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ekrem on 10/12/2016.
 */

public class AdvancedSearchActivity extends BaseActivity {

    @Bind(R.id.tl_advanced_search)
    TabLayout tlAdvancedSearch;
    @Bind(R.id.vp_advanced_search)
    ViewPager vpAdvancedSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_advanced_search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        tlAdvancedSearch.addTab(tlAdvancedSearch.newTab().setText("Meal Search"));
        tlAdvancedSearch.addTab(tlAdvancedSearch.newTab().setText("User Search"));

        final AdvancedSearchAdapter adapter = new AdvancedSearchAdapter(getSupportFragmentManager());

        vpAdvancedSearch.setAdapter(adapter);
        vpAdvancedSearch.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlAdvancedSearch));
        tlAdvancedSearch.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpAdvancedSearch.setCurrentItem(tab.getPosition());
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
