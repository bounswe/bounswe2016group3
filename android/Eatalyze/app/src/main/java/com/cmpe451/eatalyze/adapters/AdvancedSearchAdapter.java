package com.cmpe451.eatalyze.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmpe451.eatalyze.fragments.AdvMealSearchFragment;
import com.cmpe451.eatalyze.fragments.AdvUserSearchFragment;

/**
 * Created by ekrem on 10/12/2016.
 */

public class AdvancedSearchAdapter extends FragmentStatePagerAdapter {

    public AdvancedSearchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                AdvMealSearchFragment tab1 = new AdvMealSearchFragment();
                return tab1;
            case 1:
                AdvUserSearchFragment tab2 = new AdvUserSearchFragment();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
