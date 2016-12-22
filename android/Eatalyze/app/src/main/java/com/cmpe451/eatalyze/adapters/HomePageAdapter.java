package com.cmpe451.eatalyze.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmpe451.eatalyze.fragments.CaloriesFragment;
import com.cmpe451.eatalyze.fragments.MacroFragment;
import com.cmpe451.eatalyze.fragments.NutrientsFragment;
import com.cmpe451.eatalyze.fragments.RecommendedFoodServerFragment;
import com.cmpe451.eatalyze.fragments.RecommendedMealFragment;

/**
 * Created by behiye.avci on 20/12/2016.
 */

public class HomePageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public HomePageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RecommendedMealFragment tab1 = new RecommendedMealFragment();
                return tab1;
            case 1:
                RecommendedFoodServerFragment tab2 = new RecommendedFoodServerFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
