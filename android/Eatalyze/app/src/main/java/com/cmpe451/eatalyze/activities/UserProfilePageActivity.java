package com.cmpe451.eatalyze.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.Follow;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Unfollow;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.views.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Muharrem on 21.10.2016.
 */

public class UserProfilePageActivity extends BaseActivity {

    @Bind(R.id.iv_profile_photo)
    ImageView profil_pic;
    @Bind(R.id.tv_followers)
    TextView followers;
    @Bind(R.id.id_following)
    TextView following;
    @Bind(R.id.tv_bio)
    TextView bio;
    @Bind(R.id.tv_full_name)
    TextView fullName;
    @Bind(R.id.btn_follow)
    Button btn_follow;
    @Bind(R.id.btn_log)
    Button btnLog;
    @Bind(R.id.btn_diet)
    Button btnDiet;
    @Bind(R.id.id_includes)
    TextView includes;
    @Bind(R.id.id_excludes)
    TextView excludes;
    @Bind(R.id.id_preferences)
    TextView preferences;

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.ll_top_layout)
    LinearLayout llTopLayout;
    @Bind(R.id.id_expandabletextView)
    ExpandableTextView idExpandabletextView;
    @Bind(R.id.id_expandabletextView1)
    ExpandableTextView idExpandabletextView1;
    @Bind(R.id.id_expandabletextView2)
    ExpandableTextView idExpandabletextView2;
    @Bind(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @Bind(R.id.id_big_layout)
    LinearLayout idBigLayout;

    static Bundle bundle;
    static long userid;


    @Override
    public int getLayoutId() {

        return R.layout.activity_user_profil_page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        userid = -1;
        if (bundle != null) {
            userid = bundle.getLong("userid");

            apiService.getUserComments(userid, new Callback<List<Comment>>() {
                @Override
                public void success(final List<Comment> comments, Response response) {
                   final int a = comments.size();

                    if(a==0){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText("Please add a comment..");
                        idExpandabletextView2.setText("Please add a comment..");
                    }
                    else if(a==1){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText("Please add a comment..");
                        idExpandabletextView2.setText(comments.get(0).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(0).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                    else if(a==2){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText(comments.get(1).getContent());
                        idExpandabletextView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(1).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView2.setText(comments.get(0).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(0).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                    else {
                        idExpandabletextView.setText(comments.get(a - 1).getContent());
                        idExpandabletextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 1).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView1.setText(comments.get(a - 2).getContent());
                        idExpandabletextView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 2).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView2.setText(comments.get(a - 3).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 3).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });



            apiService.getfollowing(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(final List<User> userList, Response response) {
                    apiService.getUserByID(userid, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            for (int a = 0; a < userList.size(); a++) {
                                if (userList.get(a).getId().equals(user.getId())) {
                                    btn_follow.setText("FOLLOWING");
                                    break;
                                } else {
                                    btn_follow.setText("FOLLOW");

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

                    Log.d("FAİLED", error.toString());
                }
            });

            apiService.getfollowers(userid, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    followers.setText("Followers: " + userList.size());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getfollowing(userid, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    following.setText("Following: " + userList.size());

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(final List<User> userList, Response response) {
                    apiService.getUserByID(userid, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            for (int a = 0; a < userList.size(); a++) {
                                if (userList.get(a).getId().equals(user.getId()) && eatalyzeApplication.getUser().getUserType() == 1) {
                                    btn_follow.setText("FOLLOWING YOU");
                                    break;
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

            apiService.getUserByID(userid, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    bio.setText(user.getBio());
                    fullName.setText(user.getFullName());
                    Picasso.with(UserProfilePageActivity.this).load(user.getAvatarUrl()).into(profil_pic);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            btn_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn_follow.getText().equals("FOLLOW")) {
                        apiService.follow(userid, new Callback<Follow>() {
                            @Override
                            public void success(Follow follow, Response response) {
                                btn_follow.setText("FOLLOWING");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("Fail follow", error.toString());
                            }
                        });
                    } else if (btn_follow.getText().equals("FOLLOWING")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfilePageActivity.this);
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
                                        btn_follow.setText("FOLLOW");
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("fail UNFULLOW:", error.toString());
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

            followers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = eatalyzeApplication.getUser().getId();
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowersListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowersListActivity.class));
                    }
                }
            });

            following.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = eatalyzeApplication.getUser().getId();
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowingListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowingListActivity.class));
                    }
                }
            });
        } else {


            btn_follow.setText("Edit Profile");

            apiService.getUserComments(eatalyzeApplication.getUser().getId(), new Callback<List<Comment>>() {
                @Override
                public void success(final List<Comment> comments, Response response) {

                    final int a = comments.size();

                    if(a==0){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText("Please add a comment..");
                        idExpandabletextView2.setText("Please add a comment..");
                    }
                    else if(a==1){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText("Please add a comment..");
                        idExpandabletextView2.setText(comments.get(0).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(0).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                    else if(a==2){
                        idExpandabletextView.setText("Please add a comment..");
                        idExpandabletextView1.setText(comments.get(1).getContent());
                        idExpandabletextView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(1).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView2.setText(comments.get(0).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(0).getMealId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                    else {
                        idExpandabletextView.setText(comments.get(a - 1).getContent());
                        idExpandabletextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 1).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView1.setText(comments.get(a - 2).getContent());
                        idExpandabletextView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 2).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                        idExpandabletextView2.setText(comments.get(a - 3).getContent());
                        idExpandabletextView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiService.getMealById(comments.get(a - 3).getId(), new Callback<Meal>() {
                                    @Override
                                    public void success(Meal meal, Response response) {
                                        Intent intent=new Intent(UserProfilePageActivity.this,ViewMealActivity.class);
                                        intent.putExtra("ClickedMeal", meal);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        });
                    }
                }
                @Override
                public void failure(RetrofitError error) {

                }
            });

            apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(List<User> users, Response response) {
                    followers.setText("Followers: " + users.size());
                    Log.d("Number of users: ", users.size() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed", error.toString());
                }
            });

            apiService.getfollowing(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(List<User> users, Response response) {
                    following.setText("Following: " + users.size());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed", error.toString());
                }
            });

            Log.d("Access token control", eatalyzeApplication.getAccessToken() + "");
            apiService.getCurrentUser(eatalyzeApplication.getAccessToken(), new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    Log.d("Suc User Page", "Suc");
                    bio.setText(user.getBio());
                    fullName.setText(user.getFullName());
                    Picasso.with(UserProfilePageActivity.this).load(user.getAvatarUrl()).into(profil_pic);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed User Page", error.toString());
                }
            });
        }

        if (bundle == null) {

            followers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = eatalyzeApplication.getUser().getId();
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowersListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowersListActivity.class));
                    }
                }
            });

            following.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = eatalyzeApplication.getUser().getId();
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowingListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowingListActivity.class));
                    }
                }
            });
            btnDiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start diet activity
                    startActivity(new Intent(UserProfilePageActivity.this, DietActivity.class));
                }
            });

            btnLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start log activity
                    startActivity(new Intent(UserProfilePageActivity.this, LogActivity.class));
                }
            });

            preferences.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start edit preferences activity
                    startActivity(new Intent(UserProfilePageActivity.this, EditPreferencesActivity.class));
                }
            });
        } else {
            userid = bundle.getLong("userid");

            followers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = userid;
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowersListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowersListActivity.class));
                    }
                }
            });

            following.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long user_id = userid;
                    if (bundle != null) {
                        Intent intent = new Intent(UserProfilePageActivity.this, FollowingListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(UserProfilePageActivity.this, FollowingListActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (bundle == null || userid == eatalyzeApplication.getUser().getId()) {
            super.onBackPressed();
            startActivity(new Intent(UserProfilePageActivity.this, UserHomepageActivity.class));
            finish();
        } else {
            super.onBackPressed();
            Intent intent = new Intent(UserProfilePageActivity.this, FollowersListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userid", eatalyzeApplication.getUser().getId());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}


