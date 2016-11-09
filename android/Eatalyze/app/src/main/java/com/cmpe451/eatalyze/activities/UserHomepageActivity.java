package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.FoodServerAdapter;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 23/10/2016.
 */

public class UserHomepageActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.tv_hello_name)
    TextView tvHelloName;
    @Bind(R.id.tv_rec_meals_title)
    TextView tvRecMealsTitle;
    @Bind(R.id.lv_rec_meals)
    ListView lvRecMeals;
    @Bind(R.id.tv_recommended_server)
    TextView tvRecommendedServer;
    @Bind(R.id.lv_rec_food_servers)
    ListView lvRecFoodServers;

    List<Meal> recMealList = new ArrayList<Meal>();
    List<User> recFoodServerList = new ArrayList<User>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_homepage;
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String userName = eatalyzeApplication.getUser().getFullName();
        String welcomeText = "Hello, " + userName;

        //TODO change this with butterknife version
        TextView tvHelloName= (TextView) findViewById(R.id.tv_hello_name);
        tvHelloName.setText(welcomeText);

        //TODO get list of meals instead of only one
        apiService.getMenu(new Long(1), new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                Log.d("SUC Meal Call", meal.getName());

                for (int i = 0; i < 3; i++) {
                    recMealList.add(meal);
                }

                MealAdapter adapter = new MealAdapter(UserHomepageActivity.this, (ArrayList<Meal>) recMealList);
                //TODO change this to butterknife version
                ListView lvRecMeals= (ListView) findViewById(R.id.lv_rec_meals);
                lvRecMeals.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Mail Call", error.toString());
            }
        });

        //TODO get list of food server instead of only one
        apiService.getUserByID(new Long(17), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("SUC Food Server Call", user.getFullName());

                for (int i = 0; i < 3; i++) {
                    recFoodServerList.add(user);
                }

                FoodServerAdapter adapter = new FoodServerAdapter(UserHomepageActivity.this, (ArrayList<User>) recFoodServerList);
                ListView lvRecFoodServers= (ListView) findViewById(R.id.lv_rec_food_servers);
                lvRecFoodServers.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Food Server Call", error.toString());
            }
        });

        lvRecMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal clickedMeal= (Meal) adapterView.getItemAtPosition(i);
                //Log.d("Num of clicked item", i+"");

                Intent intent=new Intent(UserHomepageActivity.this,ViewMealActivity.class);
                intent.putExtra("ClickedMeal", clickedMeal);

                startActivity(intent);
            }
        });
    }
}
