package org.friet.net.main;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import org.friet.net.UI.UI;
import org.friet.net.database.*;
import org.friet.net.login.panel.*;
import org.friet.net.main.panel.*;

public class Main extends JFrame {

    public static Main frame;
    public MainPanel main;
    public JMenuBar menu;
    public static Database db;
    public static boolean manager;
    public static int Werknemmersnummer;
    
    
    
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
        frameLogin.setSize(250, 150);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelLogin panel = new PanelLogin();
        frameLogin.add(panel);
        frameLogin.setVisible(true);
        frameLogin.setAlwaysOnTop(true);
        frameLogin.setResizable(false);
        //TODO: uitroepteken
        while (panel.done) {

        }

        frameLogin.setVisible(false);
        return false;
    }
    
    public Main(){
        this.setLayout(new GridLayout(1,1));
        
        this.setName("Friet.net");
        this.setTitle("Friet.net");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,600,600);
        
        Event event = new Event();
        
        menu = new JMenuBar();
        
        JMenu levering = new JMenu("Levering");
        levering.addMenuListener(event);
        levering.setVisible(true);
        
        JMenu Bestelling = new JMenu("Bestelling");
        Bestelling.addMenuListener(event);
        Bestelling.setVisible(true);
        
        menu.add(levering);
        menu.add(Bestelling);
        
        if (Main.manager) {

        }
        
        menu.setVisible(true);
        this.setJMenuBar(menu);
        
        main = new MainPanel();
        main.setVisible(true);
        this.add(main);
        
        this.setVisible(true);
        this.revalidate();
        System.out.print(main.footer.getBounds());
        this.repaint();
    }
    
    public class Event implements MenuListener {

        @Override
        public void menuSelected(MenuEvent e) {
            if (e.getSource() == menu.getMenu(0)) {
                //Main.frame.main.bestelling.setVisible(false);
                Main.frame.main.levering.setVisible(true);
            } else if (e.getSource() == menu.getMenu(1)) {
                //Main.frame.main.bestelling.setVisible(true);
                Main.frame.main.levering.setVisible(false);
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
