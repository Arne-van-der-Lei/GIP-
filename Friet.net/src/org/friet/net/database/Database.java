package org.friet.net.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.friet.net.main.Main;

public class Database {

    final static String DBPAD = "src/res/DB.mdb";
    final static String DB = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + DBPAD;

    public Connection con;

    public Database() {

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            con = DriverManager.getConnection(DB, "", "");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addItem(String naam, float amount) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT Hoeveelheid FROM inhoud WHERE soortnaam='" + naam + "'");
            set = stat.getResultSet();
            float i = 0;
            while (set.next()) {
                i = set.getFloat("Hoeveelheid");
            }
            i += amount;
            stat = con.createStatement();
            stat.execute("UPDATE inhoud SET Hoeveelheid=" + i + " WHERE soortnaam='" + naam + "'");

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int checkPass(String user, String password) {

        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("SELECT naam,pass,manager,loginid FROM login");
            set = stat.getResultSet();
            while (set.next()) {
                String naam = set.getString("naam");
                String pass = set.getString("pass");
                Main.Werknemmersnummer = set.getInt("loginid");
                if (naam.equals(user) && password.equals(pass)) {
                    if (set.getBoolean("manager")) {
                        return 2;
                    }
                    return 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public Map<String, Map<String, Float>> getItems() {

        Map<String, Map<String, Float>> items = new HashMap<String, Map<String, Float>>();

        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT naam,PrijsPerItem,kindnaam FROM Item,soort WHERE soort.soortid = Item.soortid");
            set = stat.getResultSet();
            while (set.next()) {
                String soort = set.getString("kindnaam");
                String naam = set.getString("naam");
                float value = set.getFloat("PrijsPerItem");

                if (items.containsKey(soort)) {
                    items.get(soort).put(naam, value);
                } else {
                    Map<String, Float> item = new HashMap<String, Float>();
                    item.put(naam, value);
                    items.put(soort, item);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    public Map<String, Map<String, Float>> getInhoud() {

        Map<String, Map<String, Float>> items = new HashMap<String, Map<String, Float>>();

        Statement stat;
        ResultSet set;
        Statement stat2;
        ResultSet set2;
        try {
            stat = con.createStatement();
            stat.execute("SELECT soortnaam,kindnaam FROM Inhoud,soort Where Inhoud.kind = soort.soortid");
            set = stat.getResultSet();
            while (set.next()) {
                String naam = set.getString("soortnaam");
                String soort = set.getString("kindnaam");
                if (items.containsKey(soort)) {
                    items.get(soort).put(naam, 1f);
                } else {
                    Map<String, Float> item = new HashMap<String, Float>();
                    item.put(naam, 1f);
                    items.put(soort, item);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    public void verminderItem(String naam) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT hoeveelheid,HoeveelheidPerItem FROM inhoud,item WHERE item.naam = '" + naam + "'and item.soort= inhoud.soortid");
            set = stat.getResultSet();
            float i = 0;
            float j = 0;
            while (set.next()) {
                i = set.getFloat("hoeveelheid");
                j = set.getFloat("HoeveelheidPerItem");
            }
            i -= j;
            stat = con.createStatement();
            stat.execute("UPDATE inhoud,item SET hoeveelheid=" + i + " WHERE item.naam='" + naam + "' and item.soort= inhoud.soortid");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addBestelling(int klantId, int werknemerId, float totaalprijs) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("insert into bestelling(KlantID,WerknemerID,Totaalprijs) values(" + klantId + "," + werknemerId + "," + totaalprijs + ")");
            set = stat.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addKlant(String email, String naam) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("insert into klanten values(" + email + "," + naam + ")");
            set = stat.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addwerknemer(String naam, String adress, String gemeente, String email, String login) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("insert into login values(" + naam + "," + login + ")");
            stat = con.createStatement();
            stat.execute("SELECT id FROM login WHERE login.naam ='" + naam + "'");
            set = stat.getResultSet();
            set.next();
            stat = con.createStatement();
            stat.execute("insert into werknemer values(" + naam + "," + adress + "," + gemeente + "," + email + "," + set.getInt("id") + ")");

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatewerknemer(String naam, String adress, String gemeente, String email, int ID) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("Update naam,adres,gemeente,e-mailadres SET naam='" + naam + "',adres='" + adress + "',gemeente='" + gemeente + "',e-mailadres='" + email + "' WHERE WerknemerID=" + ID + "");

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList getwerknemers() {

        ArrayList hallo = new ArrayList();
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT * FROM werknemer ");
            set = stat.getResultSet();
            while (set.next()) {
                Map<String, String> hash = new TreeMap<String, String>();

                hash.put("naam", set.getString("naam"));
                hash.put("adres", set.getString("adres"));
                hash.put("gemeente", set.getString("gemeente"));
                hash.put("e-mail", set.getString("e-mailadres"));
                hash.put("werknemer ID", set.getString("WerknemerID"));
                hallo.add(hash);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hallo;
    }
}
