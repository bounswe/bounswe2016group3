package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ASUS on 25.10.2016.
 */

public class DietActivity extends BaseActivity {

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.cb_vegeterian)
    AppCompatCheckBox cbVegeterian;
    @Bind(R.id.tv_vegeterian)
    TextView tvVegeterian;
    @Bind(R.id.rl_vegeterian)
    LinearLayout rlVegeterian;
    @Bind(R.id.cb_vegan)
    AppCompatCheckBox cbVegan;
    @Bind(R.id.tv_vegan)
    TextView tvVegan;
    @Bind(R.id.rl_vegan)
    LinearLayout rlVegan;
    @Bind(R.id.cb_gluten)
    AppCompatCheckBox cbGluten;
    @Bind(R.id.tv_gluten)
    TextView tvGluten;
    @Bind(R.id.rl_gluten)
    LinearLayout rlGluten;
    @Bind(R.id.cb_celliac)
    AppCompatCheckBox cbCelliac;
    @Bind(R.id.tv_celliac)
    TextView tvCelliac;
    @Bind(R.id.rl_celliac)
    LinearLayout rlCelliac;
    @Bind(R.id.cb_lactose)
    AppCompatCheckBox cbLactose;
    @Bind(R.id.tv_lactose)
    TextView tvLactose;
    @Bind(R.id.rl_lactose)
    LinearLayout rlLactose;
    @Bind(R.id.cb_paleo)
    AppCompatCheckBox cbPaleo;
    @Bind(R.id.tv_paleo)
    TextView tvPaleo;
    @Bind(R.id.rl_paleo)
    LinearLayout rlPaleo;

    ArrayList<AppCompatCheckBox> checkBoxList = new ArrayList<AppCompatCheckBox>();
    int checkedIndex = 0;
    @Bind(R.id.btn_save)
    Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_diet;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkBoxList.add(cbVegeterian);
        checkBoxList.add(cbVegan);
        checkBoxList.add(cbGluten);
        checkBoxList.add(cbCelliac);
        checkBoxList.add(cbLactose);
        checkBoxList.add(cbPaleo);

        int dietType = eatalyzeApplication.getUser().getDietType();
        if (dietType != 6){
            checkBoxList.get(dietType).setChecked(true);
            disable(dietType);
        }
    }


    @OnClick({R.id.cb_vegeterian, R.id.cb_vegan, R.id.cb_gluten, R.id.cb_celliac, R.id.cb_lactose, R.id.cb_paleo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_vegeterian:
                if (cbVegeterian.isChecked()) {
                    disable(0);
                    checkedIndex = 0;
                } else {
                    enable(0);
                    checkedIndex = 6;
                }
                break;
            case R.id.cb_vegan:
                if (cbVegan.isChecked()) {
                    disable(1);
                    checkedIndex = 1;
                } else {
                    enable(1);
                    checkedIndex = 6;
                }
                break;
            case R.id.cb_gluten:
                if (cbGluten.isChecked()) {
                    disable(2);
                    checkedIndex = 2;
                } else {
                    enable(2);
                    checkedIndex = 6;
                }

                break;
            case R.id.cb_celliac:
                if (cbCelliac.isChecked()) {
                    disable(3);
                    checkedIndex = 3;
                } else {
                    enable(3);
                    checkedIndex = 6;
                }
                break;
            case R.id.cb_lactose:
                if (cbLactose.isChecked()) {
                    disable(4);
                    checkedIndex = 4;
                } else {
                    enable(4);
                    checkedIndex = 6;
                }
                break;
            case R.id.cb_paleo:
                if (cbPaleo.isChecked()) {
                    disable(5);
                    checkedIndex = 5;
                } else {
                    enable(5);
                    checkedIndex = 6;
                }
                break;
        }
    }

    public void disable(int index) {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (i != index) checkBoxList.get(i).setEnabled(false);
        }
    }

    public void enable(int index) {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (i != index) checkBoxList.get(i).setEnabled(true);
        }
    }

    public void userUpdate(int index) {
        User user = eatalyzeApplication.getUser();
        user.setDietType(index);
        eatalyzeApplication.setUser(user);
        apiService.updateUser(eatalyzeApplication.getUser(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("User update", "SUC");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("User update FAIL", error.toString());
            }
        });
    }

    @OnClick(R.id.btn_save)
    public void saveClicked() {
        userUpdate(checkedIndex);
        Toast.makeText(DietActivity.this,"Diet type saved succesfully", Toast.LENGTH_SHORT).show();
    }
}
