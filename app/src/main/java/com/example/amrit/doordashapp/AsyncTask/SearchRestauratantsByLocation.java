package com.example.amrit.doordashapp.AsyncTask;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.amrit.doordashapp.Interface.AsyncResponse;
import com.example.amrit.doordashapp.Pojos.Menu;
import com.example.amrit.doordashapp.Pojos.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amrita on 1/5/2017.
 */

public class SearchRestauratantsByLocation extends AsyncTask<Void,Void, List<Restaurant>> {
    private static final String LOG_TAG = SearchRestauratantsByLocation.class.getSimpleName();
    public AsyncResponse mresponse ;
    @Override
    protected List<Restaurant> doInBackground(Void... params) {
        Log.e(LOG_TAG, "Inside Async Task");
        String forecast_base_url = "https://api.doordash.com/v2/restaurant/";
        String LAT_PARAM = "lat";
        String LON_PARAM = "lng";

        String lat="37.595230";
        String lon="-122.043969";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String restaurantsJsonStr = null;

        try {
            Uri uri = Uri.parse(forecast_base_url).buildUpon()
                    .appendQueryParameter(LAT_PARAM,lat)
                    .appendQueryParameter(LON_PARAM,lon)
                    .build();

            URL url =  new URL(uri.toString());

            Log.e(LOG_TAG, "Url :"+url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int code = urlConnection.getResponseCode();
            if(code !=200)
                return null;

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            restaurantsJsonStr= buffer.toString();

            JSONArray jsonArray = new JSONArray(restaurantsJsonStr);
            Log.e(LOG_TAG, jsonArray.toString());

            List<Restaurant> restaurants = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject restaurant = jsonArray.getJSONObject(i);
                int id = restaurant.getInt("id");
                String name = restaurant.getString("name");
                String description = restaurant.getString("description");
                String resUrl = restaurant.getString("cover_img_url");
                String address = restaurant.getJSONObject("address").getString("printable_address");
                List<Menu> menuList = new ArrayList<>();
                JSONArray menuArr = restaurant.getJSONArray("menus");
                for(int j=0;j<menuArr.length();j++)
                {
                    JSONObject menu = menuArr.getJSONObject(j);
                    int menuId = menu.getInt("id");
                    String menuName = menu.getString("name");
                    menuList.add(new Menu(menuId, menuName));
                }
                restaurants.add(new Restaurant(id, name, description, resUrl, address, menuList));
            }

            return restaurants;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        if(restaurants==null)
            return;
        if(mresponse!=null)
            mresponse.processFinish(restaurants);
    }
}
