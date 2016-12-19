package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.views.ExpandableTextView;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 08/11/2016.
 */

public class CheckEatActivity extends BaseActivity {
    @Bind(R.id.btn_check_eat)
    Button btnCheckEat;
    @Bind(R.id.iv_meal_image)
    ImageView ivMealImage;
    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.tv_server_name)
    TextView tvServerName;
    @Bind(R.id.tv_total_calorie)
    TextView tvTotalCalorie;
    @Bind(R.id.etv_description)
    ExpandableTextView etvDescription;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_eat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

        final Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        if (meal.getPhotoUrl() != "")
            Picasso.with(CheckEatActivity.this).load(meal.getPhotoUrl()).into(ivMealImage);

        tvMealName.setText(meal.getName());

        etvDescription.setText(meal.getDescription());

        apiService.getUserByID(new Long(meal.getUserId()), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                String username = "by " + user.getFullName();
                tvServerName.setText(username);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        apiService.getNutrition(meal.getId(), new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                String totalCalorie = "";
                String infoText = "";
                if (nutritionalInfo != null) {
                    totalCalorie = nutritionalInfo.getCalories() + " kcal";
                    infoText = "You will get " + nutritionalInfo.getTotalCarbohydrate() + " grams of carbonhydrates, "
                                +nutritionalInfo.getTotalFat()+ " grams of fat, "
                                +nutritionalInfo.getProtein()+ " grams of protein by eating this meal.";

                }
                    //totalCalorie = "Total calorie is not specified";
                    tvTotalCalorie.setText(totalCalorie);
                    tvInfo.setText(infoText);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    @OnClick(R.id.btn_check_eat)
    public void onClick(View view) {
        final Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");
        apiService.checkEat(eatalyzeApplication.getAccessToken(), meal.getId(), new Callback<ResponseBody>() {
            @Override
            public void success(ResponseBody responseBody, Response response) {
                Log.d("Check eat success", "yes");
                Toast toast = Toast.makeText(getApplicationContext(), "You have check-eat'ed!", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Check eat fail", "no");
                Toast toast = Toast.makeText(getApplicationContext(), "Fix this", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

    }
}
