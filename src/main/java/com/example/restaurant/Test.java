package com.example.restaurant;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application{

    private BorderPane PanelAnnuaire = new BorderPane();
    private BorderPane panel1 = new BorderPane();
    private BorderPane panel2 = new BorderPane();

    private Label message1 = new Label("Panel 1");
    private Button BoutonAller = new Button("Aller");

    private Button BoutonRetour = new Button("Retour");
    private Label message2 = new Label("Panel 2");

    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub

        /*Panel 1*/

        BoutonAller.setPrefSize(295, 50);
        panel1.setCenter(BoutonAller);
        panel1.setTop(message1);
        message1.setTextFill(Color.RED);

        /*Panel 2*/

        BoutonRetour.setPrefSize(295, 50);
        panel2.setCenter(BoutonRetour);
        panel2.setTop(message2);
        message2.setTextFill(Color.GREEN);

        /*Actions Boutons*/

        BoutonAller.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                PanelAnnuaire.setCenter(panel2);
            }
        });

        BoutonRetour.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                PanelAnnuaire.setCenter(panel1);
            }

        });

        PanelAnnuaire.setCenter(panel1);
        Scene scene = new Scene(PanelAnnuaire, 1400, 700);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}