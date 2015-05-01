package org.friet.net.database;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
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
import javax.swing.JOptionPane;
import org.friet.net.login.panel.PanelLogin;
import org.friet.net.main.Main;

public class Database {

    final static String DBPAD = "src/res/DB.mdb";
    final static String DB = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + DBPAD;

    private int rand = 0;
    public Connection con;
    private static ArrayList hallo = new ArrayList();

    public Database() {

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            con = DriverManager.getConnection(DB, "", "");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                int first = 5000, last = 5000;

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
                int first = 5000, last = 5000;

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
            JOptionPane.showMessageDialog(null, "Kon de werknemer niet toevoegen aan de database.\n Vul de velden met de juiste gegeventypes in.", "Error", JOptionPane.ERROR_MESSAGE);
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
                String Barcode = set.getString("Barcode");
                hash.put("naam", naam);
                hash.put("prijs per item", set.getString("PrijsPerItem"));
                hash.put("hoeveelheid per item", set.getString("HoeveelheidperItem"));
                hash.put("Barcode", Barcode == null ? "" : Barcode);
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
        rand++;
        if (rand == hallo.size()) {
            rand = 0;
        }
        try {
            return (Color) hallo.get(rand);
        } catch (Exception e) {
            return new Color(rand, rand, rand);
        }
    }

    public String getnaam(String s) {
        Statement stat;
        ResultSet set;
        String out = "";
        try {
            stat = con.createStatement();
            stat.execute("SELECT Naam FROM Item WHERE Item.Barcode = '"+s+"' ORDER BY Item.ItemId");
            set = stat.getResultSet();
            while (set.next()) {
                out = set.getString("Naam").toLowerCase();
                break;
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
            stat.execute("SELECT SoortNaam FROM Inhoud WHERE Inhoud.barcodes='" + s + "' ORDER BY Inhoud.SoortId");
            set = stat.getResultSet();
            while (set.next()) {
                s = set.getString("SoortNaam").toLowerCase();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;

    }

    public void addItemBestelling(int naam, int amount) {
        Statement stat;
        ResultSet set;
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
        Map<String, Object> hallo = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                
                return -o1.compareTo(o2);
            }
        });
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT Bestelling.Totaalprijs, Bestelling.VerkoopDatum, Item.Naam, Item.ItemID, ItemsBestelling.BestellingID\n"
                    + "FROM Item INNER JOIN (Bestelling INNER JOIN ItemsBestelling ON Bestelling.BestellingID = ItemsBestelling.BestellingID) ON Item.ItemID = ItemsBestelling.ItemID\n"
                    + "ORDER BY Bestelling.VerkoopDatum;");
            set = stat.getResultSet();
            while (set.next()) {
                Map<String, String> hash;
                hash = new HashMap<String, String>();
                Date naam = set.getDate("verkoopdatum");
                String id = set.getString("BestellingID");
                if (hallo.containsKey(id) == true) {
                    Object srrrt = hallo.get(id);
                    hash.put("verkoopdatum", naam.toString());
                    hash.put("prijs", set.getString("Totaalprijs"));
                    Map<String, String> hashh = (Map<String, String>) srrrt;
                    hash.put("naam", hashh.get("naam") + ", " + set.getString("Naam"));
                    hallo.put(id, hash);
                } else {
                    hash.put("verkoopdatum", naam.toString());
                    hash.put("prijs", set.getString("Totaalprijs"));
                    hash.put("naam", set.getString("Naam"));
                    hallo.put(id, hash);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hallo;
    }

    public String addNewItem(String naam, String HoeveelheidPerItem, String Prijs, String Inhoud, String Barcode, String soortnaam) {
        Statement stat;
        ResultSet set;
        int inhoudID = -1;
        int SoortID = -1;
        try {
            stat = con.createStatement();
            stat.execute("SELECT inhoud.SoortId, inhoud.SoortNaam\n"
                    + "FROM inhoud\n"
                    + "WHERE (((inhoud.SoortNaam)='" + Inhoud + "'));");
            set = stat.getResultSet();
            while (set.next()) {
                inhoudID = set.getInt("SoortId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inhoudID == -1) {
            return "Faute ingave van veld Inhoud. In did veld moet de naam van de Inhoud staan waraan hij gelinkt staat.";
        }
        try {
            stat = con.createStatement();
            stat.execute("SELECT Soort.SoortId, Soort.CategorieNaam\n"
                    + "FROM Soort\n"
                    + "WHERE (((Soort.CategorieNaam)='" + soortnaam + "'));");
            set = stat.getResultSet();
            while (set.next()) {
                SoortID = set.getInt("SoortId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stat = con.createStatement();
            stat.execute("INSERT INTO Item ( Naam, PrijsPerItem, Soort, SoortId, HoeveelheidPerItem,Barcode )\n"
                    + "VALUES ('" + naam + "', " + Prijs + ", " + SoortID + ", " + inhoudID + ", " + HoeveelheidPerItem + ", '" + Barcode + "')");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            return "Kon het Item niet Toevoegen aan de database probeer opnieuw.";
        }
        return "Kon het item goed toevoegen.";
    }

    public Map<String, Object> getInfoInhoud() {

        Map<String, Object> hallo = new HashMap<String, Object>();
        Statement stat;
        ResultSet set;
        try {
            stat = con.createStatement();
            stat.execute("SELECT inhoud.SoortNaam, inhoud.Hoeveelheid, Soort.CategorieNaam, inhoud.Barcodes\n"
                    + "FROM Soort INNER JOIN inhoud ON Soort.SoortID = inhoud.Categorie;");
            set = stat.getResultSet();
            while (set.next()) {
                Map<String, String> hash;
                hash = new HashMap<String, String>();
                String naam = set.getString("SoortNaam");
                String cat = set.getString("CategorieNaam");
                String barcode = set.getString("Barcodes");
                hash.put("naam", naam);
                hash.put("Hoeveelheid", set.getString("Hoeveelheid"));
                hash.put("barcode", barcode == null ? "" : barcode);
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

    public String addNewInhoud(String text, String text0, String Barcode, String soortnaam) {
        Statement stat;
        ResultSet set;
        int SoortID = 0;

        try {
            stat = con.createStatement();
            stat.execute("SELECT Soort.SoortId, Soort.CategorieNaam\n"
                    + "FROM Soort\n"
                    + "WHERE (((Soort.CategorieNaam)='" + soortnaam + "'));");
            set = stat.getResultSet();
            while (set.next()) {
                SoortID = set.getInt("SoortId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stat = con.createStatement();
            stat.execute("INSERT INTO Inhoud ( Categorie, Soortnaam, Hoeveelheid, Barcodes)\n"
                    + "VALUES (" + SoortID + ", '" + text + "', " + text0 + "," + Barcode + ")");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon de inhoud niet toevoegen aan de Database.";
        }
        return "Kon de inhoud sucsessvol toevoegen aan de database.";
    }

    public String addNewSoort(String text) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("INSERT INTO Soort ( Categorienaam )\n"
                    + "VALUES ('" + text + "')");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon de Soort niet toevoegen aan de Database.";
        }
        return "Kon de Soort sucsessvol toevoegen aan de database.";
    }

    public String removeSoort(String Naam) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("DELETE FROM Soort\n"
                    + "WHERE CategorieNaam='" + Naam + "';");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon het Soort niet verwijderen van de Database.";
        }
        return "Kon het Soort sucsessvol verwijderen van database.";
    }

    public String removeInhoud(String Naam) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("DELETE FROM inhoud\n"
                    + "WHERE SoortNaam='" + Naam + "';");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            return "Kon de inhoud niet verwijderen van de Database.";
        }
        return "Kon de inhoud sucsessvol verwijderen van database.";
    }

    public String removeItem(String Naam) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("DELETE FROM Item\n"
                    + "WHERE Naam='" + Naam + "';");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon het Item niet verwijderen van de Database.";
        }
        return "Kon het Item sucsessvol verwijderen van database.";
    }

    public String updateSoort(String currentSelected, String NaamNieuw) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("UPDATE Item, soort SET soort.CategorieNaam = '" + NaamNieuw + "'\n"
                    + "WHERE (([Soort].[CategorieNaam]='" + currentSelected + "'));");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon de soort niet opslaan.";
        }
        return "Kon de soort sucsessvol opslaan.";
    }


