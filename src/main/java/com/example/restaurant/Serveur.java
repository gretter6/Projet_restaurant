package com.example.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Serveur {
    private final Connection co = DBConnection.getInstance().getConnect();

    public Serveur() throws SQLException {
    }

    public ArrayList<String> seConnecter(String email, String mdp) throws SQLException {
        co.setAutoCommit(false);

        ArrayList<String> serv = new ArrayList<>();
        String grade = "";
        ResultSet res = co.createStatement().executeQuery("" +
                "SELECT * " +
                "FROM serveur " +
                "WHERE email = \""+email+"\" AND passwd = \""+mdp+"\""
        );

        res.next();

        serv.add(res.getString(2));
        serv.add(res.getString(4));
        serv.add(res.getString(5));

        return serv;
    }

    public ArrayList<Integer> consulterTable(String date, String heure) throws SQLException {
        co.setAutoCommit(false);

        String datheur = date+" "+heure;
        ArrayList<Integer> tables = new ArrayList<>();
        ResultSet resT = co.createStatement().executeQuery("" +
                "SELECT DISTINCT numtab " +
                "FROM tabl " +
                "WHERE numtab NOT IN (" +
                    "SELECT numtab " +
                    "FROM reservation " +
                    "WHERE datres=\""+datheur+"\"" +
                ");"
        );

        while (resT.next()){
            tables.add(resT.getInt(1));
        }

        return tables;

    }

    public void reserverTable(int numtab, int nbpers, String date, String heure) throws SQLException {
        co.setAutoCommit(false);

        String datheur = date+" "+heure;
        int numR = ID.numres+1;
        co.createStatement().executeUpdate("" +
                "INSERT INTO reservation(numres,numtab,datres,nbpers) " +
                "VALUES (" + numR + "," + numtab + "," + "\"" +datheur+ " \" " + "," + nbpers + ")"
        );

        co.commit();
        ID.reload();
    }

    public ArrayList<String> consulterPlats() throws SQLException {
        co.setAutoCommit(false);
        ArrayList<String> plats = new ArrayList<>();

        ResultSet nbp = co.createStatement().executeQuery("SELECT COUNT(*) AS nb FROM plat;");
        nbp.next();
        for (int i = 1 ; i <= nbp.getInt(1) ; i++){


            ResultSet resqte = co.createStatement().executeQuery("" +
                    "SELECT libelle,qteservie " +
                    "FROM plat " +
                    "WHERE numplat="+i
            );

            resqte.next();

            ResultSet resserv = co.createStatement().executeQuery("" +
                    "SELECT SUM(quantite)" +
                    "FROM commande " +
                    "WHERE numplat="+i
            );

            resserv.next();

            int restant = resqte.getInt(2)-resserv.getInt(1);

            if (restant>0){
                plats.add(resqte.getString(1));
            }

        }

        return plats;

    }

    public int commanderPlat(int numres, int numplat, int qte) throws SQLException {
        co.setAutoCommit(false);

        int resComP = co.createStatement().executeUpdate("" +
                "INSERT INTO commande VALUES ("+numres+","+numplat+","+qte+");");

        co.commit();

        return resComP;
    }
}
