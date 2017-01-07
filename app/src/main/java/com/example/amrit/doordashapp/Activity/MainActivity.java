package com.example.amrit.doordashapp.Activity;

import android.support.test.espresso.core.deps.guava.primitives.Booleans;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.amrit.doordashapp.Adapter.RestaurantAdapter;
import com.example.amrit.doordashapp.AsyncTask.SearchRestauratantsByLocation;
import com.example.amrit.doordashapp.Interface.AsyncResponse;
import com.example.amrit.doordashapp.Pojos.Restaurant;
import com.example.amrit.doordashapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Button searchRestaurants;

    private RecyclerView rvRecyclerView;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> rList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchRestaurants = (Button) findViewById(R.id.searchRestaurants);

        rvRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantAdapter = new RestaurantAdapter(rList, getApplicationContext());
        rvRecyclerView.setAdapter(restaurantAdapter);


        searchRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchRestauratantsByLocation searchRestauratantsByLocation = new SearchRestauratantsByLocation();
                searchRestauratantsByLocation.mresponse = new AsyncResponse() {
                    @Override
                    public void processFinish(List<Restaurant> restaurants) {
                        Log.e(LOG_TAG, restaurants.toString());
                        for(int i=0;i<restaurants.size();i++) {
                            Restaurant restaurant = restaurants.get(i);
                            rList.add(restaurant);
                            restaurantAdapter.notifyDataSetChanged();
                        }
                    }
                };
                searchRestauratantsByLocation.execute();
            }
        });
    }
}
