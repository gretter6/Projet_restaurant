package com.example.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

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
    protected void onDeconnexion() throws IOException {
        //System.out.println("oui");
        HelloApplication.pageAccueil();
    }

    @FXML
    protected void onServeurCo() {
        System.out.println("oui");
        HelloApplication.panelConnexion.getChildren().set(0, HelloApplication.panelServeur);
        HelloApplication.serv = true;
    }

    @FXML
    protected void onGestCo() {
        System.out.println("oui");
        HelloApplication.panelConnexion.getChildren().set(0, HelloApplication.panelGestionnaire);
        HelloApplication.serv = false;
        System.out.println(((AnchorPane)(((TabPane)HelloApplication.panelConnexion.getChildren().get(0)).getTabs().get(0).getContent())).getChildren());

    }

    @FXML
    public void onReserverTabl(){

    }

}