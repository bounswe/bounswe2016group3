package com.cmpe451.eatalyze.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmpe451.eatalyze.fragments.CaloriesFragment;
import com.cmpe451.eatalyze.fragments.MacroFragment;
import com.cmpe451.eatalyze.fragments.NutrientsFragment;

/**
 * Created by Behiye on 11/20/2016.
 */

public class LogAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public LogAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CaloriesFragment tab1 = new CaloriesFragment();
                return tab1;
            case 1:
                NutrientsFragment tab2 = new NutrientsFragment();
                return tab2;
            case 2:
                MacroFragment tab3=new MacroFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
