package com.example.sut.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sut.entities.WeatherCityInfo;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weather.url}")
    private String weatherUrl;

    @Value("${weather.app.id}")
    private String weatherAppId;

    @RequestMapping(value = "/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherCityInfo> getWeatherForCity(@PathVariable("city") String city) {
        RestAssured.baseURI = weatherUrl;
        Response response = RestAssured.get("/data/2.5/weather?q={city}&appid={appId}", city, weatherAppId);
        WeatherCityInfo weatherCityInfo = new WeatherCityInfo();
        try {
            int code = response.jsonPath().getInt("cod");
            if (code == 200) {
                weatherCityInfo.setDescription(response.jsonPath().getString("weather[0].description"));
                weatherCityInfo.setTemperature(response.jsonPath().getDouble("main.temp") - 273.15);
            } else {
                weatherCityInfo.setDescription(response.jsonPath().getString("message"));
            }
            return ResponseEntity.status(response.statusCode()).body(weatherCityInfo);
        } catch (Exception e) {
            e.printStackTrace();
            weatherCityInfo.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(weatherCityInfo);
        }
    }
}
