/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author arne
 */
public class Button extends JButton implements MouseListener {
    public Color c;
    public Color c2;
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void setC2(Color c2) {
        this.c2 = c2;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(c);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(c2);
    }
    
    public Button(String s) {
        super(s);
        this.c = new Color(UIManager.getColor("Button.hoverBackground").getRGB());
        this.addMouseListener(this);
    }
}
