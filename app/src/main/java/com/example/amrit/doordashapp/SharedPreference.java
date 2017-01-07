package com.example.amrit.doordashapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.amrit.doordashapp.Pojos.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amrit on 1/7/2017.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "DoorDash_App";
    public static final String FAVOURITES = "Restaurant_Favourites";

    public void saveFavorites(Context context, List<Restaurant> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVOURITES, jsonFavorites);
        editor.commit();
    }

    public void addFavorite(Context context, Restaurant restaurant) {
        if(restaurant.getFavourite()==1) {
            Toast.makeText(context, "\""+restaurant.getName()+"\"" + " already added to Favourites",Toast.LENGTH_SHORT).show();
            return;
        }
        List<Restaurant> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Restaurant>();
        favorites.add(restaurant);
        saveFavorites(context, favorites);
        restaurant.setFavourite(1);
        Toast.makeText(context, "\""+restaurant.getName()+"\"" + " added to Favourites",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Restaurant> getFavorites(Context context) {
        SharedPreferences settings;
        List<Restaurant> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVOURITES)) {
            String jsonFavorites = settings.getString(FAVOURITES, null);
            Gson gson = new Gson();
            Restaurant[] favoriteItems = gson.fromJson(jsonFavorites,
                    Restaurant[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Restaurant>(favorites);
        } else
            return null;

        return (ArrayList<Restaurant>) favorites;
    }
}
