package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ekrem on 08/11/2016.
 */

public class NutritionInfoActivity extends BaseActivity {


    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_weight_amount)
    TextView tvWeightAmount;
    @Bind(R.id.tv_calories)
    TextView tvCalories;
    @Bind(R.id.tv_calorie_amount)
    TextView tvCalorieAmount;
    @Bind(R.id.tv_fat)
    TextView tvFat;
    @Bind(R.id.tv_fat_amount)
    TextView tvFatAmount;
    @Bind(R.id.tv_sat_fat)
    TextView tvSatFat;
    @Bind(R.id.tv_satfat_amount)
    TextView tvSatfatAmount;
    @Bind(R.id.tv_cholesterol)
    TextView tvCholesterol;
    @Bind(R.id.tv_cholesterol_amount)
    TextView tvCholesterolAmount;
    @Bind(R.id.tv_sodium)
    TextView tvSodium;
    @Bind(R.id.tv_sodium_amount)
    TextView tvSodiumAmount;
    @Bind(R.id.tv_carbohydrate)
    TextView tvCarbohydrate;
    @Bind(R.id.tv_carbohydrate_amount)
    TextView tvCarbohydrateAmount;
    @Bind(R.id.tv_fiber)
    TextView tvFiber;
    @Bind(R.id.tv_fiber_amount)
    TextView tvFiberAmount;
    @Bind(R.id.tv_sugars)
    TextView tvSugars;
    @Bind(R.id.tv_sugars_amount)
    TextView tvSugarsAmount;
    @Bind(R.id.tv_protein)
    TextView tvProtein;
    @Bind(R.id.tv_protein_amount)
    TextView tvProteinAmount;
    @Bind(R.id.tv_potassium)
    TextView tvPotassium;
    @Bind(R.id.tv_potassium_amount)
    TextView tvPotassiumAmount;
    @Bind(R.id.tv_phosphorus)
    TextView tvPhosphorus;
    @Bind(R.id.tv_phosphorus_amount)
    TextView tvPhosphorusAmount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nutrition_info;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        String mealName = "Nuritional Info of " + meal.getName();

        tvMealName.setText(mealName);


        //TODO Try with real ingredient data
        apiService.getNutrition(meal.getId(), new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                Log.d("success", response.toString());
                String amount = "" + nutritionalInfo.getWeight();
                tvWeightAmount.setText(amount);
                amount = "" + nutritionalInfo.getCalories();
                tvCalorieAmount.setText(amount);
                amount = "" + nutritionalInfo.getTotalFat();
                tvFatAmount.setText(amount);
                amount = "" + nutritionalInfo.getSaturatedFat();
                tvSatfatAmount.setText(amount);
                amount = "" + nutritionalInfo.getCholesterol();
                tvCholesterolAmount.setText(amount);
                amount = "" + nutritionalInfo.getSodium();
                tvSodiumAmount.setText(amount);
                amount = "" + nutritionalInfo.getTotalCarbohydrate();
                tvCarbohydrateAmount.setText(amount);
                amount = "" + nutritionalInfo.getDietaryFiber();
                tvFiber.setText(amount);
                amount = "" + nutritionalInfo.getSugars();
                tvSugarsAmount.setText(amount);
                amount = "" + nutritionalInfo.getProtein();
                tvProteinAmount.setText(amount);
                amount = "" + nutritionalInfo.getPotassium();
                tvPotassiumAmount.setText(amount);
                amount = "" + nutritionalInfo.getPhosphorus();
                tvPhosphorusAmount.setText(amount);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
