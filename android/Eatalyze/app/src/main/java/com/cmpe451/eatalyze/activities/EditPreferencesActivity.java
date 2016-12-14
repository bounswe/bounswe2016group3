package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;

import com.cmpe451.eatalyze.R;
import com.squareup.okhttp.ResponseBody;

import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ASUS on 25.10.2016.
 */

public class EditPreferencesActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_preferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO get list first then update
        // update include test
        /*
        String[] list={"pepper"};
        apiService.updatedIncludes(eatalyzeApplication.getUser().getId(), list, new Callback<ResponseBody>() {
            @Override
            public void success(ResponseBody responseBody, Response response) {
                Log.d("Include Update Info","SUC");

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Include Update Info",error.toString());
            }
        });
        */

        //get includes
        /*
        apiService.getIncludes(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                Log.d("ZAA","ZAA");
                Log.d("Include list suc",strings.length+"zaa");
                Log.d("First include element",strings[0]);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ZOO","ZOO");
                Log.d("Getting include list", error.toString());
            }
        });
        */

        //TODO first get list, then send to add already existing file
        //update exclude list
        /*
        String[] list={"onion"};
        apiService.updatedExludes(eatalyzeApplication.getUser().getId(), list, new Callback<ResponseBody>() {
            @Override
            public void success(ResponseBody responseBody, Response response) {
                Log.d("Update exclude suc","suc");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Update exclude fail",error.toString());
            }
        });
        */

        //get exclude list
        /*
        apiService.getExclude(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                Log.d("Get excludes suc",strings.length+"");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Get excludes fail",error.toString());
            }
        });
        */

    }
}
