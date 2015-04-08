package org.friet.net.database;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.friet.net.login.panel.PanelLogin;
import org.friet.net.main.Main;

public class Database {

    final static String DBPAD = "src/res/DB.mdb";
    final static String DB = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + DBPAD;

    private int rand = 0;
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

    public int checkPass(String scan) {

        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("SELECT scann,manager,loginid FROM login");
            set = stat.getResultSet();
            while (set.next()) {
                String naam = set.getString("scann");
                Main.Werknemmersnummer = set.getInt("loginid");
                if (naam.toUpperCase().equals(scan.toUpperCase())) {
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

    public TreeMap<String, TreeMap<String, Float>> getItems() {

        TreeMap<String, TreeMap<String, Float>> items;
        items = new TreeMap<>(new Comparator<String>() {
            public String[] ok = new String[]{"frieten", "sauzen", "snacks", "drank"};

            @Override
            public int compare(String o1, String o2) {
                int first = 1, last = 1;

                for (int i = 0; i < ok.length; i++) {
                    if (o1.equals(ok[i])) {
                        first = i;
                    }

                    if (o2.equals(ok[i])) {
                        last = i;
                    }
                }

                return first > last ? 1 : first < last ? -1 : 0;
            }
        });
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT naam,PrijsPerItem,CategorieNaam FROM Item,soort WHERE soort.soortid = Item.soort");
            set = stat.getResultSet();
            while (set.next()) {
                String soort = set.getString("CategorieNaam");
                String naam = set.getString("naam");
                float value = set.getFloat("PrijsPerItem");

                if (items.containsKey(soort)) {
                    items.get(soort).put(naam.toLowerCase(), new Float(value));
                } else {
                    TreeMap<String, Float> item;
                    item = new TreeMap<>();
                    item.put(naam.toLowerCase(), new Float(value));
                    items.put(soort, item);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    public Map<String, Map<String, Float>> getInhoud() {

        TreeMap<String, Map<String, Float>> items;
        items = new TreeMap<>(new Comparator<String>() {
            public String[] ok = new String[]{"frieten", "sauzen", "snacks", "drank"};

            @Override
            public int compare(String o1, String o2) {
                int first = 1, last = 1;

                for (int i = 0; i < ok.length; i++) {
                    if (o1.equals(ok[i])) {
                        first = i;
                    }

                    if (o2.equals(ok[i])) {
                        last = i;
                    }
                }

                return first > last ? 1 : first < last ? -1 : 0;
            }
        });
        Statement stat;
        ResultSet set;
        Statement stat2;
        ResultSet set2;
        try {
            stat = con.createStatement();
            stat.execute("SELECT soortnaam,CategorieNaam FROM Inhoud,soort Where Inhoud.Categorie = soort.soortid");
            set = stat.getResultSet();
            while (set.next()) {
                String naam = set.getString("soortnaam");
                String soort = set.getString("CategorieNaam");
                if (items.containsKey(soort)) {
                    items.get(soort).put(naam, 1f);
                } else {
                    TreeMap<String, Float> item;
                    item = new TreeMap<>();
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

    public int addBestelling(int werknemerId, float totaalprijs) {
        int out = 0;
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("insert into bestelling(WerknemerID,Totaalprijs,VerkoopDatum) values(" + werknemerId + "," + totaalprijs + ",NOW())");
            stat.execute("Select BestellingId FROM bestelling");
            set = stat.getResultSet();
            while (set.next()) {
                out = set.getInt("BestellingId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
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

    public void addwerknemer(String naam, String Voornaam, String adres, String gemeente, String post, String email, String login, String telefoonnummer) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("insert into login (naam,pass) values ('" + Voornaam + "','" + PanelLogin.encode(login) + "')");
            stat = con.createStatement();
            stat.execute("SELECT loginId FROM login WHERE login.naam ='" + Voornaam + "'");
            set = stat.getResultSet();
            set.next();
            stat = con.createStatement();
            stat.execute("insert into werknemer (naam,voornaam,adres,gemeente,postcode,emailadres,loginid,telefoonnummer) values ('" + naam + "','" + Voornaam + "','" + adres + "','" + gemeente + "','" + post + "','" + email + "','" + set.getInt("loginid") + "','" + telefoonnummer + "')");

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delwerknemer(int id) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("DELETE FROM werknemer WHERE Werknemer.WerknemerID=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean updatewerknemer(String naam, String Voornaam, String adres, String gemeente, String post, String email, String telefoonnummer, int ID) {
        Statement stat;
        ResultSet set;
        boolean i = false;
        try {
            stat = con.createStatement();
            stat.execute("UPDATE werknemer SET Naam='" + naam + "',Adres='" + adres + "',Gemeente='" + gemeente + "',Emailadres='" + email + "',Voornaam='" + Voornaam + "',Postcode='" + post + "',telefoonnummer='" + telefoonnummer + "' WHERE werknemer.WerknemerID=" + ID);
            set = stat.getResultSet();
            i = (set == null);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
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
                TreeMap<String, String> hash;
                hash = new TreeMap<>(new Comparator<String>() {
                    public String[] ok = new String[]{"Werknemer ID", "Voornaam", "Naam", "Adres", "Postcode", "Gemeente", "Telefoonnummer", "E-mail"};

                    @Override
                    public int compare(String o1, String o2) {
                        int first = 1, last = 1;

                        for (int i = 0; i < ok.length; i++) {
                            if (o1.equals(ok[i])) {
                                first = i;
                            }

                            if (o2.equals(ok[i])) {
                                last = i;
                            }
                        }

                        return first > last ? 1 : first < last ? -1 : 0;
                    }
                });
                hash.put("Voornaam", set.getString("voornaam"));
                hash.put("Adres", set.getString("adres"));
                hash.put("Gemeente", set.getString("gemeente"));
                hash.put("E-mail", set.getString("emailadres"));
                hash.put("Werknemer ID", set.getString("WerknemerID"));
                hash.put("Naam", set.getString("naam"));
                hash.put("Postcode", set.getString("postcode"));
                hash.put("Telefoonnummer", set.getString("telefoonnummer"));
                hallo.add(hash);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hallo;
    }

    public Map<String, Object> getInfoItems() {

        Map<String, Object> hallo = new HashMap<String, Object>();
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT * FROM Item,Soort WHERE Item.soort = Soort.soortID");
            set = stat.getResultSet();
            while (set.next()) {
                Map<String, String> hash;
                hash = new HashMap<String, String>();
                String naam = set.getString("Naam");
                String cat = set.getString("categorienaam");
                hash.put("naam", naam);
                hash.put("prijs per item", set.getString("PrijsPerItem"));
                hash.put("hoeveelheid per item", set.getString("HoeveelheidperItem"));

                if (hallo.get(cat) == null) {
                    Map<String, Object> hash2 = new HashMap<String, Object>();
                    hash2.put(naam, hash);
                    hash2.put("naam", cat);
                    hallo.put(cat, hash2);
                } else {
                    ((Map<String, Object>) hallo.get(cat)).put(naam, hash);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hallo;
    }

    public Color randomKleur() {

        ArrayList hallo = new ArrayList();
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT * FROM kleur ORDER BY ID");
            set = stat.getResultSet();
            while (set.next()) {
                hallo.add(new Color(set.getInt("r"), set.getInt("g"), set.getInt("b")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        rand++;
        if (rand == hallo.size()) {
            rand = 0;
        }
        return (Color) hallo.get(rand);
    }

    public String getnaam(String s) {
        Statement stat;
        ResultSet set;
        String out = "";
        try {
            stat = con.createStatement();
            stat.execute("SELECT Naam,barcode FROM Item,Barcodes ORDER BY Item.ItemId");
            set = stat.getResultSet();
            while (set.next()) {
                if (set.getString("barcode").equals(s)) {
                    out = set.getString("Naam");
                }
            }
            set.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;

    }

    public String getnaaml(String s) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT SoortNaam FROM Inhoud,barcodes WHERE barcodes.barcode='" + s + "' ORDER BY Inhoud.SoortId");
            set = stat.getResultSet();
            while (set.next()) {
                s = set.getString("SoortNaam");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;

    }

    public void addItemBestelling(int naam, int amount) {
        Statement stat;
        ResultSet set;
        System.out.println(naam + " " + amount);
        try {
            stat = con.createStatement();
            stat.execute("INSERT INTO ItemsBestelling (ItemId,BestellingId) VALUES (" + naam + "," + amount + ")");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getIdFromItemName(String name) {
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT ItemID FROM Item WHERE Item.naam = '" + name + "'");
            stat.closeOnCompletion();
            set = stat.getResultSet();
            while (set.next()) {
                return set.getInt("ItemId");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Map<String, Object> getBestellingen() {
        Map<String, Object> hallo = new HashMap<String, Object>();
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT Bestelling.VerkoopDatum, Item.PrijsPerItem, Item.Naam, ItemsBestelling.BestellingID, ItemsBestelling.ItemID\n"
                    + "FROM Werknemer INNER JOIN (Item INNER JOIN (Bestelling INNER JOIN ItemsBestelling ON Bestelling.BestellingID = ItemsBestelling.BestellingID) ON Item.ItemID = ItemsBestelling.ItemID) ON Werknemer.WerknemerID = Bestelling.WerknemerID;");
            set = stat.getResultSet();
            while (set.next()) {
                Map<String, String> hash;
                hash = new HashMap<String, String>();
                String naam = set.getString("naam");
                hash.put("verkoopdatum", set.getString("verkoopdatum"));
                hash.put("prijs", set.getString("PrijsPerItem"));
                hash.put("naam", naam);
                hallo.put(set.getString("BestellingID") + naam, hash);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hallo;
    }
}
