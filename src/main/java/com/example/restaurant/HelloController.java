package com.example.restaurant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class HelloController {

    //Attributs pour les serveurs (cela servira aussi pour les gestionnaires)
    private Serveur s = new Serveur();
    private Gestionnaire g = new Gestionnaire();

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

    //Attributs spécifiques aux gestionnaires

    @FXML
    private TextField libelleplatcrea;

    @FXML
    private TextField typeplatcrea;

    @FXML
    private TextField prixunitplatcrea;

    @FXML
    private TextField qteplatcrea;

    @FXML
    private TextField libelleplatAmodif;

    @FXML
    private TextField libelleplatmodif;

    @FXML
    private TextField typeplatmodif;

    @FXML
    private TextField prixunitplatmodif;

    @FXML
    private TextField qteplatmodif;

    @FXML
    private TextField nomservaffecter;

    @FXML
    private TextField numtablaffecter;

    @FXML
    private TextField emailcreaserv;

    @FXML
    private TextField mdpcreaserv;

    @FXML
    private TextField nomcreaserv;

    @FXML
    private TextField gradecreaserv;

    @FXML
    private TextField nomAmodifserv;

    @FXML
    private TextField emailmodifserv;

    @FXML
    private TextField mdpmodifserv;

    @FXML
    private TextField nommodifserv;

    @FXML
    private TextField grademodifserv;

    @FXML
    private TextField numrescalculeraddition;

    @FXML
    private TextArea montantcalculaddition;

    @FXML
    private TextField montantaffaddition;

    @FXML
    private TextField numresaffaddition;

    @FXML
    private TableView tablechaff;

    @FXML
    private DatePicker datedebchaff;

    @FXML
    private DatePicker datefinchaff;

    @FXML
    private ListView affichagepaschaff;

    public HelloController() throws SQLException {
    }

    //Méthode pour les actions d'un serveur (cela servira pour les gestionnaires)
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
        HelloApplication.panelConnexion.getChildren().set(0, HelloApplication.panelServeur);
        HelloApplication.serv = true;
    }

    @FXML
    protected void onGestCo() {
        HelloApplication.panelConnexion.getChildren().set(0, HelloApplication.panelGestionnaire);
        HelloApplication.serv = false;
    }

    @FXML
    public void onReserverTabl() throws SQLException {
        int numtab = Integer.parseInt(numtabreserver.getText());
        int nbpers = Integer.parseInt(nbperstabreserver.getText());
        String date = String.valueOf(datereservertabl.getValue());
        String heure = heurereservertabl.getText();

        this.s.reserverTable(numtab,nbpers,date,heure);
    }

    @FXML
    public void onConsulterTabl() throws SQLException {
        String date = String.valueOf(datepickerconsultertabl.getValue());
        String heure = heureconsultertabl.getText();

        ArrayList<Integer> tables;
        tables = this.s.consulterTable(date,heure);

        ObservableList<Integer> obs = FXCollections.observableArrayList();
        obs.addAll(tables);

        this.affichagetabl.setItems(obs);
    }

    @FXML
    public void onCommanderPlat() throws SQLException {
        int numres = Integer.parseInt(numresplat.getText());
        int numplat = Integer.parseInt(numplatcommander.getText());
        int qte = Integer.parseInt(qtecommander.getText());

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

    //Méthodes spécifiques aux gestionnaires

    @FXML
    public void onCreerPlat() throws SQLException {
        String libelle = libelleplatcrea.getText();
        String type = typeplatcrea.getText();
        double prixunit = Double.parseDouble(prixunitplatcrea.getText());
        int qteservie = Integer.parseInt(qteplatcrea.getText());

        this.g.creerPlat(libelle,type,prixunit,qteservie);
    }

    @FXML
    public void onModifierPlat() throws SQLException {
        String platAModif = libelleplatAmodif.getText();
        String libelle = libelleplatmodif.getText();
        String type = typeplatmodif.getText();
        double prixunit = 0;
        int qteservie = 0;
        System.out.println(prixunitplatmodif.getText());
        if (!prixunitplatmodif.getText().equals("")){
            prixunit = Double.parseDouble(prixunitplatmodif.getText());
        }
        if (!qteplatmodif.getText().equals("")) {
            qteservie = Integer.parseInt(qteplatmodif.getText());
        }

        this.g.modifierPlat(platAModif,libelle,type,prixunit,qteservie);
    }

    @FXML
    public void onAffecterServ() throws SQLException {
        String preNom = nomservaffecter.getText();
        int numtab = Integer.parseInt(numtablaffecter.getText());

        this.g.affecterServeur(preNom,numtab);
    }

    @FXML
    public void onCreerServ() throws SQLException {
        String email = emailcreaserv.getText();
        String mdp = mdpcreaserv.getText();
        String nom = nomcreaserv.getText();
        String grade = gradecreaserv.getText();

        this.g.creerServeur(email,mdp,nom,grade);
    }

    @FXML
    public void onModifierServ() throws SQLException {
        String nomAModif = nomAmodifserv.getText();
        String email = emailmodifserv.getText();
        String mdp = mdpmodifserv.getText();
        String nom = nommodifserv.getText();
        String grade = grademodifserv.getText();

        this.g.modifierServeur(nomAModif,email,mdp,nom,grade);
    }

    @FXML
    public void onCalculerAddition() throws SQLException {
        int numres = 0;
        double montant = 0;
        if (!numrescalculeraddition.getText().equals("")) {
            numres = Integer.parseInt(numrescalculeraddition.getText());
        }

        if (numres!=0) {
            montant = this.g.calculerMontantTotal(numres);
            this.montantcalculaddition.setText(String.valueOf(montant));
        }

    }

    @FXML
    public void onAffecterAddition()
    {

    }

    @FXML
    public void onAfficherChAff() throws SQLException {
        String datedeb = String.valueOf(datedebchaff.getValue());
        String datefin = String.valueOf(datefinchaff.getValue());


        HashMap<String,Double> d;
        d = this.g.chiffreAffaire(datedeb, datefin);
        Map sortedMap = new TreeMap(d);
        Set set2 = sortedMap.entrySet();
        for (Object o : set2) {
            Map.Entry me2 = (Map.Entry) o;
            System.out.print(me2.getKey() + ": ");
            System.out.println(me2.getValue());
        }

    }

    @FXML
    public void onAfficherPasChAff()
    {

    }



}