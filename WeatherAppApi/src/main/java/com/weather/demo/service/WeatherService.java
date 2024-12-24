package com.weather.demo.service;

import com.weather.demo.utils.GeoApiClient;
import com.weather.demo.utils.WeatherApiClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private GeoApiClient geoApiClient;

    @Autowired
    private WeatherApiClient weatherApiClient;

    public String getWeatherData(String city) {
        JSONObject cityLocationData = geoApiClient.getLocationData(city);

        if (cityLocationData == null || !isValidCityData(cityLocationData)) {
            return "Invalid city data. Please try again.";
        }

        double latitude = (double) cityLocationData.get("latitude");
        double longitude = (double) cityLocationData.get("longitude");

        return weatherApiClient.getWeatherData(latitude, longitude);
    }

    private boolean isValidCityData(JSONObject cityLocationData) {
        if (cityLocationData == null) {
            return false;
        }
        return cityLocationData.containsKey("latitude") && cityLocationData.containsKey("longitude");
    }
}