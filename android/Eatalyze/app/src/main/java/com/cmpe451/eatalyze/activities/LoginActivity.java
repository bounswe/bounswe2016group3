package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.AccessToken;
import com.cmpe451.eatalyze.models.LoginCredentials;
import com.cmpe451.eatalyze.utils.Utils;

import butterknife.Bind;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                            startActivity(new Intent(LoginActivity.this, UserProfilePageActivity.class));
                            finish();
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
}
