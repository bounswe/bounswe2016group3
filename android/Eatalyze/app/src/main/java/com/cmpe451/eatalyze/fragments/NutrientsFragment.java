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

public class NutrientsFragment extends Fragment {
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrients, container, false);
        ButterKnife.bind(this, view);

        //TODO real amount value will be put
        NutritionInfoView view1=new NutritionInfoView(getContext(), "Protein","100"+" gr");
        llContent.addView(view1);
        NutritionInfoView view2=new NutritionInfoView(getContext(), "Total Carbohydrate","100"+" gr");
        llContent.addView(view2);
        NutritionInfoView view3=new NutritionInfoView(getContext(), "Total Fat","100"+" gr");
        llContent.addView(view3);
        NutritionInfoView view4=new NutritionInfoView(getContext(), "Sugars","100"+" mg");
        llContent.addView(view4);
        NutritionInfoView view5=new NutritionInfoView(getContext(), "Saturated Fat","100"+" mg");
        llContent.addView(view5);
        NutritionInfoView view6=new NutritionInfoView(getContext(), "Cholesterol","100"+" mg");
        llContent.addView(view6);
        NutritionInfoView view7=new NutritionInfoView(getContext(), "Dietary Fiber","100"+" mg");
        llContent.addView(view7);
        NutritionInfoView view8=new NutritionInfoView(getContext(), "Sodium","100"+" mg");
        llContent.addView(view8);
        NutritionInfoView view9=new NutritionInfoView(getContext(), "Potassium","100"+" mg");
        llContent.addView(view9);
        NutritionInfoView view10=new NutritionInfoView(getContext(), "Phosphorus","100"+" mg");
        llContent.addView(view10);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
