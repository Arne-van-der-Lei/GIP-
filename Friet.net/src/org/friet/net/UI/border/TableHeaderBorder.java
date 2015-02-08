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
public class TableHeaderBorder implements Border {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        g.translate(x, y);
        g.setColor(UIManager.getColor("TableHeader.borderColor"));
        g.drawRect(0, 0, w - 1, h - 1);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 1, 10, 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
