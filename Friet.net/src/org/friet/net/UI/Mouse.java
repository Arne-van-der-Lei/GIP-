/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author arne
 */
public class Mouse implements MouseListener {
    private JButton b;

    public Mouse(JButton b) {
        this.b = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        b.setBackground(UIManager.getColor("Button.hoverBackground"));
        System.out.println(e.getSource());
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        b.setBackground(UIManager.getColor("Button.-background"));
    }
    
}
