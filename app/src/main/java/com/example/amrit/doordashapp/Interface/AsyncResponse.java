package com.example.amrit.doordashapp.Interface;

import com.example.amrit.doordashapp.Pojos.Restaurant;

import java.util.List;

/**
 * Created by amrit on 1/6/2017.
 */

public interface AsyncResponse {
    void processFinish(List<Restaurant> restaurants);
}
