package com.cmpe451.eatalyze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.models.Ratings;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.views.ExpandableTextView;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


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
    @Bind(R.id.btn_send_comment)
    Button btnSendComment;
    @Bind(R.id.tv_total_calorie)
    TextView tvTotalCalorie;
    @Bind(R.id.btn_comments)
    Button btnComments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_meal;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        //TODO this with real clicked meal
        apiService.getNutrition(meal.getId(), new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                String totalCalorie="";
                if(nutritionalInfo!=null)
                    totalCalorie = "Total calories: " + nutritionalInfo.getCalories() + " kcal";
                else
                    totalCalorie = "Total calorie is not specified";
                tvTotalCalorie.setText(totalCalorie);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });



        //TODO this with real clicked meal
       /* apiService.getMealById(new Long(34), new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                etvIngredient.setText(meal.getIngredients());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });*/
        if(meal.getIngredients()==null) etvIngredient.setText("Ingredients are not specified");
        else etvIngredient.setText(meal.getIngredients());


        apiService.getRatings(eatalyzeApplication.getAccessToken(), meal.getId(), new Callback<Ratings>() {
            @Override
            public void success(Ratings ratings, Response response) {
                Log.d("Ratings fetch success", response.toString());
                if(ratings.getAverage() != null)
                rbMealRating.setRating(ratings.getAverage());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Ratings fetch fail", error.toString());
            }
        });



        tvMealName.setText(meal.getName());

        //etvIngredient.setText(meal.getIngredients());

        if(meal.getPhotoUrl()!="")
            Picasso.with(ViewMealActivity.this).load(meal.getPhotoUrl()).into(ivMealImage);
        /*else
            Picasso.with(ViewMealActivity.this).load("https://image.freepik.com/free-icon/fork-and-knife-in-cross_318-61306.jpg).into(ivMealImage");
        */

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


        rbMealRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

                apiService.getRatings(eatalyzeApplication.getAccessToken(), meal.getId(), new Callback<Ratings>() {
                    @Override
                    public void success(Ratings ratings, Response response) {
                        String rating1 = "" + rating;
                        String rating2 = ""+ ratings.getAverage();
                        Log.d("rating1", rating1);
                        Log.d("rating2", rating2);
                        if((rating < ratings.getAverage() - 0.1 ||  rating > ratings.getAverage() + 0.1) ){
                            apiService.rateMeal(eatalyzeApplication.getAccessToken(), meal.getId(), rating, new Callback<ResponseBody>() {
                                @Override
                                public void success(ResponseBody responseBody, Response response) {
                                    Log.d("Rating sent", response.toString());
                                    CharSequence text = "You have rated " + rating + "/5!";
                                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("Rating sent failure", error.toString());
                                    Toast toast = Toast.makeText(getApplicationContext(), "You have rated before, sorry!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });


            }
        });
    }





    @OnClick({R.id.btn_check_eat, R.id.btn_tag_meal, R.id.btn_nutrition_info, R.id.btn_comments})
    public void onClick(View view) {
        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");
        switch (view.getId()) {
            case R.id.btn_check_eat:
                Intent intent = new Intent(ViewMealActivity.this, TagPeopleActivity.class);
                intent.putExtra("ClickedMeal", meal);
                startActivity(intent);
                break;
            case R.id.btn_tag_meal:
                intent = new Intent(ViewMealActivity.this, TagMealActivity.class);
                intent.putExtra("ClickedMeal", meal);
                startActivity(intent);
                break;
            case R.id.btn_nutrition_info:
                intent = new Intent(ViewMealActivity.this, NutritionInfoActivity.class);
                intent.putExtra("ClickedMeal", meal);
                startActivity(intent);
                break;
            case R.id.btn_comments:
                intent = new Intent(ViewMealActivity.this, ViewMealCommentsActivity.class);
                intent.putExtra("ClickedMeal", meal);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.btn_send_comment)
    public void onClick() {

        Meal meal = (Meal) getIntent().getSerializableExtra("ClickedMeal");

        String content = etComment.getText().toString();
        Log.d("success", content);

        Long mealId = meal.getId();
        Log.d("success mealid", mealId.toString());

        Long userId = eatalyzeApplication.getUser().getId();
        Log.d("success userid", userId.toString());

        long creationTime = new DateTime().getMillis();
        long updateTime = new DateTime().getMillis();

        Comment comment = new Comment(mealId, userId, content, creationTime, updateTime);


        if (content.length() > 0) {

            apiService.createComment(eatalyzeApplication.getAccessToken(), comment, new Callback<Comment>() {
                @Override
                public void success(Comment comment, Response response) {
                    Log.d("Comment sent success", response.toString());
                    etComment.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "Comment sent successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Comment sent fail", error.toString());
                }
            });

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Write something sir!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

}
