package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Tag;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ekrem on 19/12/2016.
 */

public class MealTagsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Tag> tags = new ArrayList<>();

    public MealTagsAdapter(Context context, ArrayList<Tag> tags) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = layoutInflater.inflate(R.layout.item_tag, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else viewHolder = (ViewHolder) convertView.getTag();

        Tag tag = tags.get(position);
        String displayName = tag.getDisplayName();

        viewHolder.tvTag.setText(displayName);


        viewHolder.btnUntag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_tag)
        TextView tvTag;
        @Bind(R.id.btn_untag)
        Button btnUntag;
        @Bind(R.id.cv_tag)
        CardView cvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
