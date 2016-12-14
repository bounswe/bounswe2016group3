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

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_server_home_page;
    }

    @Override
    public void onCreate(Bundle savedInstances) {

        super.onCreate(savedInstances);

        final ArrayList<User> userList=new ArrayList<>();
        userList.add(eatalyzeApplication.getUser());

        ArrayList<Comment> commentList=new ArrayList<>();
        apiService.commentsByMeal(new Long(1), new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                Log.d("Comment size check-->",comments.size()+"");
                //CommentAdapter adapter=new CommentAdapter(FoodServerHomePage.this,userList, (ArrayList<Comment>) comments);
                //lvComments.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        //apiService.commentsByMeal();
        //CommentAdapter adapter=new CommentAdapter()
    }
}
