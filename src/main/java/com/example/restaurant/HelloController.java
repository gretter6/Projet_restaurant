package com.example.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onConnexion(){
        System.out.println("oui");
    }

    @FXML
    protected void onDeconnexion(){
        System.out.println("oui");
    }
}