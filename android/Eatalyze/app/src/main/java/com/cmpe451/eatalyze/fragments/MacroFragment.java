package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.views.NutritionInfoView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Behiye on 11/20/2016.
 */

public class MacroFragment extends Fragment {

    @Bind(R.id.ll_content)
    LinearLayout llContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_macro, container, false);
        ButterKnife.bind(this, view);

        //TODO add piechart for macros

        NutritionInfoView view1 = new NutritionInfoView(getContext(), "Protein", "100" + " gr");
        llContent.addView(view1);
        NutritionInfoView view2 = new NutritionInfoView(getContext(), "Total Carbohydrate", "100" + " gr");
        llContent.addView(view2);
        NutritionInfoView view3 = new NutritionInfoView(getContext(), "Total Fat", "100" + " gr");
        llContent.addView(view3);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
