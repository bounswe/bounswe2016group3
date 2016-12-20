package com.cmpe451.eatalyze.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.cmpe451.eatalyze.fragments.FoodServerSignupFragment;
import com.cmpe451.eatalyze.fragments.UserSignupFragment;

/**
 * Created by Behiye on 10/26/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UserSignupFragment tab1 = new UserSignupFragment();
                Log.d("Signup Tab Check","user selected");
                return tab1;
            case 1:
                FoodServerSignupFragment tab2 = new FoodServerSignupFragment();
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