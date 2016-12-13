package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Behiye on 11/20/2016.
 */

public class CaloriesFragment extends Fragment {
    @Bind(R.id.tv_calorie_info)
    TextView tvCalorieInfo;
    @Bind(R.id.tv_meal_description)
    TextView tvMealDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);
        ButterKnife.bind(this, view);

        ((LogActivity) getActivity()).getApiService().getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                tvMealDescription.setText(nutritionalInfo.getCalories()+"");

                ((LogActivity) getActivity()).getApiService().getWeeklyMeals(new Callback<List<Meal>>() {
                    @Override
                    public void success(List<Meal> meals, Response response) {
                        Log.d("weekly meals suc","SUC");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("weekly meals fail",error.toString());
                    }
                });
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
