package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.squareup.okhttp.ResponseBody;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddMealActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
/*
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

    */
        apiService.addMeal(new Meal(new Long(1), new Long(1), new Long(1), "meal check behiye", "description behiye", "100 grams of tomato, 100 grams of salt", ""), new Callback<ResponseBody>() {
            @Override
            public void success(ResponseBody responseBody, Response response) {
                Log.d("Succ add meal",responseBody.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Fail add meal",error.toString());
            }
        });
    }

}