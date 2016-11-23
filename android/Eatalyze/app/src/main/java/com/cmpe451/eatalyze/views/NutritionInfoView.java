package com.cmpe451.eatalyze.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.fragments.NutrientsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Behiye on 11/20/2016.
 */

public class NutritionInfoView extends FrameLayout {
    @Bind(R.id.tv_info)
    TextView tvInfo;
    @Bind(R.id.tv_amount)
    TextView tvAmount;

    public NutritionInfoView(Context context, String info, String amount) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_nutrition_info, this);

        ButterKnife.bind(this);

        tvInfo.setText(info);
        tvAmount.setText(amount);
    }
}
