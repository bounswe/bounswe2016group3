package com.cmpe451.eatalyze.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.CommentAdapter;
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by ekrem on 21/11/2016.
 */

public class ViewMealCommentsActivity extends BaseActivity {

    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.lv_comments)
    ListView lvComments;

    List<Comment> commentsOnMeal = new ArrayList<Comment>();
    List<User> userList = new ArrayList<User>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_meal_comments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        String mealName = "Comments made on "+meal.getName();

        tvMealName.setText(mealName);


        apiService.commentsByMeal(meal.getId(), new Callback<List<Comment>>() {
            @Override
            public void success(final List<Comment> comments, Response response) {
                if (!comments.isEmpty()) {
                    Log.d("comment fetch success", response.toString());

                    apiService.getUsers(new Callback<List<User>>() {
                        @Override
                        public void success(List<User> users, Response response) {
                            //TODO Get comment specific user data
                            for (int i = 0; i < comments.size(); i++) {
                                for (User user:users){
                                    if(user.getId().equals(comments.get(i).getUserId())){
                                        commentsOnMeal.add(comments.get(i));
                                        userList.add(user);
                                        break;
                                    }
                                }

                            }

                            CommentAdapter adapter = new CommentAdapter(ViewMealCommentsActivity.this, (ArrayList<User>) userList, (ArrayList<Comment>) commentsOnMeal);
                            lvComments.setAdapter(adapter);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }


            @Override
            public void failure(RetrofitError error) {
                Log.d("comment fetch failure", error.toString());
            }
        });

        lvComments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment comment = (Comment) parent.getItemAtPosition(position);
                apiService.getUserByID(comment.getUserId(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.d("user call success", response.toString());
                        Intent intent = new Intent(ViewMealCommentsActivity.this, UserProfilePageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("userid", user.getId());
                        intent.putExtras(bundle);
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
