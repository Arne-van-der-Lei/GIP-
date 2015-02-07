/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

/**
 *
 * @author arne
 */
public class MenuBorder implements Border {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

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
