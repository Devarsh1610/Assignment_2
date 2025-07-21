package com.example.assignment2.controller;



import com.example.assignment2.models.Forecast;
import com.example.assignment2.models.ForecastWeather;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ForecastController {

    @FXML
    private HBox forecastContainer;

    @FXML
    private Label forecastCityLabel;
    String apiKey = "2bdf21477102c4453b84614acc07ffeb";
    String cityName;

    /**
     * setter to set the cityname from the previous stage
     * Also setting to the label because initilize method is called as soon as stage is created
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
        forecastCityLabel.setText("24-Hour Forecast for " + cityName);
    }

    /**
     * Calling the forecast api of openweather as soon as the stage is created with the previous searched
     * city. This api returns the list of data as it's the forecast, so we looping through the each dataEntry
     * and creating Dynamic VBOX for each data entry and adding to the HBOX added in the scene.
     */
    @FXML
    public void initialize() {
        try {
            String uri = "https://api.openweathermap.org/data/2.5/forecast?q=London" + "&units=metric&appid=" + apiKey;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
            HttpResponse<String> response= client.send(request,HttpResponse.BodyHandlers.ofString());
            String jsonBody = response.body();

            if(response.statusCode() == 200){
                Gson gson = new Gson();
                ForecastWeather forecastWeatherData = gson.fromJson(jsonBody, ForecastWeather.class);

                // Now you can access the data:
                String cityName = forecastWeatherData.getCity().getName();

                // This will get the local date of the very first forecast entry
                LocalDateTime firstEntryDateTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(forecastWeatherData.getList().get(0).getDt()), ZoneId.systemDefault()
                );
                List<Forecast> forecastList = forecastWeatherData.getList();

                int numberOfEntriesToShow = Math.min(8, forecastList.size());
                List<Forecast> entriesToShow = forecastList.subList(0, numberOfEntriesToShow);

                for(Forecast forecastEntry : entriesToShow){
                    VBox forecastEntryBox = createForecastItem(forecastEntry);
                    forecastContainer.getChildren().add(forecastEntryBox);
                }

            }else{
                System.err.println("API Error: Status Code " + response.statusCode() + ", Body: " + jsonBody);
            }


        }catch (Exception e){
            System.err.println("Exception found " + e.getMessage());
        }

    }

    /**
     *
     * @param forecastEntry
     * each entry is being passed and vbox is being created for the TIME, Weather icon, And temperature
     */
    private VBox createForecastItem(Forecast forecastEntry) {
        VBox forecastEntryBox = new VBox(20);
        forecastEntryBox.getStyleClass().add("forecast-item");
        forecastEntryBox.setAlignment(Pos.CENTER);

        // Time Label
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(forecastEntry.getDt()), ZoneId.systemDefault());
        DateTimeFormatter itemTimeFormatter = DateTimeFormatter.ofPattern("h a");
//        formatting the fetched time as hour a (example 6 PM)
        Label timeLabel = new Label(dateTime.format(itemTimeFormatter));
        timeLabel.getStyleClass().add("forecast-time-label");

        // Weather Icon
        ImageView iconView = new ImageView();
        if (forecastEntry.getWeather() != null && !forecastEntry.getWeather().isEmpty()) {
            String iconCode = forecastEntry.getWeather().get(0).getIcon();
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            iconView.setImage(new Image(iconUrl));
        }
        iconView.setFitHeight(40);
        iconView.setFitWidth(40);

        // Temperature Label
        Label tempLabel = new Label(String.format("%.0f\u00B0C", forecastEntry.getMain().getTemp()));
        tempLabel.getStyleClass().add("forecast-temp-label");
//        adding thelabels to the vbox created above.
        forecastEntryBox.getChildren().addAll(timeLabel,iconView, tempLabel);

        return  forecastEntryBox;
    }

}
