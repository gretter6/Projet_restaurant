package com.example.restaurant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelloController {
    private Serveur s = new Serveur();
    @FXML
    private Label welcomeText;

    @FXML
    private TextField numtabreserver;

    @FXML
    private TextField nbperstabreserver;

    @FXML
    private DatePicker datereservertabl;

    @FXML
    private TextField heurereservertabl;

    @FXML
    private DatePicker datepickerconsultertabl;

    @FXML
    private TextField heureconsultertabl;

    @FXML
    private ListView affichagetabl;

    @FXML
    private TextField numresplat;

    @FXML
    private TextField numplatcommander;

    @FXML
    private TextField qtecommander;

    @FXML
    private ListView affichageplatsdispo;

//    @FXML
//    private TextField qtecommander;
//
//    @FXML
//    private TextField qtecommander;
//
//    @FXML
//    private TextField qtecommander;

    public HelloController() throws SQLException {
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

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
    public void onReserverTabl() throws SQLException {
        int numtab = Integer.parseInt(String.valueOf(numtabreserver));
        int nbpers = Integer.parseInt(String.valueOf(datereservertabl));
        String date = String.valueOf(datereservertabl);
        String heure = String.valueOf(heurereservertabl);
        this.s.reserverTable(numtab,nbpers,date,heure);
    }

    @FXML
    public void onConsulterTabl() throws SQLException {
        String date = String.valueOf(datepickerconsultertabl);
        String heure = String.valueOf(heureconsultertabl);
        this.s.consulterTable(date,heure);
    }

    @FXML
    public void onCommanderPlat() throws SQLException {
        int numres = Integer.parseInt(String.valueOf(numresplat));
        int numplat = Integer.parseInt(String.valueOf(numplatcommander));
        int qte = Integer.parseInt(String.valueOf(qtecommander));
        this.s.commanderPlat(numres,numplat,qte);
    }

    @FXML
    public void onConsulterPlat() throws SQLException {
        ArrayList<String> plats;
        plats = this.s.consulterPlats();
        ObservableList<String> obs = FXCollections.observableArrayList();
        obs.addAll(plats);

        this.affichageplatsdispo.setItems(obs);


    }



}