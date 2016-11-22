package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Tag;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 22/11/2016.
 */

public class TagMealActivity extends BaseActivity {


    @Bind(R.id.et_tag_name)
    EditText etTagName;
    @Bind(R.id.btn_tag_meal)
    Button btnTagMeal;
    @Bind(R.id.gv_tags)
    GridView gvTags;

    @Override
    public int getLayoutId() {
        return R.layout.activity_meal_tag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        apiService.tagsByMeal(meal.getId(), new Callback<List<String>>() {
            @Override
            public void success(List<String> strings, Response response) {
                Log.d("tags fetch success", response.toString());
                //TODO adapt to gridview
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("tag fetch fail", error.toString());
            }
        });

    }

    @OnClick(R.id.btn_tag_meal)
    public void onClick() {

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        Tag tag = new Tag(meal.getId(), etTagName.getText().toString());

        if(tag.getTag().length() > 0) {
            //NOT WORKING
            apiService.tagMeal(eatalyzeApplication.getAccessToken(), tag, new Callback<Tag>() {
                @Override
                public void success(Tag tag, Response response) {
                    Log.d("tagging success", response.toString());
                    etTagName.setText("");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("tagging failure", error.toString());
                }
            });
        }
    }
}
