package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.LogAdapter;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.WeeklyMeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ASUS on 25.10.2016.
 */

public class LogActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    List<WeeklyMeal> weeklyMeals = new ArrayList<WeeklyMeal>();
    public List<Meal> mealList = new ArrayList<Meal>();

    public NutritionalInfo nutritionalInfo=new NutritionalInfo();

    @Override
    public int getLayoutId() {
        return R.layout.activity_log;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService.getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                Log.d("Suc Nutritional Info",nutritionalInfo.getCalories()+"");
                LogActivity.this.nutritionalInfo=nutritionalInfo;


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
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                        /*
                        apiService.getEatenMeals(eatalyzeApplication.getUser().getId(), new Callback<List<Meal>>() {
                            @Override
                            public void success(List<Meal> meals, Response response) {
                                HashMap<Long,Meal> mealMap=new HashMap<Long,Meal>();

                                String str = "";
                                for(int i=0; i<meals.size(); i++){
                                    mealMap.put(meals.get(i).getId(),meals.get(i));
                                    str+=meals.get(i).getId()+" ";
                                }

                                Log.d("ALL EATEN ",str);

                                String str2="";
                                for(int i=0; i<weekMeal.size(); i++){
                                    mealList.add(mealMap.get(weeklyMeals.get(i).getMealId()));
                                    str2+=weeklyMeals.get(i).getMealId()+" ";

                                }

                                Log.d("Weekly eaten ",str2);


                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("Get eaten FAIL", error.toString());
                            }
                        });
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
                Log.d("Failed Nutritional Info",error.toString());
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Calories"));
        tabLayout.addTab(tabLayout.newTab().setText("Nutrients"));
        tabLayout.addTab(tabLayout.newTab().setText("Macros"));

        final LogAdapter adapter = new LogAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
