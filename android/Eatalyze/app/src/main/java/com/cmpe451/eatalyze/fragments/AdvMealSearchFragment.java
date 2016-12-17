package com.cmpe451.eatalyze.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmpe451.eatalyze.EatalyzeApplication;
import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.AdvancedSearchActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ekrem on 10/12/2016.
 */

public class AdvMealSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    @Bind(R.id.sp_diet)
    Spinner spDiet;
    @Bind(R.id.tv_calorie_range)
    TextView tvCalorieRange;
    @Bind(R.id.et_cal_min)
    EditText etCalMin;
    @Bind(R.id.et_cal_max)
    EditText etCalMax;
    @Bind(R.id.cv_calorie_range)
    CardView cvCalorieRange;
    @Bind(R.id.tv_cho_range)
    TextView tvChoRange;
    @Bind(R.id.et_cho_min)
    EditText etChoMin;
    @Bind(R.id.et_cho_max)
    EditText etChoMax;
    @Bind(R.id.cv_cho_range)
    CardView cvChoRange;
    @Bind(R.id.tv_fat_range)
    TextView tvFatRange;
    @Bind(R.id.et_fat_min)
    EditText etFatMin;
    @Bind(R.id.et_fat_max)
    EditText etFatMax;
    @Bind(R.id.cv_fat_range)
    CardView cvFatRange;
    @Bind(R.id.tv_protein_range)
    TextView tvProteinRange;
    @Bind(R.id.et_protein_min)
    EditText etProteinMin;
    @Bind(R.id.et_protein_max)
    EditText etProteinMax;
    @Bind(R.id.cv_protein_range)
    CardView cvProteinRange;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adv_meal_search, container, false);
        ButterKnife.bind(this, view);
        Spinner spinner = (Spinner) spDiet;
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getApplicationContext(),
                R.array.diet_types_array,
                R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
