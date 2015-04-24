package org.friet.net.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import org.friet.net.UI.Menu;
import org.friet.net.UI.UI;
import org.friet.net.database.Database;
import org.friet.net.login.panel.PanelLogin;
import org.friet.net.main.panel.MainPanel;

public class Main extends JFrame {

    public static Main frame;
    public MainPanel main;
    public JMenuBar menu;
    public static Database db;
    public static boolean manager;
    public static int Werknemmersnummer;
    public static int Klantnummer;
    public static String s;
    public static boolean scann = false;

    public static void main(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            private String s;

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (KeyEvent.KEY_RELEASED == e.getID()) { 
                    if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                        try{
                            Main.scann(Main.s);
                        }catch(Exception ex){Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);}
                        Main.s = "";
                    } else {
                        Main.s += e.getKeyChar();
                    }
                }
                return false;
            }
        });

        UI.setLAF();

        InitDB();

        while (login() & !scann) {
        }

        frame = new Main();

    }

    private static void InitDB() {
        db = new Database();
    }

    public static boolean login() {
        JFrame frameLogin = new JFrame("Login");
        frameLogin.setSize(500, 500);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setLayout(null);
        PanelLogin panel = new PanelLogin();
        panel.setBounds(50, 50, 400, 400);
        frameLogin.add(panel);
        frameLogin.setVisible(true);
        frameLogin.setAlwaysOnTop(true);
        frameLogin.setResizable(false);
        //TODO: uitroepteken + manager
        while (!panel.done ) {

        }
        Main.s = "";
        frameLogin.setVisible(false);
        return false;
    }

    public Main() {
        this.setLayout(new GridLayout(1, 1));

        this.setName("Friet.net");
        this.setTitle("Friet.net");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, 600, 600);
        menu = new JMenuBar();
        menu.setMaximumSize(new Dimension(10, 100));

        JMenu levering = new Menu("Levering");
        levering.setVisible(true);

        JMenu Bestelling = new Menu("Bestelling");
        Bestelling.setVisible(true);

        menu.add(Bestelling);
        menu.add(levering);

        if (Main.manager) {
            JMenu Werknemer = new Menu("Werknemer");
            Werknemer.setVisible(true);
            JMenu Info = new Menu("Artikelen");
            Info.setVisible(true);
            JMenu Inhoud = new Menu("Inhoud");
            Inhoud.setVisible(true);

            menu.add(Werknemer);
            menu.add(Info);
            menu.add(Inhoud);

            menu.getMenu(2).setBorderPainted(false);
            menu.getMenu(3).setBorderPainted(false);
            menu.getMenu(4).setBorderPainted(false);
        }

        menu.getMenu(0).setBorderPainted(true);
        menu.getMenu(1).setBorderPainted(false);

        JMenu infoBestellingen = new Menu("Info Bestellingen");
        infoBestellingen.setVisible(true);

        menu.add(infoBestellingen);

        menu.getMenu(5).setBorderPainted(false);
        menu.setVisible(true);
        this.setJMenuBar(menu);

        main = new MainPanel();
        main.setVisible(true);
        this.add(main);

        this.setVisible(true);
    }

    public static void scann(String s) {
        if (Main.frame != null) {
            if (Main.frame.main.levering.allowsBar) {
                Main.frame.main.levering.barcode(s);
            } else if (Main.frame.main.bestelling.allowsBar) {
                Main.frame.main.bestelling.barcode(s);
            }
        }
    }
}
