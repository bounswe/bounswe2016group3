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
import com.cmpe451.eatalyze.models.Comment;
import com.cmpe451.eatalyze.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ekrem on 21/11/2016.
 */

public class CommentAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Comment> comments = new ArrayList<>();

    public CommentAdapter(Context context, ArrayList<User> userList, ArrayList<Comment> comments) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.userList = userList;
        this.comments = comments;
    }


    @Override
    public int getCount() {
        if (!comments.isEmpty())
            return comments.size();
        else
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (!comments.isEmpty())
            return comments.get(position);
        else
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

            convertView = layoutInflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        Comment comment = comments.get(position);
        User user = userList.get(position);
        String username = user.getFullName();
        String userImageUrl = user.getAvatarUrl();

        if (userImageUrl.length() == 0){
            userImageUrl = "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png";
        }

        Picasso.with(context).load(userImageUrl).into(viewHolder.ivUserImage);

        viewHolder.tvContent.setText(comment.getContent());
        viewHolder.tvUsername.setText(username);



        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.iv_user_image)
        ImageView ivUserImage;
        @Bind(R.id.tv_username)
        TextView tvUsername;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.cv_comment_item)
        CardView cvCommentItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
