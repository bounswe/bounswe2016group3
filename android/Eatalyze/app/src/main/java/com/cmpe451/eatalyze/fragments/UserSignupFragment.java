package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.SignupActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserSignupFragment extends Fragment {

    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_fullName)
    EditText etFullName;
    @Bind(R.id.et_secretQuestion)
    EditText etSecretQuestion;
    @Bind(R.id.et_secretAnswer)
    EditText etSecretAnswer;
    @Bind(R.id.btn_signup)
    Button btnSignup;
    @Bind(R.id.activity_user_signup_fragment)
    RelativeLayout activityUserSignupFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_signup)
    public void signupClicked(){
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String fullName=etFullName.getText().toString();
        String secretQuestion= etSecretQuestion.getText().toString();
        String secretAnswer=etSecretAnswer.getText().toString();
        ((SignupActivity)getActivity()).signupAct(email,password,fullName,secretQuestion,secretAnswer);
    }

}
