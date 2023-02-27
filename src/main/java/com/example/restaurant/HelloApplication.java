package com.example.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class HelloApplication extends Application {

    private Pane panelConnexion = new Pane();
    private TabPane panelServeur = new TabPane();
    private TabPane panelGestionnaire = new TabPane();

    public HelloApplication() {
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        ID.reload();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane inter = fxmlLoader.load();
        panelConnexion.getChildren().add(inter);

        FXMLLoader fxmlLoaderS = new FXMLLoader(HelloApplication.class.getResource("serveur.fxml"));
        panelServeur = fxmlLoaderS.load();

        FXMLLoader fxmlLoaderG = new FXMLLoader(HelloApplication.class.getResource("gestionnaire.fxml"));
        panelGestionnaire = fxmlLoaderG.load();

        Scene scene = new Scene(panelConnexion);

        for (Node nom : panelConnexion.getChildren()){
            System.out.println(nom.getId());
        }

        Button boutonCo = (Button) inter.getChildren().get(3);

        boutonCo.setOnAction(arg0 -> panelConnexion.getChildren().set(0, panelServeur));

        /*BoutonRetour.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                panelConnexion.setCenter(panel1);
            }

        });*/




        stage.setTitle("Page d'accueil");
        stage.setScene(scene);
        //stage.show();


        //partie de test
        try {
            ArrayList<String> plats;
            Serveur s = new Serveur();
            plats = s.consulterPlats();
            System.out.println(plats.get(0));

            //s.reserverTable(12,4,"2021-09-10","19:00:00");
            s.consulterTable("2021-09-10","19:00:00");
            //System.out.println(s.commanderPlat(107,3,3));

            Gestionnaire g = new Gestionnaire();
            //g.affecterServeur("Leo Jon",14);

            System.out.println(g.calculerMontantTotal(100));

            //ArrayList<Double> d = new ArrayList<>();
            HashMap<String,Double> d = new HashMap<>();
            d = g.chiffreAffaire("2021-09-09","2021-10-09");
            Map sortedMap = new TreeMap(d);
            Set set2 = sortedMap.entrySet();
            for (Object o : set2) {
                Map.Entry me2 = (Map.Entry) o;
                System.out.print(me2.getKey() + ": ");
                System.out.println(me2.getValue());
            }

            ArrayList<String> lServ;
            lServ = g.pasDeChiffreAff("2021-09-09","2021-10-09");

            for (String nom:
                 lServ) {
                System.out.println(nom);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}