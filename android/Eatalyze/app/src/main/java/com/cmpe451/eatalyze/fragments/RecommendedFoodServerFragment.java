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
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.FoodServerProfilePageActivity;
import com.cmpe451.eatalyze.activities.UserHomepageActivity;
import com.cmpe451.eatalyze.activities.ViewMealActivity;
import com.cmpe451.eatalyze.adapters.FoodServerAdapter;
import com.cmpe451.eatalyze.models.Meal;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.request.ApiService;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by behiye.avci on 20/12/2016.
 */

public class RecommendedFoodServerFragment extends Fragment {
    View view;
    @Bind(R.id.tv_hello_name)
    TextView tvHelloName;
    @Bind(R.id.lv_rec_food_serverss)
    ListView lvRecFoodServers;

    List<User> recFoodServerList = new ArrayList<User>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommended_food_servers, container, false);
        ButterKnife.bind(this, view);

        String userName = ((UserHomepageActivity) getActivity()).getEatalyzeApplication().getUser().getFullName();
        String welcomeText = "Hello, " + userName;

        tvHelloName.setText(welcomeText);

        ApiService apiService=((UserHomepageActivity) getActivity()).getApiService();
        //TODO get list of food server instead of only one
        apiService.getUserByID(new Long(225), new Callback<User>() {

            @Override
            public void success(User user, retrofit.client.Response response) {
                Log.d("SUC Food Server Call", user.getFullName());

                recFoodServerList.add(user);

                FoodServerAdapter adapter = new FoodServerAdapter(getContext(), (ArrayList<User>) recFoodServerList);
                lvRecFoodServers.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Food Server Call", error.toString());
            }
        });

        apiService.getUserByID(new Long(97), new Callback<User>() {

            @Override
            public void success(User user, retrofit.client.Response response) {
                Log.d("SUC Food Server Call", user.getFullName());

                recFoodServerList.add(user);

                FoodServerAdapter adapter = new FoodServerAdapter(getContext(), (ArrayList<User>) recFoodServerList);
                lvRecFoodServers.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Food Server Call", error.toString());
            }
        });

        apiService.getUserByID(new Long(211), new Callback<User>() {


            @Override
            public void success(User user, retrofit.client.Response response) {
                Log.d("SUC Food Server Call", user.getFullName());

                recFoodServerList.add(user);

                FoodServerAdapter adapter = new FoodServerAdapter(getContext(), (ArrayList<User>) recFoodServerList);
                lvRecFoodServers.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failed Food Server Call", error.toString());
            }
        });

        //FoodServerAdapter adapter = new FoodServerAdapter(getContext(), (ArrayList<User>) recFoodServerList);
        //lvRecFoodServers.setAdapter(adapter);
        /*
        Log.d("asdasdads",recFoodServerList.size() + "");
        lvRecFoodServers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User clickedUser= (User) adapterView.getItemAtPosition(i);
                Log.d("clickedServer", ""+i);
                Intent intent=new Intent(getContext(), FoodServerProfilePageActivity.class);
                intent.putExtra("ClickedUser", clickedUser);
                startActivity(intent);
            }
        });
        */

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
