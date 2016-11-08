package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.views.ExpandableTextView;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by ekrem on 05/11/2016.
 */

public class ViewMealActivity extends BaseActivity {


    @Bind(R.id.iv_meal_image)
    ImageView ivMealImage;
    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.tv_server_name)
    TextView tvServerName;
    @Bind(R.id.btn_check_eat)
    Button btnCheckEat;
    @Bind(R.id.btn_tag_meal)
    Button btnTagMeal;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.btn_nutrition_info)
    Button btnNutritionInfo;
    @Bind(R.id.etv_ingredient)
    ExpandableTextView etvIngredient;
    @Bind(R.id.rb_meal_rating)
    RatingBar rbMealRating;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_meal;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");
        //Log.d("Meal name check",meal.getName());
    }


    @OnClick({R.id.btn_check_eat, R.id.btn_tag_meal, R.id.btn_nutrition_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_eat:
                startActivity(new Intent(ViewMealActivity.this, TagPeopleActivity.class));
                break;
            case R.id.btn_tag_meal:
                break;
            case R.id.btn_nutrition_info:
                startActivity(new Intent(ViewMealActivity.this, NutritionInfoActivity.class));
                break;
        }
    }

    @OnClick(R.id.rb_meal_rating)
    public void onClick() {
    }
}
