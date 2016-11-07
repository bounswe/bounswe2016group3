package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.FoodServerAdapter;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

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
    @Bind(R.id.btn_logout)
    Button btnLogout;

    List<Meal> recMealList = new ArrayList<Meal>();
    List<User> recFoodServerList = new ArrayList<User>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_homepage;
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String userName =eatalyzeApplication.getUser().getFullName();
        String welcomeText = "Hello, " + userName;
        tvHelloName.setText(welcomeText);

        //TODO get list of meals instead of only one
        apiService.getMenu(new Long(1), new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                Log.d("SUC Meal Call",meal.getName());

                for(int i=0; i<3; i++){
                    recMealList.add(meal);
                }

                MealAdapter adapter=new MealAdapter(UserHomepageActivity.this, (ArrayList<Meal>) recMealList);
                lvRecMeals.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Mail Call",error.toString());
            }
        });

        //TODO get list of food server instead of only one
        apiService.getUserByID(new Long(17), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("SUC Food Server Call",user.getFullName());

                for(int i=0; i<3; i++){
                    recFoodServerList.add(user);
                }

                FoodServerAdapter adapter=new FoodServerAdapter(UserHomepageActivity.this, (ArrayList<User>) recFoodServerList);
                lvRecFoodServers.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Food Server Call",error.toString());
            }
        });
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        SharedPreferences preferences =eatalyzeApplication.getSp();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        eatalyzeApplication.setAccessToken(null);
        Intent intent=new Intent(this, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
