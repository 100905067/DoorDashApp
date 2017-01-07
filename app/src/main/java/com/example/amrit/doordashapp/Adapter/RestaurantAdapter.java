package com.example.amrit.doordashapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amrit.doordashapp.Interface.OnItemClick;
import com.example.amrit.doordashapp.Pojos.Restaurant;
import com.example.amrit.doordashapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by amrita on 1/6/2017.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.CustomViewHolder> {
    private static final String LOG_TAG = RestaurantAdapter.class.getSimpleName() ;
    List<Restaurant> restaurantList;
    Context context;


    public OnItemClick onItemClick;

    public RestaurantAdapter(List<Restaurant> rList, Context c) {
        Log.e(LOG_TAG,"inside constructor");
        restaurantList = rList;
        context = c;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.rName.setText(restaurant.getName());
        holder.rDescription.setText(restaurant.getDescription());
        holder.rAddress.setText(restaurant.getAddress());

        Picasso.with(context).load(restaurant.getUrl()).into(holder.rImage);
    }

    @Override
    public int getItemCount() {
        Log.e(LOG_TAG,"Size ="+restaurantList.size());
        if(restaurantList.isEmpty())
            return 0;
        return restaurantList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView rName, rDescription, rAddress;
        ImageView rImage;

        public CustomViewHolder(View itemView) {
            super(itemView);
            rName = (TextView)itemView.findViewById(R.id.resName);
            rDescription = (TextView) itemView.findViewById(R.id.resDesc);
            rAddress = (TextView) itemView.findViewById(R.id.resAddress);
            rImage = (ImageView) itemView.findViewById(R.id.resImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        Restaurant clickedRestaurant = restaurantList.get(pos);
                        onItemClick.onItemClickListener(clickedRestaurant);
                    }
                }
            });
        }
    }
}
