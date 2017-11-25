package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
//  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      COMPLETED (3) Within syncWeather, fetch new weather data
//      COMPLETED (4) If we have valid results, delete the old data and insert the new
public class SunshineSyncTask {
    synchronized public static void syncWeather(Context context) {

        try {
            URL url = NetworkUtils.getUrl(context);
            String weatherJsonData = NetworkUtils.getResponseFromHttpUrl(url);

            ContentValues[] contentValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, weatherJsonData);

            if(contentValues != null && contentValues.length > 0) {
                context.getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);

                context.getContentResolver().bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, contentValues);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}