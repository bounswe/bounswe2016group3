package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.MealTagsAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.Tag;

import java.util.ArrayList;

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
    @Bind(R.id.et_identifier)
    EditText etIdentifier;

    MealTagsAdapter adapter;

    //List<Tag> tagsOnMeal = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_meal_tag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        //TODO CHECK THIS IF WORKING
        apiService.tagsByMeal(meal.getId(), new Callback<ArrayList<Tag>>() {
            @Override
            public void success(ArrayList<Tag> tags, Response response) {
                Log.d("Fetching success", response.toString());
                adapter = new MealTagsAdapter(TagMealActivity.this, tags);
                gvTags.setAdapter(adapter);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Fetching failure", error.toString());
            }
        });


    }

    @OnClick(R.id.btn_tag_meal)
    public void onClick() {

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        Long relationType = new Long(2);
        Long relationId = meal.getId();
        String displayName = etTagName.getText().toString();
        String identifier = etIdentifier.getText().toString();
        if (displayName.length() > 0 && identifier.length() > 0) {
            Tag tag = new Tag(relationType, relationId, displayName, identifier);

            apiService.tagMeal(eatalyzeApplication.getAccessToken(), tag, new Callback<Tag>() {
                @Override
                public void success(Tag tag, Response response) {
                    etTagName.setText("");
                    etIdentifier.setText("");
                    adapter.notifyDataSetChanged();
                    //CharSequence text = "Meal tagged as" + tag.getDisplayName() + " successfully";
                    //Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    //toast.show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("tagging failure", error.toString());
                }
            });

        } else {
            CharSequence text = "Please enter a proper tag (e.g name:identifier)";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
