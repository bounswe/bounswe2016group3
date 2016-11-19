package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Menu;
import com.cmpe451.eatalyze.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FoodServerProfilePageActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.ll_top_layout)
    LinearLayout idTopLayout;
    @Bind(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @Bind(R.id.id_big_layout)
    LinearLayout idBigLayout;
    @Bind(R.id.iv_profile_photo)
    ImageView ivProfilePhoto;
    @Bind(R.id.btn_follow)
    Button btnFollow;
    @Bind(R.id.tv_full_name)
    TextView tvFullName;
    @Bind(R.id.tv_followers)
    TextView tvFollowers;
    @Bind(R.id.tv_bio)
    TextView tvBio;
    @Bind(R.id.btn_add_meal)
    Button btnAddMeal;
    @Bind(R.id.lv_menu)
    ListView lvMenu;

    ArrayList<Menu> menus;
    ArrayList<Meal> mealOfMenu;

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_profile_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        // gets current user
        apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("Suc User Page", "Suc");
                tvBio.setText(user.getBio());
                tvFullName.setText(user.getFullName());
                Picasso.with(FoodServerProfilePageActivity.this).load(user.getAvatarUrl()).into(ivProfilePhoto);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed User Page", error.toString());
            }
        });

        // gets menus
        apiService.getMenus(eatalyzeApplication.getUser().getId(), new Callback<List<Menu>>() {
            @Override
            public void success(final List<Menu> menus, Response response) {
                Log.d("Menu server call success. # of meals ->", menus.get(0).getId() + "");
                FoodServerProfilePageActivity.this.menus = (ArrayList<Menu>) menus;

                // TODO find a better way to do that and food server can have more than one menu
                apiService.getMealsOfMenu(menus.get(0).getId(), new Callback<List<Meal>>() {
                    @Override
                    public void success(List<Meal> meals, Response response) {
                        Log.d("succ meal list call. SIZE ->", meals.size() + "");
                        FoodServerProfilePageActivity.this.mealOfMenu = (ArrayList<Meal>) meals;

                        final String[] foodServerName = {""};
                        apiService.getUserByID(menus.get(0).getUserId(), new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                Log.d("User by id call is SUC", user.getFullName());
                                foodServerName[0] = user.getFullName();
                                Log.d("URL", mealOfMenu.get(0).getPhotoUrl());

                                //TODO put ingredients and nutritional info

                                MealAdapter adapter = new MealAdapter(FoodServerProfilePageActivity.this, mealOfMenu, foodServerName[0]);
                                lvMenu.setAdapter(adapter);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("User by id call is FAIL", error.toString());
                            }
                        });

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("fail meal list call.", error.toString());
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Menu server call fail", error.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FoodServerProfilePageActivity.this, FoodServerHomePage.class));
        finish();
    }

    @OnClick(R.id.btn_add_meal)
    public void addMealClicked() {
        startActivity(new Intent(FoodServerProfilePageActivity.this, AddMealActivity.class));
    }

    @OnClick({R.id.iv_profile_photo, R.id.btn_follow, R.id.tv_full_name, R.id.tv_followers})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_profile_photo:
                break;
            case R.id.btn_follow:
                break;
            case R.id.tv_full_name:
                break;
            case R.id.tv_followers:
                startActivity(new Intent(FoodServerProfilePageActivity.this, FollowersListActivity.class));
                break;
        }
    }

}
