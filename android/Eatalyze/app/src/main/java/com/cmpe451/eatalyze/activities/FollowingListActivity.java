package com.cmpe451.eatalyze.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


public class FollowingListActivity extends BaseActivity {
    @Bind(R.id.appBar)
    Toolbar appBar;

    @Bind(R.id.listView)
    ListView listView;

    Context context;
    List<User> userList_ = new ArrayList<>();
    static Bundle bund;
    static long userid;
    @Override
    public int getLayoutId() {

        return R.layout.activity_following_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        context = getApplicationContext();

        bund = getIntent().getExtras();
        userid=-1;
        if(bund!=null){
            userid = bund.getLong("userid");
            apiService.getfollowing(userid, new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    for(int a = 0  ; a <userList.size(); a++){
                        userList_.add(userList.get(a));
                        Log.d("Succesful", "Succes");
                        FollowersAdapter adapter = new FollowersAdapter(context, userList_);
                        ListView list = (ListView) findViewById(R.id.listView);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Selected(position);
                            }
                        });
                    }
                }
                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed", error.toString());
                }
            });
        }
        else{
            apiService.getfollowing(eatalyzeApplication.getUser().getId(), new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    for(int a = 0  ; a <userList.size(); a++) {
                        userList_.add(userList.get(a));
                        Log.d("Succesful", "Succes");
                        FollowersAdapter adapter = new FollowersAdapter(context, userList_);
                        ListView list = (ListView) findViewById(R.id.listView);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Selected(position);
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Failed", error.toString());
                }
            });
        }
    }

    public void Selected(int position){
        Long user_id = userList_.get(position).getId();

        if(userList_.get(position).getUserType()==0) {
            Intent intent = new Intent(FollowingListActivity.this, UserProfilePageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userid", user_id);
            intent.putExtras(bundle);
            startActivity(intent);
          //  finish();
        }
        else{

            Intent intent = new Intent(FollowingListActivity.this, FoodServerProfilePageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userid", user_id);
            intent.putExtras(bundle);
            startActivity(intent);
          //  finish();

        }
    }
}
