/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ButtonBorder implements Border {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (c.isEnabled()){
            g.setColor(UIManager.getColor("Button.borderColor"));
        }else {
            g.setColor(UIManager.getColor("Button.enabledBorderColor"));
        }
        g.drawLine(x, y+height-2,x+ width-1,y+ height-2);
        g.drawLine(x, y+height-1,x+ width-1,y+ height-1);
        
    }

    @Override
    public Insets getBorderInsets(Component c) {
        if (c.getName() == "-") {
            return new Insets(20, 10, 20, 10);
        } else if (c.getName() == "2") {
            return new Insets(10, 10, 10, 10);
        }
        return new Insets(3, 3, 3, 3);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
