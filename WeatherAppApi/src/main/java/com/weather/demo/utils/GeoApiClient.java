package com.weather.demo.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class GeoApiClient {

    public JSONObject getLocationData(String city) {
        city = city.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(reader);

            JSONArray locationData = (JSONArray) response.get("results");
            return (JSONObject) locationData.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}