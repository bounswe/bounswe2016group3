package com.cmpe451.eatalyze.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Menu;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.Unfollow;
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
    static Bundle bundle;
    static long userid;
    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_profile_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        bundle = getIntent().getExtras();
        userid = -1 ;


        //**************************VIEWER**************************************//
        if(bundle != null && bundle.getLong("userid") != eatalyzeApplication.getUser().getId()){

            userid = bundle.getLong("userid");

            apiService.getUserByID(userid, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    tvBio.setText(user.getBio());
                    tvFullName.setText(user.getFullName());
                    Picasso.with(FoodServerProfilePageActivity.this).load(user.getAvatarUrl()).into(ivProfilePhoto);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getMenus(userid, new Callback<List<Menu>>() {
                @Override
                public void success(final List<Menu> menus, Response response) {
                    if (!menus.isEmpty()) {
                        //Log.d("Menu server call success. # of meals ->", menus.get(0).getId() + "");
                        FoodServerProfilePageActivity.this.menus = (ArrayList<Menu>) menus;

                        // TODO find a better way to do that and food server can have more than one menu
                        apiService.getMealsOfMenu(menus.get(0).getId(), new Callback<List<Meal>>() {
                            @Override
                            public void success(List<Meal> meals, Response response) {
                                Log.d("succ meal list call", meals.size() + "");
                                FoodServerProfilePageActivity.this.mealOfMenu = (ArrayList<Meal>) meals;

                                final String[] foodServerName = {""};
                                apiService.getUserByID(menus.get(0).getUserId(), new Callback<User>() {
                                    @Override
                                    public void success(User user, Response response) {
                                        Log.d("User by id call is SUC", user.getFullName());
                                        foodServerName[0] = user.getFullName();
                                       // Log.d("URL", mealOfMenu.get(0).getPhotoUrl());

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
                }
                @Override
                public void failure(RetrofitError error) {
                    Log.d("Menu server call fail", error.toString());
                }
            });

            apiService.getfollowers(userid, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    tvFollowers.setText("Followers: " + userList.size());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


            tvFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = userid;
                    Intent intent = new Intent(FoodServerProfilePageActivity.this, FollowersListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("userid", user_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                   // finish();
                }
            });

            btnAddMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Oopss!! You can not do this..",Toast.LENGTH_SHORT).show();
                }
            });

            apiService.getfollowing(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(final List<User> userList, Response response) {
                    apiService.getUserByID(userid, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            for(int a = 0; a < userList.size(); a++){
                                if(userList.get(a).getId().equals(user.getId())){
                                    btnFollow.setText("FOLLOWING");
                                    break;
                                }
                                else{
                                    btnFollow.setText("FOLLOW");

                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnFollow.getText().equals("FOLLOW")){
                        apiService.follow(userid, new Callback<Follow>() {
                            @Override
                            public void success(Follow follow, Response response) {
                                btnFollow.setText("FOLLOWING");
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(FoodServerProfilePageActivity.this);
                        builder.setTitle("Unfollow");
                        builder.setIcon(R.drawable.ic_logo_eatalyze);
                        builder.setMessage("Stop Following ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                apiService.unfollow(userid, new Callback<Unfollow>() {
                                    @Override
                                    public void success(Unfollow unfollow, Response response) {
                                        dialog.dismiss();
                                        btnFollow.setText("FOLLOW");
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                }
            });

            lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(FoodServerProfilePageActivity.this,ViewMealActivity.class);
                    Meal clickedMeal= (Meal) adapterView.getItemAtPosition(i);
                    intent.putExtra("ClickedMeal",clickedMeal);
                    startActivity(intent);
                }
            });
        }

        //***************FOOD SERVER**********************//
        else {

            btnFollow.setText("EDIT PROFILE");
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

            apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    tvFollowers.setText("Followers: " + userList.size());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


            apiService.getMenus(eatalyzeApplication.getUser().getId(), new Callback<List<Menu>>() {
                @Override
                public void success(final List<Menu> menus, Response response) {
                    if (!menus.isEmpty()) {
                        FoodServerProfilePageActivity.this.menus = (ArrayList<Menu>) menus;

                        apiService.getMealsOfMenu(menus.get(0).getId(), new Callback<List<Meal>>() {
                            @Override
                            public void success(List<Meal> meals, Response response) {
                                Log.d("succ meal list call.", meals.size() + "");
                                FoodServerProfilePageActivity.this.mealOfMenu = (ArrayList<Meal>) meals;

                                String currentFoodServer=eatalyzeApplication.getUser().getFullName();
                                MealAdapter adapter = new MealAdapter(FoodServerProfilePageActivity.this, mealOfMenu, currentFoodServer);
                                lvMenu.setAdapter(adapter);

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("fail meal list call.", error.toString());
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Menu server call fail", error.toString());
                }
            });

            tvFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = eatalyzeApplication.getUser().getId();
                    Intent intent = new Intent(FoodServerProfilePageActivity.this, FollowersListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("userid", user_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                  //  finish();
                }
            });

            btnAddMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodServerProfilePageActivity.this, AddMealActivity.class));
                }
            });

            lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(FoodServerProfilePageActivity.this,ViewMealActivity.class);
                    Meal clickedMeal= (Meal) adapterView.getItemAtPosition(i);
                    intent.putExtra("ClickedMeal",clickedMeal);
                    startActivity(intent);
                }
            });
        }
    }
}
