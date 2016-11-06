package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.FoodServer;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.hello_name)
    TextView helloName;
    @Bind(R.id.tv_recommendedmeal)
    TextView tvRecommendedmeal;
    @Bind(R.id.rec_meal_list)
    ListView recMealList;
    @Bind(R.id.tv_recommendedserver)
    TextView tvRecommendedserver;
    @Bind(R.id.rec_server_list)
    ListView rec_server;

    @Bind(R.id.btn_logout)
    Button btnLogout;

    final List<Meal> recMeals = new ArrayList<Meal>();

    final List<FoodServer> recFoodServers = new ArrayList<FoodServer>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_homepage;
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String userName =eatalyzeApplication.getUser().getFullName();
        String welcomeText = "Hello, " + userName;
        helloName.setText(welcomeText);

        apiService.getMenu(new Long(1), new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                Log.d("SUC Meal Call",meal.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Mail Call",error.toString());
            }
        });

        apiService.getUserByID(new Long(17), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("SUC Food Server Call",user.getFullName());
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
