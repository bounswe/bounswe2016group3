package com.cmpe451.eatalyze.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.FollowersAdapter;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.GONE;


/**
 * Created by ASUS on 22.11.2016.
 */

public class SearchActivity extends BaseActivity {
    static Bundle bund;
    static String search;
    Context context;
    List<User> usersearchresults = new ArrayList<>();
    List<Meal> mealsearchresults = new ArrayList<>();
    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.lv_usersearchresult)
    ListView lvUsersearchresult;
    @Bind(R.id.ly_usersearch)
    LinearLayout lyUsersearch;
    @Bind(R.id.lv_mealsearchresult)
    ListView lvMealsearchresult;
    @Bind(R.id.ly_mealsearch)
    LinearLayout lyMealsearch;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        context = getApplicationContext();


        bund = getIntent().getExtras();
        if (bund != null) {
            search = bund.getString("query");

            apiService.userSearch(search, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    if(userList.isEmpty()){
                        lyUsersearch.setVisibility(GONE);
                    }
                    for (int a = 0; a < userList.size(); a++) {
                        usersearchresults.add(userList.get(a));
                        Log.d("USERLIST: ", userList.get(a).getFullName() + "");
                        FollowersAdapter adapter = new FollowersAdapter(context, usersearchresults);
                        ListView list = (ListView) findViewById(R.id.lv_usersearchresult);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Selected(position);
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Fail: ", error.toString());
                }
            });

            apiService.mealSearch(search, new Callback<List<Meal>>() {
                @Override
                public void success(List<Meal> meals, Response response) {
                    if(meals.isEmpty()){
                        lyMealsearch.setVisibility(GONE);
                    }
                    for (int a = 0; a < meals.size(); a++) {
                        mealsearchresults.add(meals.get(a));

                        apiService.getUserByID(meals.get(a).getUserId(), new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                MealAdapter adapter = new MealAdapter(context, (ArrayList<Meal>) mealsearchresults, user.getFullName());
                                ListView list = (ListView) findViewById(R.id.lv_mealsearchresult);
                                list.setAdapter(adapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Meal clickedMeal= (Meal) parent.getItemAtPosition(position);
                                        //Log.d("Num of clicked item", i+"");
                                        Intent intent=new Intent(SearchActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", clickedMeal);
                                        startActivity(intent);

                                        }

                                });
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });


                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
    public void Selected(int position) {
        Long user_id = usersearchresults.get(position).getId();

        if (usersearchresults.get(position).getUserType() == 0) {
            Intent intent = new Intent(SearchActivity.this, UserProfilePageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userid", user_id);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(SearchActivity.this, FoodServerProfilePageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userid", user_id);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }
    }





}


