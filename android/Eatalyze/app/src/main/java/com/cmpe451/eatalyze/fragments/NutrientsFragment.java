package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.views.NutritionInfoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Behiye on 11/20/2016.
 */

public class NutrientsFragment extends Fragment {
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nutrients, container, false);
        ButterKnife.bind(this, view);

        ((LogActivity)getActivity()).getApiService().getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {

                //TODO real amount value will be put
                NutritionInfoView view1=new NutritionInfoView(getContext(), "Protein",""+nutritionalInfo.getProtein()+""+" gr");
                llContent.addView(view1);
                NutritionInfoView view2=new NutritionInfoView(getContext(), "Total Carbohydrate",""+nutritionalInfo.getTotalCarbohydrate()+""+" gr");
                llContent.addView(view2);
                NutritionInfoView view3=new NutritionInfoView(getContext(), "Total Fat",""+nutritionalInfo.getTotalFat()+""+" gr");
                llContent.addView(view3);
                NutritionInfoView view4=new NutritionInfoView(getContext(), "Sugars",""+nutritionalInfo.getSugars()+""+" mg");
                llContent.addView(view4);
                NutritionInfoView view5=new NutritionInfoView(getContext(), "Saturated Fat",""+nutritionalInfo.getSaturatedFat()+""+" mg");
                llContent.addView(view5);
                NutritionInfoView view6=new NutritionInfoView(getContext(), "Cholesterol",""+nutritionalInfo.getSaturatedFat()+""+" mg");
                llContent.addView(view6);
                NutritionInfoView view7=new NutritionInfoView(getContext(), "Dietary Fiber",""+nutritionalInfo.getDietaryFiber()+""+" mg");
                llContent.addView(view7);
                NutritionInfoView view8=new NutritionInfoView(getContext(), "Sodium",""+nutritionalInfo.getSodium()+""+" mg");
                llContent.addView(view8);
                NutritionInfoView view9=new NutritionInfoView(getContext(), "Potassium",""+nutritionalInfo.getPotassium()+""+" mg");
                llContent.addView(view9);
                NutritionInfoView view10=new NutritionInfoView(getContext(), "Phosphorus",""+nutritionalInfo.getPhosphorus()+""+" mg");
                llContent.addView(view10);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
