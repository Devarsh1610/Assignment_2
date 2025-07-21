package com.example.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        this will load the first stage
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("current-weather-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Weather Application");
//       this is adding css to the current stage.
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        // Add the image to the stage's icon list
        Image appIcon = new Image(MainApplication.class.getResourceAsStream("/images/weather-icon.png"));
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}