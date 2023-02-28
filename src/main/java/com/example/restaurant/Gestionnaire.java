package com.example.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Gestionnaire extends Serveur{
    private final Connection co = DBConnection.getInstance().getConnect();

    public Gestionnaire() throws SQLException {
    }

    public void creerServeur(String email,String mdp,String nom, String grade) throws SQLException {
        co.setAutoCommit(false);
        if ((grade.equals("serveur") || grade.equals("gestionnaire")) && email.contains("@")) {
            int numserv = ID.numserv + 1;

            co.createStatement().executeUpdate("" +
                    "INSERT INTO serveur VALUES (" + numserv + ",\"" + email + "\",\"" + mdp + "\",\"" + nom + "\",\"" + grade + "\")"
            );

            ID.reload();
            co.commit();
        }
    }

    public void modifierServeur(String nomAModif, String email,String mdp,String nom, String grade) throws SQLException {
        co.setAutoCommit(false);

        ResultSet resServ = co.createStatement().executeQuery(
          "SELECT * " +
                  "FROM serveur " +
                  "WHERE nomserv = "+nomAModif
        );

        resServ.next();

        if (email.equals("")){
            email = resServ.getString("email");
        }
        if (mdp.equals("")){
            mdp = resServ.getString("passwd");
        }
        if (nom.equals("")){
            nom = nomAModif;
        }
        if (grade.equals("")){
            grade = resServ.getString("grade");
        }

        co.createStatement().executeUpdate("" +
                "UPDATE serveur SET email = "+email+", passwd = "+mdp+",nomserv = "+nom+", grade = "+grade+" "+
                "WHERE nomserv = "+nomAModif
        );

        co.commit();
    }

    public void affecterServeur(String preNom, int numtab) throws SQLException {
        co.setAutoCommit(false);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateAff = format.format(date);

        ResultSet resNumServ = co.createStatement().executeQuery("" +
                "SELECT numserv " +
                "FROM serveur " +
                "WHERE nomserv like "+"\""+preNom+"\""
        );

        resNumServ.next();
        String numserv = resNumServ.getString(1);

        co.createStatement().executeUpdate("" +
                "INSERT INTO affecter " +
                "VALUES ("+numtab+","+"\""+dateAff+"\""+","+numserv+")"
        );


        co.commit();
    }

    public void creerPlat(String libelle, String type, double prixunit, int qteservie) throws SQLException {
        co.setAutoCommit(false);
        int numplat = ID.numplat+1;

        co.createStatement().executeUpdate(
          "INSERT INTO plat VALUES ("+numplat+", \""+libelle+"\",  \""+type+" \" ,"+prixunit+","+qteservie+")"
        );

        ID.reload();
        co.commit();
    }

    public void modifierPlat(String platAModif,String libelle, String type, double prixunit, int qteservie) throws SQLException {
        co.setAutoCommit(false);
        if (!platAModif.equals("")) {
            ResultSet resPlat = co.createStatement().executeQuery(
                    "SELECT * " +
                            "FROM plat " +
                            "WHERE libelle = \"" +platAModif+"\""
            );

            resPlat.next();

            if (libelle.equals("")) {
                libelle = platAModif;
            }
            if (type.equals("")) {
                type = resPlat.getString("type");
            }
            if (prixunit == 0) {
                prixunit = resPlat.getDouble("prixunit");
            }
            if (qteservie == 0) {
                qteservie = resPlat.getInt("qteservie");
            }

            co.createStatement().executeUpdate("" +
                    "UPDATE plat SET libelle = \"" + libelle + "\", type = \"" + type + "\",prixunit = " + prixunit + ", qteservie = " + qteservie + " " +
                    "WHERE libelle = \"" + platAModif+"\""
            );

            co.commit();
        }
    }

    public double calculerMontantTotal(int numres) throws SQLException {
        co.setAutoCommit(false);

        double montant = 0;

        ResultSet commande = co.createStatement().executeQuery("" +
                "SELECT * " +
                "FROM commande " +
                "WHERE numres="+numres
        );

        while (commande.next()){
            int numplat = commande.getInt(2);
            int qte = commande.getInt(3);
            ResultSet resPrix = co.createStatement().executeQuery("" +
                    "SELECT prixunit " +
                    "FROM plat " +
                    "WHERE numplat = "+numplat
            );

            resPrix.next();
            montant = montant + (resPrix.getDouble(1)*qte);
        }

        return montant;
    }

    public HashMap<String,Integer> nombreDeCommande(String datedeb, String datefin) throws SQLException {
        co.setAutoCommit(false);

        HashMap<String,Integer> mapNbCommande = new HashMap<>();

        for (int i = 1 ; i <= ID.numserv ; i++ ) {
            ArrayList<Integer> tabs = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            String nom = "";
            int nbComm = 0;

            ResultSet resServ = co.createStatement().executeQuery(
                    "SELECT * " +
                            "FROM serveur " +
                            "WHERE numserv = " + i
            );

            resServ.next();
            nom = resServ.getString("nomserv");


            ResultSet restab = co.createStatement().executeQuery(
                    "SELECT numtab,dataff " +
                            "FROM affecter " +
                            "WHERE numserv = " + i + " " +
                            "AND DATEDIFF('" + datefin + "',dataff) >= 0 AND DATEDIFF('" + datedeb + "',dataff) <= 0"
            );

            while (restab.next()) {
                tabs.add(restab.getInt(1));
                dates.add(restab.getString(2));
            }
            int cpt = 0;
            for (int tab:
                    tabs) {

                ResultSet resRes = co.createStatement().executeQuery(
                        "SELECT numres, datres " +
                                "FROM reservation " +
                                "WHERE numtab = "+tab
                );

                while (resRes.next()){
                    if (resRes.getString(2).contains(dates.get(cpt))){

                        ResultSet resCommandes = co.createStatement().executeQuery("" +
                                "SELECT COUNT(*) " +
                                "FROM commande " +
                                "WHERE numres="+resRes.getString("numres")
                        );

                        resCommandes.next();

                        mapNbCommande.put(nom,resCommandes.getInt(1));
                    }
                }
                cpt++;
            }
        }

        return mapNbCommande;
    }

    public HashMap<String,Double> chiffreAffaire(String datedeb,String datefin) throws SQLException {
        co.setAutoCommit(false);

        HashMap<String,Double> mapServCh = new HashMap<>();
        ArrayList<Double> tabChAffServ = new ArrayList<>();

        for (int i = 1 ; i <= ID.numserv ; i++ ) {
            ArrayList<Integer> tabs = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            String nom = "";
            double chAff = 0;

            ResultSet resServ = co.createStatement().executeQuery(
              "SELECT * " +
                      "FROM serveur " +
                      "WHERE numserv = "+i
            );

            resServ.next();
            nom = resServ.getString("nomserv");


            ResultSet restab = co.createStatement().executeQuery(
                    "SELECT numtab,dataff " +
                            "FROM affecter " +
                            "WHERE numserv = "+i+" " +
                            "AND DATEDIFF('"+datefin+"',dataff) >= 0 AND DATEDIFF('"+datedeb+"',dataff) <= 0"
            );

            while (restab.next()){
                tabs.add(restab.getInt(1));
                dates.add(restab.getString(2));
            }
            int cpt = 0;
            for (int tab:
                 tabs) {

                ResultSet resRes = co.createStatement().executeQuery(
                        "SELECT numres, datres " +
                                "FROM reservation " +
                                "WHERE numtab = "+tab
                );

                while (resRes.next()){
                    if (resRes.getString(2).contains(dates.get(cpt))){
                        chAff += this.calculerMontantTotal(resRes.getInt(1));
                    }
                }

                cpt++;
            }

            tabChAffServ.add(chAff);
            mapServCh.put(nom,chAff);
        }

        return mapServCh;
    }

    public ArrayList<String> pasDeChiffreAff(String datedeb,String datefin) throws SQLException {
        co.setAutoCommit(false);

        ArrayList<String> listServ = new ArrayList<>();

        for (int i = 1 ; i <= ID.numserv ; i++ ) {
            ArrayList<Integer> tabs = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            String nom = "";
            double chAff = 0;

            ResultSet resServ = co.createStatement().executeQuery(
                    "SELECT * " +
                            "FROM serveur " +
                            "WHERE numserv = " + i
            );

            resServ.next();
            nom = resServ.getString("nomserv");


            ResultSet restab = co.createStatement().executeQuery(
                    "SELECT numtab,dataff " +
                            "FROM affecter " +
                            "WHERE numserv = " + i + " " +
                            "AND DATEDIFF('" + datefin + "',dataff) >= 0 AND DATEDIFF('" + datedeb + "',dataff) <= 0"
            );

            while (restab.next()) {
                tabs.add(restab.getInt(1));
                dates.add(restab.getString(2));
            }
            int cpt = 0;
            for (int tab:
                    tabs) {

                ResultSet resRes = co.createStatement().executeQuery(
                        "SELECT numres, datres " +
                                "FROM reservation " +
                                "WHERE numtab = "+tab
                );

                while (resRes.next()){
                    if (resRes.getString(2).contains(dates.get(cpt))){
                        chAff += this.calculerMontantTotal(resRes.getInt(1));
                    }
                }

                cpt++;
            }

            if (chAff==0){
                listServ.add(nom);
            }

        }

        return listServ;
    }

}
