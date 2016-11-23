package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.squareup.okhttp.ResponseBody;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 08/11/2016.
 */

public class TagPeopleActivity extends BaseActivity {
    @Bind(R.id.lv_following_list)
    ListView lvFollowingList;
    @Bind(R.id.btn_check_eat)
    Button btnCheckEat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag_following_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation



    }

    @OnClick(R.id.btn_check_eat)
    public void onClick(View view) {
        final Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");
                apiService.checkEat(eatalyzeApplication.getAccessToken(), meal.getId(), new Callback<ResponseBody>() {
                    @Override
                    public void success(ResponseBody responseBody, Response response) {
                        Log.d("Check eat success", "yes");
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Check eat fail", "no");

                    }
                });

    }
}