    public String updateInhoud(String currentSelected, String NaamNieuw, String HoeveelHeidNieuw, String Barcode) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("UPDATE inhoud SET inhoud.SoortNaam = '" + NaamNieuw + "', inhoud.Hoeveelheid = '" + HoeveelHeidNieuw + "' , inhoud.Barcodes = '" + Barcode + "' "
                    + "WHERE (([inhoud].[SoortNaam])='" + currentSelected + "')");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon de Inhoud niet Opslaan.";
        }
        return "Kon de inhoud sucsessvol opslaan.";
    }

    public String updateItem(String currentSelected, String NaamNieuw, String HoeveelheidPerItem, String PrijsPerItem, String Barcode) {
        Statement stat;
        ResultSet set;

        try {
            stat = con.createStatement();
            stat.execute("UPDATE Item SET Item.Naam = '" + NaamNieuw + "', Item.HoeveelheidPerItem = '" + HoeveelheidPerItem + "', Item.PrijsPerItem = '" + PrijsPerItem + "', Item.Barcode='" + Barcode + "'\n"
                    + "WHERE (([Item].[Naam]='"+currentSelected+"'));");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Kon de Item niet Opslaan.";
        }
        return "Kon de Item sucsessvol opslaan.";
    }

    public Float getItemsInhoud(String get) {
        Statement stat;
        ResultSet set;
        Float ok = 0f;
        try {
            stat = con.createStatement();
            stat.execute("SELECT SoortID FROM Inhoud WHERE Inhoud.soortNaam = '" + get + "';");
            set = stat.getResultSet();
            set.next();
            String str = set.getString("SoortID");
            set.close();
            stat.close();
            System.out.print(str);
            stat = con.createStatement();
            stat.execute("SELECT HoeveelheidPerItem FROM item WHERE item.soortid = " + str + ";");
            set = stat.getResultSet();
            set.next();
            ok = set.getFloat("HoeveelheidPerItem");
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }
}
