package com.cmpe451.eatalyze.activities;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.adapters.PreferenceAdapter;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ASUS on 25.10.2016.
 */

public class EditPreferencesActivity extends BaseActivity {
    @Bind(R.id.tv_include_title)
    TextView tvIncludeTitle;
    @Bind(R.id.tv_exclude_title)
    TextView tvExcludeTitle;
    @Bind(R.id.iv_add_exclude_icon)
    ImageView ivAddExcludeIcon;

    @Bind(R.id.et_include_tag)
    EditText etIncludeTag;
    @Bind(R.id.gv_includes)
    GridView gvIncludes;

    PreferenceAdapter adapterIncludes;
    PreferenceAdapter adapterExcludes;
    @Bind(R.id.iv_add_include_icon)
    ImageView ivAddIncludeIcon;
    @Bind(R.id.rl_add_include)
    RelativeLayout rlAddInclude;
    @Bind(R.id.et_exclude_tag)
    EditText etExcludeTag;
    @Bind(R.id.rl_add_exclude)
    RelativeLayout rlAddExclude;
    @Bind(R.id.gv_excludes)
    GridView gvExcludes;

    ArrayList<String> includeList = new ArrayList<String>();
    ArrayList<String> excludeList = new ArrayList<String>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_preferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // INCLUDES
        apiService.getIncludes(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                for (int i = 0; i < strings.length; i++) {
                    includeList.add(strings[i]);
                }
                adapterIncludes = new PreferenceAdapter(EditPreferencesActivity.this, includeList);
                gvIncludes.setAdapter(adapterIncludes);

                adapterIncludes.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (adapterIncludes.check) {
                            apiService.getIncludes(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
                                @Override
                                public void success(String[] strings, Response response) {
                                    String[] newList = new String[strings.length - 1];
                                    ArrayList<String> temp = new ArrayList<String>();
                                    int num = 0;
                                    for (int i = 0; i < strings.length; i++) {
                                        if (i != adapterIncludes.changedId) {
                                            temp.add(strings[i]);
                                        }
                                    }

                                    for (int i = 0; i < temp.size(); i++) {
                                        newList[i] = temp.get(i);
                                    }

                                    apiService.updatedIncludes(eatalyzeApplication.getUser().getId(), newList, new Callback<ResponseBody>() {
                                        @Override
                                        public void success(ResponseBody responseBody, Response response) {
                                            Log.d("Include Update Info", "SUC");

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }

                                    });
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("ZOO", "ZOO");
                                    Log.d("Getting include list", error.toString());
                                }
                            });


                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ZOO", "ZOO");
                Log.d("Getting include list", error.toString());
            }
        });

        // EXCLUDES
        apiService.getExclude(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                for (int i = 0; i < strings.length; i++) {
                    excludeList.add(strings[i]);
                }
                adapterExcludes = new PreferenceAdapter(EditPreferencesActivity.this, excludeList);
                gvExcludes.setAdapter(adapterExcludes);

                adapterExcludes.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (adapterExcludes.check) {
                            apiService.getExclude(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
                                @Override
                                public void success(String[] strings, Response response) {
                                    String[] newList = new String[strings.length - 1];
                                    ArrayList<String> temp = new ArrayList<String>();
                                    int num = 0;
                                    for (int i = 0; i < strings.length; i++) {
                                        if (i != adapterExcludes.changedId) {
                                            temp.add(strings[i]);
                                        }
                                    }

                                    for (int i = 0; i < temp.size(); i++) {
                                        newList[i] = temp.get(i);
                                    }

                                    apiService.updatedExcludes(eatalyzeApplication.getUser().getId(), newList, new Callback<ResponseBody>() {
                                        @Override
                                        public void success(ResponseBody responseBody, Response response) {
                                            Log.d("Include Update Info", "SUC");

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }

                                    });
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("ZOO", "ZOO");
                                    Log.d("Getting include list", error.toString());
                                }
                            });


                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ZOO", "ZOO");
                Log.d("Getting include list", error.toString());
            }
        });
    }

    @OnClick(R.id.iv_add_include_icon)
    public void addIncludeClicked() {
        adapterIncludes.check = false;
        final String newInclude = etIncludeTag.getText().toString();
        includeList.add(newInclude);
        etIncludeTag.setText("");
        apiService.getIncludes(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                String[] newList = new String[strings.length + 1];
                for (int i = 0; i < strings.length; i++) {
                    newList[i] = strings[i];
                }
                newList[strings.length] = newInclude;

                apiService.updatedIncludes(eatalyzeApplication.getUser().getId(), newList, new Callback<ResponseBody>() {
                    @Override
                    public void success(ResponseBody responseBody, Response response) {
                        Log.d("Include Update Info", "SUC");

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ZOO", "ZOO");
                Log.d("Getting include list", error.toString());
            }
        });
        adapterIncludes.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_add_exclude_icon)
    public void addExcludeClicked() {
        adapterExcludes.check = false;
        final String newExclude = etExcludeTag.getText().toString();
        excludeList.add(newExclude);
        etExcludeTag.setText("");
        apiService.getExclude(eatalyzeApplication.getUser().getId(), new Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                String[] newList = new String[strings.length + 1];
                for (int i = 0; i < strings.length; i++) {
                    newList[i] = strings[i];
                }
                newList[strings.length] = newExclude;

                apiService.updatedExcludes(eatalyzeApplication.getUser().getId(), newList, new Callback<ResponseBody>() {
                    @Override
                    public void success(ResponseBody responseBody, Response response) {
                        Log.d("Include Update Info", "SUC");

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ZOO", "ZOO");
                Log.d("Getting include list", error.toString());
            }
        });
        adapterExcludes.notifyDataSetChanged();
    }
}
