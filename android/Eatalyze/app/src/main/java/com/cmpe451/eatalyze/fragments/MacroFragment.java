package com.cmpe451.eatalyze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.LogActivity;
import com.cmpe451.eatalyze.models.NutritionalInfo;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.PieChartL;

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
    @Bind(R.id.pie_chart)
    PieChartL pieChart;

//    @Bind(R.id.ll_content)
//    LinearLayout llContent;
//    @Bind(R.id.pie_chart)
//    PieChartL pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_macro, container, false);
        ButterKnife.bind(this, view);

        List<ChartData> values = new ArrayList<>();

        float fat=((LogActivity)getActivity()).nutritionalInfo.getTotalFat();
        float protein=((LogActivity)getActivity()).nutritionalInfo.getProtein();
        float carbo=((LogActivity)getActivity()).nutritionalInfo.getTotalCarbohydrate();
        values.add(new ChartData("Protein", protein));
        values.add(new ChartData("Fat", fat));
        values.add(new ChartData("Carbohydrate ", carbo));
        pieChart.setData(values);


        //TODO check if this usage is valid
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
