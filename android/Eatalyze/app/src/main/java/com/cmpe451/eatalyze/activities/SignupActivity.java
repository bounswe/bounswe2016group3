package com.cmpe451.eatalyze.activities;

import android.content.Intent;
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
import com.cmpe451.eatalyze.constants.UserType;
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

    public void signupAct(String email, String password, String fullName, String secretQuestion, String secretAnswer, UserType userType){
        UserRequest userRequest=new UserRequest();
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setFullName(fullName);
        userRequest.setSecretQuestion(secretQuestion);
        userRequest.setSecretAnswer(secretAnswer);
        userRequest.setUserType(userType);

        //TODO create pick a picture option in SignupActivity or ProfilePage activities
        if(userType==UserType.FOOD_SERVER){
            if(fullName.equals("Nusr'et"))
                userRequest.setAvatarUrl("http://postkes.com/wp-content/uploads/2016/01/Screenshot_11.png");
            else if(fullName.equals("Bolulu Hasan Usta"))
                userRequest.setAvatarUrl("http://www.bhu.com.tr/images/tema/image/logo.png");
            else if(fullName.equals("Aperatif"))
                userRequest.setAvatarUrl("https://upload.wikimedia.org/wikipedia/en/3/3a/Burger_King_Logo.svg");  //not png?
            else{ //default
                userRequest.setAvatarUrl("http://emojipedia-us.s3.amazonaws.com/cache/cf/d7/cfd7a98b83e6c87bf54c4f656e812008.png");
            }
        }

        //TODO create pick a picture option in SignupActivity or ProfilePage activities
        if(userType==UserType.REGULAR){
            if(fullName.equals("Vedat Milor")){
                userRequest.setAvatarUrl("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQI3P3aaGM01z1Uxsbs-qCDxXDY3rkKPoZwDfx9o2_2IqQZp2Y9");
            }else if(fullName.equals("Ekrem Öztürk")){
                userRequest.setAvatarUrl("https://scontent-frt3-1.xx.fbcdn.net/v/t1.0-1/c0.74.160.160/p160x160/14369863_10154084785657809_8326659837332353757_n.jpg?oh=24dac75e84ebfdae385df46347b74361&oe=5888AAEE");
            }else if(fullName.equals("Muharrem Yeşilyurt")){
                userRequest.setAvatarUrl("https://scontent-frt3-1.xx.fbcdn.net/v/t1.0-9/994616_10201023277717342_140240186_n.jpg?oh=be53671e84af56a7af4db633e05eb992&oe=58D39D0F");
            }else{
                userRequest.setAvatarUrl("http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png");
            }
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }
}
