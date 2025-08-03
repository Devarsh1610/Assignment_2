package com.example.assignment2.controller;

import com.example.assignment2.MainApplication;
import com.example.assignment2.models.CurrentWeather;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrentWeatherController {
    String apiKey = "2bdf21477102c4453b84614acc07ffeb";
    String cityName;

    @FXML
    private TextField cityInput;
    @FXML
    private Button getWeatherbtn;

    @FXML
    private ImageView weatherImageIcon;

    @FXML
    private Label cityNameLabel;

    @FXML
    private Label highTempLabel;

    @FXML
    private Label lowTempLabel;


    @FXML
    private Label temperatureUnitLabel;

    @FXML
    private Label weatherDescLabel;

    @FXML
    private Label feelsLikeLabel;
    @FXML
    private Label humidityLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Button forecastBtn;

    /**
     * This will get the input from the user and then call the
     * open weather api and get the json details and displaying to the FXML.
     * Also used another api of open weather to display the icon from the iconcodes that we fetched from the first API
     *
     * Used gson library to process the data and POJO classes.
     * Used Httpclient to call the API, then parsed the json response which is required for GSON
     */
    @FXML
    private void getWeatherfromOpenWeatherAPI(){
        try {
            cityName = cityInput.getText();
            String uri = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + apiKey;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
            HttpResponse<String> response= client.send(request,HttpResponse.BodyHandlers.ofString());
            String jsonBody = response.body();

            if(response.statusCode() == 200){
                Gson gson = new Gson();
                CurrentWeather weatherData = gson.fromJson(jsonBody, CurrentWeather.class);

               
                String cityName = weatherData.getName();
                double temperature = weatherData.getMain().getTemp();
                String description = weatherData.getWeather().get(0).getDescription();
                double feelsLike = weatherData.getMain().getFeelsLike();
                double highTemp = weatherData.getMain().getHightemp();
                double lowTemp = weatherData.getMain().getLowtemp();
                int humidity = weatherData.getMain().getHumidity();
                double windSpeed = weatherData.getWind().getSpeed();
                String iconCode = weatherData.getWeather().get(0).getIcon();
                String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

                
                Image weatherIcon = new Image(iconUrl);

                
                weatherImageIcon.setImage(weatherIcon);

                cityNameLabel.setText(cityName);
                temperatureUnitLabel.setText(String.format("%.1f\u00B0C", temperature));
                weatherDescLabel.setText(description);
                feelsLikeLabel.setText(String.format("%.1f\u00B0C", feelsLike));
                highTempLabel.setText(String.format("%.1f\u00B0", highTemp));
                lowTempLabel.setText(String.format("%.1f\u00B0", lowTemp));

                humidityLabel.setText(String.format("%d%%", humidity));
                windSpeedLabel.setText(String.valueOf(windSpeed));
            }else{
                System.err.println("API Error: Status Code " + response.statusCode() + ", Body: " + jsonBody);
            }


        }catch (Exception e){
            System.err.println("Exception found " + e.getMessage());
        }
    }

    /**
     * Gets the input city from the input box and loads the forecast-view.fxml,
     * which then displays the 24-hour forecast for the city the user searched for.
     * This method creates a new top-level window (Stage) with a new Scene.
     * An application icon and a dynamic title are also added to the new Stage.
     *
     * Exceptions are also being handles via try and catch and logged in console as well.
     */
    @FXML
    private void getWeatherForecast(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/forecast-view.fxml"));
            Parent forecastRootNode = fxmlLoader.load();
            ForecastController forecastController = fxmlLoader.getController();
            forecastController.setCityName(cityName);
            Scene scene = new Scene(forecastRootNode,600,600);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage forecastStage = new Stage();
            Image appIcon = new Image(MainApplication.class.getResourceAsStream("/images/weather-clock-icon.png"));
            forecastStage.getIcons().add(appIcon);
            forecastStage.setTitle("24-Hour Forecast for " + cityName);
            forecastStage.setScene(scene);
            forecastStage.show();
        }catch (Exception e){
            System.err.println("Exception found " + e.getMessage());
        }
    }

}

