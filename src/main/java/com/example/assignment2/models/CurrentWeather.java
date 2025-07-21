package com.example.assignment2.models;

import java.util.List;

public class CurrentWeather {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String name;

    // Getters for the fields we need for first fxml
    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "weather=" + weather +
                ", main=" + main +
                ", wind=" + wind +
                ", name='" + name + '\'' +
                '}';
    }
}
