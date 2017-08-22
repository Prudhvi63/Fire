package com.pyapps.practice;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Yallanki on 8/17/2017.
 */

public class RecyclerViewViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    ImageButton imageButton;
    ImageButton threeDots;
    public RecyclerViewViewHolder(View itemView, final Context context) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        threeDots = (ImageButton) itemView.findViewById(R.id.threedots);
        threeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, threeDots);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.test_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                context,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }

        });
    }
}
