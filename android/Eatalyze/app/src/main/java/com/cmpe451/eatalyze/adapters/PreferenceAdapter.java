package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Behiye on 12/19/2016.
 */

public class PreferenceAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<String> preferenceList;

    public ViewHolder holder;
    public int changedId;
    public boolean check = false;
    String type;

    public PreferenceAdapter(Context context, ArrayList<String> preferenceList, String type) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.preferenceList = preferenceList;
        this.type = type;
    }

    @Override
    public int getCount() {
        return preferenceList.size();
    }

    @Override
    public Object getItem(int i) {
        return preferenceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return preferenceList.indexOf(preferenceList.get(i));
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        check = false;
        holder = null;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_include, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();

        holder.tvTag.setText(preferenceList.get(i));

        if (type.equals("excludes")) {
            holder.btnUntag.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.tvTag.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.cvTag.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.llInclude.setBackgroundColor(context.getResources().getColor(R.color.red));

        }

        holder.btnUntag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                Log.d("CLICKED X", "YESS");
                preferenceList.remove(i);
                changedId = i;
                notifyDataSetChanged();
            }
        });

        return view;
    }



    static class ViewHolder {
        @Bind(R.id.tv_tag)
        TextView tvTag;
        @Bind(R.id.btn_untag)
        Button btnUntag;
        @Bind(R.id.cv_tag)
        CardView cvTag;
        @Bind(R.id.ll_include)
        LinearLayout llInclude;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        Button getBtn() {
            return btnUntag;
        }

    }
}
