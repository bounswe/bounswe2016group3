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

import com.cmpe451.eatalyze.EatalyzeApplication;
import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.WeeklyMeal;
import com.cmpe451.eatalyze.request.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
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

    List<Meal> mealList = new ArrayList<Meal>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);
        ButterKnife.bind(this, view);


        final ApiService apiService=((LogActivity) getActivity()).getApiService();

        final EatalyzeApplication eatalyzeApplication=((LogActivity)getActivity()).getEatalyzeApplication();
        final User user=eatalyzeApplication.getUser();

        apiService.getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                Log.d("Suc Nutritional Info",nutritionalInfo.getCalories()+"");
                tvMealDescription.setText(nutritionalInfo.getCalories()+"");

                apiService.getWeeklyMeals(new Callback<List<WeeklyMeal>>() {
                    @Override
                    public void success(final List<WeeklyMeal> weekMeal, Response response) {
                        Log.d("weekly meals suc", weekMeal.size() + "");

                        apiService.getEatenMeals(eatalyzeApplication.getUser().getId(), new Callback<List<Meal>>() {
                            @Override
                            public void success(List<Meal> eatenMeals, Response response) {
                                Log.d("Eaten meal",eatenMeals.size()+"");

                                ArrayList<Long> ids=new ArrayList<Long>();
                                for(int i=0; i<weekMeal.size(); i++){
                                    ids.add(weekMeal.get(i).getMealId());
                                }

                                for(int i=0; i<ids.size(); i++){
                                    for(int k=0; k<eatenMeals.size(); k++){
                                        if(ids.get(i).equals(eatenMeals.get(k).getId())){
                                            mealList.add(eatenMeals.get(k));
                                        }
                                    }
                                }

                                Log.d("MAP SIZE",mealList.size()+"");
                                MealAdapter adapter = new MealAdapter(getContext(), (ArrayList<Meal>) mealList,"weekly");
                                lvDailyCalories.setAdapter(adapter);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("weekly meals fail", error.toString());
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Nutritional Info",error.toString());
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
