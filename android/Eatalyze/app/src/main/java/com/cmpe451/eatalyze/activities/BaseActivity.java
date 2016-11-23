package com.cmpe451.eatalyze.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cmpe451.eatalyze.EatalyzeApplication;
import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.User;
import com.cmpe451.eatalyze.request.ApiService;
import com.cmpe451.eatalyze.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Behiye on 10/8/2016.
 *
 * This class is used for methods which are common for all other activities
 */
public abstract class BaseActivity extends AppCompatActivity {
    ApiService apiService;
    RestAdapter restAdapter; //may be private
    EatalyzeApplication eatalyzeApplication;
    SharedPreferences sharedPreferences;

    public abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        handleIntent(getIntent());

        Toolbar appBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(appBar);

        ButterKnife.bind(this);
        eatalyzeApplication = (EatalyzeApplication) getApplication();
        sharedPreferences = eatalyzeApplication.getSp();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-type", "application/json");
                if (eatalyzeApplication.getAccessToken() != null)
                    request.addHeader("Authorization", "Bearer " + eatalyzeApplication.getAccessToken().getAccessToken());

            }
        };

        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(getString(R.string.base_url))
                .build();
        apiService = restAdapter.create(ApiService.class);

        Intent searchIntent = getIntent();

        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);

            Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("query",query);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    public void onNewIntent(Intent intent){
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("success ", query);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_Search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        String userName = eatalyzeApplication.getUser().getFullName();
        userName += "";
        menu.findItem(R.id.id_profil_page).setTitle(userName);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_logout:
                SharedPreferences preferences = eatalyzeApplication.getSp();
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                eatalyzeApplication.setAccessToken(null);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.id_profil_page:

                if(eatalyzeApplication.getUser().getUserType() == 0) {
                    Log.d("User is regular user: ", " Success");
                    startActivity(new Intent(BaseActivity.this, UserProfilePageActivity.class));
                }
                else{
                    startActivity(new Intent(BaseActivity.this, FoodServerProfilePageActivity.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
