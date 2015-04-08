/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenu;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class Menu extends JMenu implements MouseListener {

    public Menu(String s) {
        super(s);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == Main.frame.menu.getMenu(0)) {
            Main.frame.main.bestelling.setVisible(true);
            Main.frame.main.levering.setVisible(false);
            Main.frame.main.werknemers.setVisible(false);
            Main.frame.main.items.setVisible(false);
            Main.frame.main.InfoBestellingen.setVisible(false);
            Main.frame.menu.getMenu(4).setBorderPainted(false);
            Main.frame.menu.getMenu(0).setBorderPainted(true);
            Main.frame.menu.getMenu(1).setBorderPainted(false);
            if (Main.manager) {
                Main.frame.menu.getMenu(2).setBorderPainted(false);
                Main.frame.menu.getMenu(3).setBorderPainted(false);
            }
        } else if (e.getSource() == Main.frame.menu.getMenu(1)) {
            Main.frame.main.bestelling.setVisible(false);
            Main.frame.main.levering.setVisible(true);
            Main.frame.main.werknemers.setVisible(false);
            Main.frame.main.items.setVisible(false);
            Main.frame.main.InfoBestellingen.setVisible(false);
            Main.frame.menu.getMenu(4).setBorderPainted(false);
            Main.frame.menu.getMenu(1).setBorderPainted(true);
            Main.frame.menu.getMenu(0).setBorderPainted(false);
            if (Main.manager) {
                Main.frame.menu.getMenu(2).setBorderPainted(false);
                Main.frame.menu.getMenu(3).setBorderPainted(false);
            }
        } else if (e.getSource() == Main.frame.menu.getMenu(2)) {
            Main.frame.main.bestelling.setVisible(false);
            Main.frame.main.levering.setVisible(false);
            Main.frame.main.werknemers.setVisible(true);
            Main.frame.main.items.setVisible(false);
            Main.frame.main.InfoBestellingen.setVisible(false);
            Main.frame.menu.getMenu(4).setBorderPainted(false);
            Main.frame.menu.getMenu(0).setBorderPainted(false);
            Main.frame.menu.getMenu(1).setBorderPainted(false);
            if (Main.manager) {
                Main.frame.menu.getMenu(2).setBorderPainted(true);
                Main.frame.menu.getMenu(3).setBorderPainted(false);
            }
        } else if (e.getSource() == Main.frame.menu.getMenu(3)) {
            Main.frame.main.bestelling.setVisible(false);
            Main.frame.main.levering.setVisible(false);
            Main.frame.main.werknemers.setVisible(false);
            Main.frame.main.items.setVisible(true);
            Main.frame.main.InfoBestellingen.setVisible(false);
            Main.frame.menu.getMenu(4).setBorderPainted(false);
            Main.frame.menu.getMenu(0).setBorderPainted(false);
            Main.frame.menu.getMenu(1).setBorderPainted(false);
            if (Main.manager) {
                Main.frame.menu.getMenu(2).setBorderPainted(false);
                Main.frame.menu.getMenu(3).setBorderPainted(true);
            }
        } else if (e.getSource() == Main.frame.menu.getMenu(4)) {
            Main.frame.main.bestelling.setVisible(false);
            Main.frame.main.levering.setVisible(false);
            Main.frame.main.werknemers.setVisible(false);
            Main.frame.main.items.setVisible(false);
            Main.frame.main.InfoBestellingen.setVisible(true);
            Main.frame.menu.getMenu(4).setBorderPainted(true);
            Main.frame.menu.getMenu(0).setBorderPainted(false);
            Main.frame.menu.getMenu(1).setBorderPainted(false);
            if (Main.manager) {
                Main.frame.menu.getMenu(2).setBorderPainted(false);
                Main.frame.menu.getMenu(3).setBorderPainted(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
