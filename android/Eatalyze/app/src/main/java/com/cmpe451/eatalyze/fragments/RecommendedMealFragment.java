package com.cmpe451.eatalyze.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.UserHomepageActivity;
import com.cmpe451.eatalyze.activities.ViewMealActivity;
import com.cmpe451.eatalyze.adapters.MealAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by behiye.avci on 20/12/2016.
 */

public class RecommendedMealFragment extends Fragment {
    View view;
    @Bind(R.id.tv_hello_name)
    TextView tvHelloName;
    @Bind(R.id.lv_rec_meals)
    ListView lvRecMeals;

    List<Meal> recMealList = new ArrayList<Meal>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommended_meals, container, false);
        ButterKnife.bind(this, view);

        String userName = ((UserHomepageActivity) getActivity()).getEatalyzeApplication().getUser().getFullName();
        String welcomeText = "Hello, " + userName;

        tvHelloName.setText(welcomeText);

        //TODO make this for recommended meals, not random ones
        ((UserHomepageActivity) getActivity()).getApiService().getMealById(new Long(51), new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                Log.d("SUC meal call", meal.getName());
                for (int i = 0; i < 3; i++) {
                    recMealList.add(meal);
                }

                ((UserHomepageActivity) getActivity()).getApiService().getUserByID(meal.getUserId(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        MealAdapter adapter = new MealAdapter(getContext(), (ArrayList<Meal>) recMealList, user.getFullName());
                        lvRecMeals.setAdapter(adapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("FAIL meal call", error.toString());
            }
        });

        lvRecMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal clickedMeal= (Meal) adapterView.getItemAtPosition(i);
                //Log.d("Num of clicked item", i+"");

                Intent intent=new Intent(getContext(), ViewMealActivity.class);
                intent.putExtra("ClickedMeal", clickedMeal);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
