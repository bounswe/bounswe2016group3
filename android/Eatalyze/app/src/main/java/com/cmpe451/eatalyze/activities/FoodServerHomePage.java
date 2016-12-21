package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.CommentAdapter;
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Menu;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FoodServerHomePage extends BaseActivity {

    @Bind(R.id.tv_welcome_text)
    TextView tvWelcomeText;
    @Bind(R.id.lv_comments)
    ListView lvComments;
    
    static long userid;

    List<User> userList = new ArrayList<User>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_home_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {

        super.onCreate(savedInstances);



        userid = eatalyzeApplication.getUser().getId();

            apiService.getMenus(userid, new Callback<List<Menu>>() {
                @Override
                public void success(List<Menu> menus, Response response) {
                    Log.d("menu size:", ""+menus.size());
                    apiService.getMealsOfMenu(menus.get(0).getId(), new Callback<List<Meal>>() {
                        @Override
                        public void success(List<Meal> meals, Response response) {
                            for(int mealSize = 0; mealSize < meals.size(); mealSize++){
                                apiService.commentsByMeal(meals.get(mealSize).getId(), new Callback<List<Comment>>() {
                                    @Override
                                    public void success(final List<Comment> comments, Response response) {
                                        Log.d("Comment size check-->",comments.size()+"");
                                        if(!comments.isEmpty()){
                                            String welcome =   "Welcome "
                                                                +eatalyzeApplication.getUser().getFullName()+
                                                                ".\nThere are "+comments.size()+
                                                                " comments made on your meals.";
                                            tvWelcomeText.setText(welcome);
                                            apiService.getUsers(new Callback<List<User>>() {
                                                @Override
                                                public void success(List<User> users, Response response) {
                                                    for (int i = 0; i < comments.size(); i++) {
                                                        for (User user:users){
                                                            if(user.getId().equals(comments.get(i).getUserId())){

                                                                userList.add(user);
                                                                break;
                                                            }
                                                        }

                                                    }

                                                    CommentAdapter adapterIncludes=new CommentAdapter(FoodServerHomePage.this, (ArrayList<User>) userList, (ArrayList<Comment>) comments);
                                                    lvComments.setAdapter(adapterIncludes);
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {

                                                }
                                            });
                                        } else if(comments.isEmpty()) {
                                            String welcome = "Welcome "
                                                             +eatalyzeApplication.getUser().getFullName()+
                                                             ".\nThere are 0 comments made on your meals.";
                                            tvWelcomeText.setText(welcome);
                                        }



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

                @Override
                public void failure(RetrofitError error) {

                }
            });


        //apiService.commentsByMeal();
        //CommentAdapter adapterIncludes=new CommentAdapter()
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
