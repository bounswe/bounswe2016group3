package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.utils.Utils;

import butterknife.Bind;

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
                    Utils.message(LoginActivity.this, "Login Error", "Please enter your email address.", null);
                } else if (password.equals("")) {
                    Utils.message(LoginActivity.this, "Login Error", "Wrong password.", null);
                } else {
                    //TODO checking real users
                }
            }
        });
    }
}
