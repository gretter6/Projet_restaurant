package com.example.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    public static Pane panelConnexion = new Pane();
    public static TabPane panelServeur = new TabPane();
    public static TabPane panelGestionnaire = new TabPane();

    public static boolean serv = false;

    public HelloApplication() {
    }

    public static Pane pageAccueil() throws IOException {
        if (panelConnexion.getChildren().size()>0) {
            panelConnexion.getChildren().remove(0);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane inter = fxmlLoader.load();
        panelConnexion.getChildren().add(inter);
        return inter;
    }

    public static void pageServ() throws IOException {
        FXMLLoader fxmlLoaderS = new FXMLLoader(HelloApplication.class.getResource("serveur.fxml"));
        panelServeur = fxmlLoaderS.load();
    }

    public static void pageGest() throws IOException {
        FXMLLoader fxmlLoaderG = new FXMLLoader(HelloApplication.class.getResource("gestionnaire.fxml"));
        panelGestionnaire = fxmlLoaderG.load();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        ID.reload();
        Pane inter = pageAccueil();

        pageServ();

        pageGest();

        Scene scene = new Scene(panelConnexion);

        stage.setTitle("Page d'accueil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}