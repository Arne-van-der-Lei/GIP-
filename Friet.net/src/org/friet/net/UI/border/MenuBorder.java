/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 *
 * @author arne
 */
public class MenuBorder implements Border {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(UIManager.getColor("Menu.borderColor"));
        g.drawRect(x , y - 1, width - 1, height - 1);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
