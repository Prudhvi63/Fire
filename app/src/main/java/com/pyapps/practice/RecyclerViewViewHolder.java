package com.pyapps.practice;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Yallanki on 8/17/2017.
 */

public class RecyclerViewViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    ImageButton imageButton;
    public RecyclerViewViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
    }
}
