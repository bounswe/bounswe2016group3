package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Behiye on 11/7/2016.
 */

public class MealAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<Meal> mealList=new ArrayList<Meal>();
    String servername;

    public MealAdapter(Context context, ArrayList<Meal> mealList, String serverName) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mealList = mealList;
        this.servername=serverName;
    }

    @Override
    public int getCount() {
        if (mealList != null) {
            return mealList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mealList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mealList.indexOf(mealList.get(i));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_meal, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();

        Meal meal=mealList.get(position);
        //TODO remove this if statement
        if(meal.getPhotoUrl().equals(""))
            meal.setPhotoUrl("https://image.freepik.com/free-icon/fork-and-knife-in-cross_318-61306.jpg");
        Picasso.with(context).load(meal.getPhotoUrl()).into(holder.ivMealImage);
        holder.tvMealName.setText(meal.getName());
        if(servername.equals("weekly")){
            holder.tvServerName.setText("");
        }else{
            holder.tvServerName.setText("@"+servername);

        }
        holder.tvMealDescription.setText(meal.getDescription());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.iv_meal_image)
        ImageView ivMealImage;
        @Bind(R.id.tv_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_server_name)
        TextView tvServerName;
        @Bind(R.id.tv_meal_description)
        TextView tvMealDescription;
        @Bind(R.id.cv_meal_item)
        CardView cvMealItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
