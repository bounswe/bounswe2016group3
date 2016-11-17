package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddMealActivity extends BaseActivity {

    @Bind(R.id.rl_image)
    RelativeLayout rlImage;
    @Bind(R.id.tv_title_meal_name)
    TextView tvTitleMealName;
    @Bind(R.id.tv_meal_name)
    TextView tvMealName;
    @Bind(R.id.rl_name)
    RelativeLayout rlName;
    @Bind(R.id.tv_ingredients_title)
    TextView tvIngredientsTitle;
    @Bind(R.id.activity_add_meal)
    RelativeLayout activityAddMeal;
    @Bind(R.id.sp_ingredients1)
    Spinner spIngredients1;
    @Bind(R.id.et_amount1)
    EditText etAmount1;
    @Bind(R.id.rl_ing1)
    RelativeLayout rlIng1;
    @Bind(R.id.sp_ingredients2)
    Spinner spIngredients2;
    @Bind(R.id.et_amount2)
    EditText etAmount2;
    @Bind(R.id.rl_ing2)
    RelativeLayout rlIng2;
    @Bind(R.id.sp_ingredients3)
    Spinner spIngredients3;
    @Bind(R.id.et_amount3)
    EditText etAmount3;
    @Bind(R.id.rl_ing3)
    RelativeLayout rlIng3;
    @Bind(R.id.sp_ingredients4)
    Spinner spIngredients4;
    @Bind(R.id.et_amount4)
    EditText etAmount4;
    @Bind(R.id.rl_ing4)
    RelativeLayout rlIng4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        //Spinner spinner = (Spinner) findViewById(R.id.sp_ingredients1);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spIngredients1.setAdapter(adapter);
        spIngredients2.setAdapter(adapter);
        spIngredients3.setAdapter(adapter);
        spIngredients4.setAdapter(adapter);

        String ingredientText=new String(spIngredients1.getSelectedItem()+" "+etAmount1+"\n"+
                spIngredients2.getSelectedItem()+" "+etAmount2+"\n"+
                spIngredients3.getSelectedItem()+" "+etAmount3+"\n"+
                spIngredients4.getSelectedItem()+" "+etAmount4+"\n");

    }
}