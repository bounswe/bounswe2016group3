package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ekrem on 05/11/2016.
 */

public class ViewMealActivity extends BaseActivity {


    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.iv_meal_image)
    ImageView ivMealImage;
    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.btn_check_eat)
    Button btnCheckEat;
    @Bind(R.id.btn_tag_meal)
    Button btnTagMeal;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.tv_ingredient)
    TextView tvIngredient;
    @Bind(R.id.lv_ingredient)
    ListView lvIngredient;
    @Bind(R.id.tv_nutrition)
    TextView tvNutrition;
    @Bind(R.id.lv_nutrition)
    ListView lvNutrition;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_meal;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_check_eat, R.id.btn_tag_meal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_eat:
                break;
            case R.id.btn_tag_meal:
                break;
        }
    }
}
