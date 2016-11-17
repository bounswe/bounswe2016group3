package com.cmpe451.eatalyze.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.eatalyze.R;
import com.cmpe451.eatalyze.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */

public class FollowersAdapter extends ArrayAdapter<User>{

    Context context;
    List<User> userList;

    public FollowersAdapter(Context context, List<User> userList) {
        super(context, R.layout.customrows_forfollowersfollowing, userList);

        this.context = context;
        this.userList = userList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.customrows_forfollowersfollowing, null, true);


        TextView fullname = (TextView) rowView.findViewById(R.id.fullname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);

        fullname.setText(userList.get(position).getFullName());
        Log.d("Fullname of users:", userList.get(position).getFullName());
        Picasso.with(FollowersAdapter.this.getContext()).load(userList.get(position).getAvatarUrl()).into(imageView);

        return rowView;

    };
}
