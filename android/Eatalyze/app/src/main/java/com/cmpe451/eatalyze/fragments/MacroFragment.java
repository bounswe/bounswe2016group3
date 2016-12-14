package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.cmpe451.eatalyze.views.NutritionInfoView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Behiye on 11/20/2016.
 */

public class MacroFragment extends Fragment {

    NutritionalInfo nutritionalInfo = new NutritionalInfo();

    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.pc_macro_chart)
    PieChart pcMacroChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_macro, container, false);
        ButterKnife.bind(this, view);

        ((LogActivity) getActivity()).getApiService().getWeeklyNutritionalInfo(new Callback<NutritionalInfo>() {
            @Override
            public void success(NutritionalInfo nutritionalInfo, Response response) {
                //TODO add piechart for macros

                NutritionInfoView view1 = new NutritionInfoView(getContext(), "Protein", "" + nutritionalInfo.getProtein() + "" + " gr");
                llContent.addView(view1);
                NutritionInfoView view2 = new NutritionInfoView(getContext(), "Total Carbohydrate", "" + nutritionalInfo.getTotalCarbohydrate() + "" + " gr");
                llContent.addView(view2);
                NutritionInfoView view3 = new NutritionInfoView(getContext(), "Total Fat", "" + nutritionalInfo.getTotalFat() + "" + " gr");
                llContent.addView(view3);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        /*
        ArrayList<PieEntry> entries=new ArrayList<>();
        entries.add(new PieEntry(4f, 0));
        entries.add(new PieEntry(8f, 0));
        entries.add(new PieEntry(6f, 0));

        PieDataSet dataSet=new PieDataSet(entries, "Macros");

        ArrayList<String> labels=new ArrayList<>();
        labels.add("carbo");
        labels.add("protein");
        labels.add("fat");

        PieData data=new PieData(dataSet);
        pcMacroChart.setData(data);*/

        //TODO check if this usage is valid
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
