package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.activities.FoodServerProfilePageActivity;
import com.cmpe451.eatalyze.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Behiye on 11/7/2016.
 */

public class FoodServerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<User> foodServerList=new ArrayList<User>();

    public FoodServerAdapter(Context context, ArrayList<User> foodServerList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.foodServerList = foodServerList;
    }

    @Override
    public int getCount() {
        if (foodServerList != null) {
            return foodServerList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return foodServerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return foodServerList.indexOf(foodServerList.get(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_food_server, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();

        final User foodServer = foodServerList.get(i);
        Picasso.with(context).load(foodServer.getAvatarUrl()).into(holder.ivFoodServerImage);
        holder.tvFoodServerName.setText(foodServer.getFullName());

        //TODO get location for food server
        //holder.tvFoodServerLocation.setText();
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.iv_food_server_image)
        ImageView ivFoodServerImage;
        @Bind(R.id.tv_food_server_name)
        TextView tvFoodServerName;
        @Bind(R.id.tv_food_server_location)
        TextView tvFoodServerLocation;
        @Bind(R.id.cv_food_server_item)
        CardView cvFoodServerItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
