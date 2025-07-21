package com.example.assignment2.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    private long dt;
    private Main main;
    private List<Weather> weather;

//    serializer in order to match the type with json return type
    @SerializedName("dt_txt")
    private String dtTxt;

    // Getters
    public long getDt() { return dt; }

    public Main getMain() { return main; }

    public List<Weather> getWeather() { return weather; }

    public String getDtTxt() { return dtTxt; }

}
