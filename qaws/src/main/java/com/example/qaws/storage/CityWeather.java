package com.example.qaws.storage;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.example.qaws.entities.weather.WeatherInfo;

public class CityWeather {
    private static ConcurrentMap<String, WeatherInfo> weatherRecords = new ConcurrentHashMap<>();

    public static void put(String city, WeatherInfo weather) {
        weatherRecords.put(city, weather);
    }

    public static Optional<WeatherInfo> get(String city) {
        return Optional.ofNullable(weatherRecords.get(city));
    }
}
