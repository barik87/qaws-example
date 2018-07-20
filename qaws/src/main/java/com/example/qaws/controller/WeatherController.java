package com.example.qaws.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.qaws.entities.weather.WeatherInfo;
import com.example.qaws.entities.weather.WeatherResponse;
import com.example.qaws.entities.weather.WeatherResponsePartMain;
import com.example.qaws.entities.weather.WeatherResponsePartWeather;
import com.example.qaws.storage.CityWeather;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    // For SUT
    @RequestMapping(value = "/data/2.5/weather", method = RequestMethod.GET)
    public ResponseEntity<WeatherResponse> getWeatherForCity(@RequestParam("q") String city) {
        Optional<WeatherInfo> cityWeather = CityWeather.get(city);
        WeatherResponse response = new WeatherResponse();
        if (cityWeather.isPresent()) {
            response.setCod(200);

            WeatherResponsePartWeather weatherPart = new WeatherResponsePartWeather();
            weatherPart.setDescription(cityWeather.get().getDescription());

            WeatherResponsePartMain weatherResponsePartMain = new WeatherResponsePartMain();
            weatherResponsePartMain.setTemp(cityWeather.get().getTemp());
            response.setMain(weatherResponsePartMain);

            response.setWeather(Collections.singletonList(weatherPart));
        } else {
            response.setCod(400);
            response.setMessage("city not found");
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ResponseEntity<String> putWeatherForCity(@RequestParam("city") String city,
            @RequestBody WeatherInfo weatherInfo) {
        CityWeather.put(city, weatherInfo);
        return ResponseEntity.ok("OK");
    }
}
