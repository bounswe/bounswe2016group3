package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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

    ArrayList<Meal> mealList;

    public MealAdapter(Context context, ArrayList<Meal> mealList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mealList = mealList;
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
        Picasso.with(context).load(meal.getImageURL()).into(holder.ivMealImage);
        holder.tvMealName.setText(meal.getName());
        //TODO server name will be taken
        // holder.tvServerName.setText();
        // holder.tvCalorieAmount.setText(meal.getTotalCalorie());
        //holder.tvNutrients.setText(meal.getAmounts().toString());
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.iv_meal_image)
        ImageView ivMealImage;
        @Bind(R.id.tv_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_server_name)
        TextView tvServerName;
        @Bind(R.id.tv_calorie_amount)
        TextView tvCalorieAmount;
        @Bind(R.id.tv_nutrients)
        TextView tvNutrients;
        //TODO rating bar will be added
        //@Bind(R.id.rb_meal_rating)
        //RatingBar rbMealRating;
        @Bind(R.id.cv_meal_item)
        CardView cvMealItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
