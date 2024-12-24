package com.weather.demo.controller;

import com.weather.demo.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private WeatherService weatherService;

    private String city = "Berlin";

    @BeforeEach
    public void setUp() {
        // Set up mock behavior for WeatherService
        when(weatherService.getWeatherData(city)).thenReturn("Temperature: 6.8°C, Humidity: 83%, Wind Speed: 10.8 m/s");
    }

    @Test
    public void testGetWeather() {
        // Perform GET request on /weather/{city} endpoint
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/weather/" + city, String.class);

        // Assert that the response status is OK (200)
        assertEquals(200, response.getStatusCodeValue());

        // Assert that the weather data matches the mock data
        assertEquals("Temperature: 6.8°C, Humidity: 83%, Wind Speed: 10.8 m/s", response.getBody());
    }
}