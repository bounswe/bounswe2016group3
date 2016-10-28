package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.PagerAdapter;
import com.cmpe451.eatalyze.fragments.UserSignupFragment;
import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.models.UserRequest;
import com.cmpe451.eatalyze.models.UserResponse;
import com.cmpe451.eatalyze.request.ApiService;
import com.cmpe451.eatalyze.utils.Utils;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Behiye on 10/17/2016.
 */

public class SignupActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    android.support.design.widget.TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText("User Signup"));
        tabLayout.addTab(tabLayout.newTab().setText("Food Server Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void signupAct(String email, String password, String fullName,String secretQuestion, String secretAnswer){
        UserRequest userRequest=new UserRequest();
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setFullName(fullName);
        userRequest.setSecretQuestion(secretQuestion);
        userRequest.setSecretAnswer(secretAnswer);

        apiService.signup(userRequest, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                Utils.message(SignupActivity.this,"SUC","SUC",null);
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.message(SignupActivity.this,"FAIL",error.toString(),null);
            }
        });
    }
}
