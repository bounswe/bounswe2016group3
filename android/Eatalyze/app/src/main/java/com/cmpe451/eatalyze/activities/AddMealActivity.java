package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.IngredientAdapter;
import com.cmpe451.eatalyze.models.Ingredient;
import com.cmpe451.eatalyze.models.Meal;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class AddMealActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.iv_meal_image)
    ImageView ivMealImage;
    @Bind(R.id.btn_edit_meal)
    Button btnEditMeal;
    @Bind(R.id.rl_meal_image)
    RelativeLayout rlMealImage;
    @Bind(R.id.rl_meal_info)
    RelativeLayout rlMealInfo;
    @Bind(R.id.tv_ingredient_title)
    TextView tvIngredientTitle;
    @Bind(R.id.iv_add_icon)
    ImageView ivAddIcon;
    @Bind(R.id.rl_ingredient_title)
    RelativeLayout rlIngredientTitle;
    @Bind(R.id.lv_ingredients)
    ListView lvIngredients;
    @Bind(R.id.btn_add_meal)
    Button btnAddMeal;
    @Bind(R.id.et_meal_name)
    EditText etMealName;
    @Bind(R.id.et_meal_description)
    EditText etMealDescription;

    ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
    IngredientAdapter adapter;
    double amount=0;
    String name="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        adapter = new IngredientAdapter(AddMealActivity.this, ingredientList);
        lvIngredients.setAdapter(adapter);

    }

    //TODO add ingredients
    @OnClick(R.id.iv_add_icon)
    public void addButtonClicked() {
        /*
        Double amount=0.0;
        String name="";
        if(ingredientList.size()!=0){
            View v=lvIngredients.getChildAt(ingredientList.size()-1);
            amount=Double.parseDouble(((EditText)v.findViewById(R.id.et_amount)).getText().toString());
            name=((EditText)v.findViewById(R.id.et_indgredient)).getText().toString();
            ingredientList.get(ingredientList.size()-1).setName(name);
            ingredientList.get(ingredientList.size()-1).setAmount(amount);
        }else{
            ingredientList.add(new Ingredient());
        }
        */
        ingredientList.add(new Ingredient());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_add_meal)
    public void addMealClicked() {
        String ingredient = "";
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredient = ingredient + ingredientList.get(i).getAmount() + " grams of " + ingredientList.get(i).getName() + " ";
        }

        String mealName = etMealName.toString();
        String desc = etMealDescription.toString();
        //TODO get menu of different users
        apiService.addMeal(new Meal(null, new Long(1), eatalyzeApplication.getUser().getId(), mealName, desc, ingredient, ""));
    }
}