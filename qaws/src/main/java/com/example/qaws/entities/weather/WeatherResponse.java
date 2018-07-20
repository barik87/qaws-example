package com.example.qaws.entities.weather;

import java.util.List;

public class WeatherResponse {
    private List<WeatherResponsePartWeather> weather;
    private WeatherResponsePartMain main;
    private int cod;
    private String message;

    public List<WeatherResponsePartWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherResponsePartWeather> weather) {
        this.weather = weather;
    }

    public WeatherResponsePartMain getMain() {
        return main;
    }

    public void setMain(WeatherResponsePartMain main) {
        this.main = main;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
