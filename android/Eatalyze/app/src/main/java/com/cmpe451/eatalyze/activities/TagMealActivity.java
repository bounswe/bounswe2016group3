package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

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

        String tagName = etTagName.getText().toString();

        Long relationType = new Long(2);
        Long relationId = meal.getId();
        String displayName = "";
        String identifier = "";
        if(tagName.contains(":")){
            int tagIndex = tagName.indexOf(":");
            displayName = tagName.substring(0, tagIndex);
            identifier = tagName.substring(tagIndex+1);
        } else {
            CharSequence text = "Please enter a proper tag (e.g name:identifier)";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }

        Tag tag = new Tag(relationType, relationId, displayName, identifier);

        //TODO remove tagging by server check

        apiService.tagMeal(eatalyzeApplication.getAccessToken(), tag, new Callback<Tag>() {
            @Override
            public void success(Tag tag, Response response) {
                etTagName.setText("");
                CharSequence text = "Meal tagged as" +tag.getDisplayName()+ " successfully";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("tagging failure", error.toString());
            }
        });

    }
}
