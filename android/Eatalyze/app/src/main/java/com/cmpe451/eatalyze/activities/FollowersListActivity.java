package com.cmpe451.eatalyze.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.FollowersAdapter;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FollowersListActivity extends BaseActivity {
    @Bind(R.id.appBar)
    Toolbar appBar;

    @Bind(R.id.listView)
    ListView listView;

    Context context;
    List<User> userList_ = new ArrayList<>();

    @Override
    public int getLayoutId() {

        return R.layout.activity_followers_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        context = getApplicationContext();

        apiService.getfollowers(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
            @Override
            public void success(List<User> userList, Response response) {


                for(int a = 0  ; a <userList.size(); a++){
                    userList_.add(userList.get(a));
                    Log.d("Succesful", "Succes");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed", error.toString());
            }
        });

        FollowersAdapter adapter = new FollowersAdapter(this, userList_);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
}
