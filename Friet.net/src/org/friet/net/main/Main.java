package org.friet.net.main;

import java.awt.Dimension;
import java.awt.GridLayout;
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
    
    
    public static void main(String[] args) {
        UI.setLAF();
        
        InitDB();

        while (login()) {
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
        while (!panel.done) {

        }

        frameLogin.setVisible(false);
        return false;
    }
    
    public Main(){
        this.setLayout(new GridLayout(1,1));
        
        this.setName("Friet.net");
        this.setTitle("Friet.net");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0,0,600,600);
        
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

            menu.add(Werknemer);

            menu.getMenu(2).setBorderPainted(false);
        }

        menu.getMenu(0).setBorderPainted(true);
        menu.getMenu(1).setBorderPainted(false);

        menu.setVisible(true);
        this.setJMenuBar(menu);
        
        main = new MainPanel();
        main.setVisible(true);
        this.add(main);
        
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }
}
