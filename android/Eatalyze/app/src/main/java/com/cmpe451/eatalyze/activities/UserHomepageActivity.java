package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.FoodServer;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 23/10/2016.
 */

public class UserHomepageActivity extends BaseActivity {

    @Bind(R.id.hello_name)
    TextView hello_name;

    @Bind(R.id.rec_meal_list)
    ListView rec_meal;

    @Bind(R.id.rec_server_list)
    ListView rec_server;

    final List<Meal>  recMeals = new ArrayList<Meal>();

    final List<FoodServer> recFoodServers = new ArrayList<FoodServer>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_homepage;
    }

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_homepage);

        apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                String welcomeText = "Hello, " + user.getFullName();
                hello_name.setText(welcomeText);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }






}
