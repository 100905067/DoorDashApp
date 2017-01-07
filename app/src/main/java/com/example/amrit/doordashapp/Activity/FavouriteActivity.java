package com.example.amrit.doordashapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amrit.doordashapp.Adapter.RestaurantAdapter;
import com.example.amrit.doordashapp.Interface.OnItemClick;
import com.example.amrit.doordashapp.Pojos.Restaurant;
import com.example.amrit.doordashapp.R;
import com.example.amrit.doordashapp.Model.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements OnItemClick {

    private RecyclerView rvRecyclerView;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> rList = new ArrayList<>();

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        back = (Button)findViewById(R.id.back);

        rvRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantAdapter = new RestaurantAdapter(rList, getApplicationContext());
        rvRecyclerView.setAdapter(restaurantAdapter);

        restaurantAdapter.onItemClick = this;

        populateFav();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void populateFav() {
        List<Restaurant> restaurants = new SharedPreference().getFavorites(getApplicationContext());
        if(restaurants==null || restaurants.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Favourite List is Empty",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }
        for(int i=0;i<restaurants.size();i++) {
            Restaurant restaurant = restaurants.get(i);
            rList.add(restaurant);
            restaurantAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClickListener(Restaurant restaurant) {
        return;
    }
}
