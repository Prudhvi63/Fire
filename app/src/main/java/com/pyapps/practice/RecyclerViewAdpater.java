package com.pyapps.practice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyapps.practice.Models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yallanki on 8/17/2017.
 */

public class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewViewHolder> {
    ArrayList<Post> posts = null;
    Context context;

    public RecyclerViewAdpater(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_details,parent,false);

        return new RecyclerViewViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
        Post post =posts.get(position);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.imageView);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.imageButton);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
