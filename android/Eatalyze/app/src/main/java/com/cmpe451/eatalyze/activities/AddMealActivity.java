package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.IngredientAdapter;
import com.cmpe451.eatalyze.models.Ingredient;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Menu;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddMealActivity extends BaseActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Uri selectedImage;

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
    double amount = 0;
    String name = "";
    @Bind(R.id.et_indgredient)
    EditText etIndgredient;
    @Bind(R.id.et_amount)
    EditText etAmount;
    @Bind(R.id.rl_add_ingredient_item)
    RelativeLayout rlAddIngredientItem;
    @Bind(R.id.sp_amount)
    Spinner spAmount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        Spinner spinner = (Spinner) spAmount;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.amount_array,
                R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        adapter = new IngredientAdapter(AddMealActivity.this, ingredientList);
        lvIngredients.setAdapter(adapter);

    }

    //TODO add ingredients
    @OnClick(R.id.iv_add_icon)
    public void addButtonClicked() {
        ingredientList.add(new Ingredient(etIndgredient.getText().toString(), Double.parseDouble(etAmount.getText().toString())));
        adapter.notifyDataSetChanged();
        etIndgredient.setText("");
        etAmount.setText("");
        etIndgredient.setHint("ingredient");
        etAmount.setHint("Amount");
    }


    @OnClick(R.id.btn_add_meal)
    public void addMealClicked() {
        String ingredient = "";
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredient = ingredient + ingredientList.get(i).getAmount() + " grams of " + ingredientList.get(i).getName() + " ";
        }

        final String mealName = etMealName.getText().toString();
        final String desc = etMealDescription.getText().toString();
        //TODO get menu of different users
        final String finalIngredient = ingredient;
        apiService.getMenus(eatalyzeApplication.getUser().getId(), new Callback<List<Menu>>() {
            @Override
            public void success(List<Menu> menus, Response response) {
                apiService.addMeal(new Meal(null, menus.get(0).getId(), eatalyzeApplication.getUser().getId(), mealName, desc, finalIngredient, "https://image.freepik.com/free-icon/fork-and-knife-in-cross_318-61306.jpg"), new Callback<ResponseBody>() {
                    @Override
                    public void success(ResponseBody responseBody, Response response) {
                        Log.d("Adding meal suc", "SUC");
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Adding meal fail", error.toString());
                    }
                });

                Intent intent = new Intent(AddMealActivity.this, FoodServerProfilePageActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.iv_meal_image);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    //TODO will be implemented
    @OnClick(R.id.btn_edit_meal)
    public void onClick() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                1);//SELECT_FILE);
    }
}