module com.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.assignment2 to javafx.fxml;
    exports com.example.assignment2;

    exports com.example.assignment2.controller;
    opens com.example.assignment2.controller to javafx.fxml;

//    needto grant permission as gson will map the private fields so we needto explicity tell java so it allows gson to use provate fields of model classes
    opens com.example.assignment2.models to com.google.gson;
}