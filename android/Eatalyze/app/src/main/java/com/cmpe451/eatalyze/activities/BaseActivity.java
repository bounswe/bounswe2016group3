package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmpe451.eatalyze.EatalyzeApplication;
import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.request.ApiService;
import com.cmpe451.eatalyze.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import butterknife.ButterKnife;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Behiye on 10/8/2016.
 *
 * This class is used for methods which are common for all other activities
 */
public abstract class BaseActivity extends AppCompatActivity {
    ApiService apiService;
    private RestAdapter restAdapter;
    EatalyzeApplication eatalyzeApplication;
    SharedPreferences sharedPreferences;

    public abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);
        eatalyzeApplication = (EatalyzeApplication) getApplication();
        sharedPreferences = eatalyzeApplication.getSp();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

       RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-type","application/json");
                request.addHeader("Authorization","Bearer "+eatalyzeApplication.getAccessToken().getAccessToken());
            }
        };

        restAdapter = new RestAdapter.Builder()
    //                .setClient(new RetrofitHttpClient())
                //.setErrorHandler(new MyErrorHandler())
                .setRequestInterceptor(requestInterceptor)
                //.setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
               .setClient(new OkClient(new OkHttpClient()))

                .setEndpoint(getString(R.string.base_url))
                .build();
        apiService = restAdapter.create(ApiService.class);
    }
}
