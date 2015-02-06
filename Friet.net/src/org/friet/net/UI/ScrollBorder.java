/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package org.friet.net.UI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 *
 * @author arne
 */
public class ScrollBorder implements Border {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(UIManager.getColor("ScrollPane.borderColor"));

        g.drawLine(x, y + height - 2, x + width - 1, y + height - 2);
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);

    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1, 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
