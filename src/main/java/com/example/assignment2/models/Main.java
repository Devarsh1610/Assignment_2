package com.example.assignment2.models;

import com.google.gson.annotations.SerializedName;

public class Main {
    private double temp;

//    the json name for this field is feels_like so need to match
    @SerializedName("feels_like")
    private double feelsLike;
    private int humidity;

    @SerializedName("temp_max")
    private double hightemp;

    @SerializedName("temp_min")
    private double lowtemp;

    // Getters
    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getHightemp() {
        return hightemp;
    }

    public double getLowtemp() {
        return lowtemp;
    }
}
