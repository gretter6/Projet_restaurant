package com.example.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ID {
    public static int numplat;
    public static int numres;
    public static int numserv;
    public static int numtab;

    public static void reload() throws SQLException {
        Connection co = DBConnection.getInstance().getConnect();
        co.setAutoCommit(false);

        ResultSet res = co.createStatement().executeQuery("SELECT MAX(numplat) FROM plat;");
        res.next();
        numplat = res.getInt(1);

        res = co.createStatement().executeQuery("SELECT MAX(numres) FROM reservation;");
        res.next();
        numres = res.getInt(1);

        res = co.createStatement().executeQuery("SELECT MAX(numserv) FROM serveur;");
        res.next();
        numserv = res.getInt(1);

        res = co.createStatement().executeQuery("SELECT MAX(numtab) FROM tabl;");
        res.next();
        numtab = res.getInt(1);
    }


}
