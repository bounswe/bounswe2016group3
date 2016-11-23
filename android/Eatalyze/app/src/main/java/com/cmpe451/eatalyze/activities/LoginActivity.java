package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.constants.UserType;
import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.LoginCredentials;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Behiye on 10/8/2016.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_signup)
    TextView tvSignup;
    @Bind(R.id.tv_forgotPassword)
    TextView tvForgotPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (eatalyzeApplication.getAccessToken() != null) {
            if(eatalyzeApplication.getUser().getUserType()==0){
                startActivity(new Intent(this, UserHomepageActivity.class));
            }else if(eatalyzeApplication.getUser().getUserType()==1){
                startActivity(new Intent(this, FoodServerProfilePageActivity.class));
            }else{//ADMIN

            }
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.equals("")) {
                    Utils.message(LoginActivity.this, "Login ERROR", "Please enter your email address.", null);
                } else if (password.equals("")) {
                    Utils.message(LoginActivity.this, "Login ERROR", "Please enter your password.", null);
                } else {
                    apiService.login(new LoginCredentials(email, password), new Callback<AccessToken>() {
                        @Override
                        public void success(AccessToken accessToken, Response response) {
                            if (accessToken != null) {
                                eatalyzeApplication.setAccessToken(accessToken);
                                apiService.getCurrentUser(accessToken, new Callback<User>() {
                                    @Override
                                    public void success(User user, Response response) {
                                       eatalyzeApplication.setUser(user);
                                        if(user.getUserType()== 0){
                                            startActivity(new Intent(LoginActivity.this, UserProfilePageActivity.class));
                                        }else if(user.getUserType()==1){
                                            startActivity(new Intent(LoginActivity.this,FoodServerProfilePageActivity.class));
                                        }else{//ADMIN
                                            Log.d("Admin check","Inside admin choice");
                                        }
                                        finish();

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("Getting current user er",error.toString());
                                    }
                                });

                            } else {
                                Utils.message(LoginActivity.this, "Login ERROR", "Wrong email or password.", null);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //TODO log may be implemented to keep track of fails
                        }
                    });
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
    }

    @OnClick(R.id.tv_forgotPassword)
    public void onClick() {
        startActivity(new Intent(LoginActivity.this, EditPreferencesActivity.class));
    }
}
