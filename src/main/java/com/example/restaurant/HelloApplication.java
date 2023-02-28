package com.example.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
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


//        Button boutonServ = (Button) inter.getChildren().get(5);
//        Button boutonGest = (Button) inter.getChildren().get(6);
//
//        boutonServ.setOnAction(actionEvent -> {
//            panelConnexion.getChildren().set(0, panelServeur);
//            HelloApplication.serv = true;
//        }
//        );
//        boutonGest.setOnAction(actionEvent -> {
//                    panelConnexion.getChildren().set(0, panelGestionnaire);
//                    serv = false;
//                    System.out.println(((AnchorPane)(((TabPane)panelConnexion.getChildren().get(0)).getTabs().get(0).getContent())).getChildren());
//                }
//        );





        /*BoutonRetour.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                panelConnexion.setCenter(panel1);
            }

        });*/




        stage.setTitle("Page d'accueil");
        stage.setScene(scene);
        stage.show();


        //partie de test
        /*
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

         */
    }

    public static void main(String[] args) {
        launch();
    }
}