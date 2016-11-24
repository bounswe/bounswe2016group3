package com.cmpe451.eatalyze.activities;


import android.os.Bundle;
import android.util.Log;
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
            public void success(List<Comment> comments, Response response) {
                if (!comments.isEmpty()) {
                    Log.d("comment fetch success", response.toString());
                    //TODO Get comment specific user data
                    for (int i = 0; i < comments.size(); i++) {
                        commentsOnMeal.add(comments.get(i));
                        User ekrem = new User();
                        ekrem.setFullName("Ekrem Ozturk");
                        userList.add(ekrem);
                    }

                    CommentAdapter adapter = new CommentAdapter(ViewMealCommentsActivity.this, (ArrayList<User>) userList, (ArrayList<Comment>) commentsOnMeal);
                    lvComments.setAdapter(adapter);


                }
            }


            @Override
            public void failure(RetrofitError error) {
                Log.d("comment fetch failure", error.toString());
            }
        });

    }
}
