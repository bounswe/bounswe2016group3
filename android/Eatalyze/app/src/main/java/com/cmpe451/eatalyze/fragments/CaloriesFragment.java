package com.cmpe451.eatalyze.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.WeeklyMeal;

import java.util.ArrayList;
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
    @Bind(R.id.rl_weekly_info)
    RelativeLayout rlWeeklyInfo;
    @Bind(R.id.tv_intake_title)
    TextView tvIntakeTitle;
    @Bind(R.id.lv_daily_calories)
    ListView lvDailyCalories;

    List<WeeklyMeal> weeklyMeals = new ArrayList<WeeklyMeal>();
    List<Meal> mealList = new ArrayList<Meal>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);
        ButterKnife.bind(this, view);

        ((LogActivity) getActivity()).getApiService().getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                tvMealDescription.setText(nutritionalInfo.getCalories() + "");


                ((LogActivity) getActivity()).getApiService().getWeeklyMeals(new Callback<List<WeeklyMeal>>() {
                    @Override
                    public void success(List<WeeklyMeal> meals, Response response) {
                        Log.d("weekly meals suc", meals.size() + "");
                        /*
                        weeklyMeals=meals;
                        User user=((LogActivity)getActivity()).getEatalyzeApplication().getUser();

                        for(int i=0; i<weeklyMeals.size(); i++){
                            Meal currentMeal=((LogActivity) getActivity()).getApiService().getMealByIdSync(weeklyMeals.get(i).getMealId());
                            mealList.add(currentMeal);
                        }
                        MealAdapter adapter = new MealAdapter(getContext(), (ArrayList<Meal>) mealList,user.getFullName());
                        lvDailyCalories.setAdapter(adapter);
                        */
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("weekly meals fail", error.toString());
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
