package com.weather.demo.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class WeatherApiClient {

    public String getWeatherData(double latitude, double longitude) {
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(reader);

            JSONObject currentWeather = (JSONObject) response.get("current");

            double temperature = (double) currentWeather.get("temperature_2m");
            long humidity = (long) currentWeather.get("relative_humidity_2m");
            double windSpeed = (double) currentWeather.get("wind_speed_10m");

            return String.format("Temperature: %.1f°C, Humidity: %d%%, Wind Speed: %.1f m/s",
                    temperature, humidity, windSpeed);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching weather data.";
        }
    }
}