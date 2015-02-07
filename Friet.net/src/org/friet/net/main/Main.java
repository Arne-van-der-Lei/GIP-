package org.friet.net.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
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
        Main.manager = true;

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
        
        Event event = new Event();
        
        menu = new JMenuBar();
        menu.setMaximumSize(new Dimension(10, 100));
        
        JMenu levering = new JMenu("Levering");
        levering.addMenuListener(event);
        levering.setVisible(true);
        
        JMenu Bestelling = new JMenu("Bestelling");
        Bestelling.addMenuListener(event);
        Bestelling.setVisible(true);

        menu.add(Bestelling);
        menu.add(levering);

        if (Main.manager) {
            JMenu Werknemer = new JMenu("Werknemer");
            Werknemer.addMenuListener(event);
            Werknemer.setVisible(true);

            menu.add(Werknemer);
        }
        
        menu.setVisible(true);
        this.setJMenuBar(menu);
        
        main = new MainPanel();
        main.setVisible(true);
        this.add(main);
        
        this.setVisible(true);
        this.revalidate();
        this.repaint();
        //System.out.println(Main.frame.main.levering.cansel.getBounds());
    }
    
    public class Event implements MenuListener {

        @Override
        public void menuSelected(MenuEvent e) {
            if (e.getSource() == menu.getMenu(0)) {
                Main.frame.main.bestelling.setVisible(true);
                Main.frame.main.levering.setVisible(false);
                Main.frame.main.werknemers.setVisible(false);
            } else if (e.getSource() == menu.getMenu(1)) {
                Main.frame.main.bestelling.setVisible(false);
                Main.frame.main.levering.setVisible(true);
                Main.frame.main.werknemers.setVisible(false);
            } else if (e.getSource() == menu.getMenu(2)) {
                Main.frame.main.bestelling.setVisible(false);
                Main.frame.main.levering.setVisible(false);
                Main.frame.main.werknemers.setVisible(true);
            }
        }

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        @Override
        public void menuCanceled(MenuEvent e) {
        }

    }
}
