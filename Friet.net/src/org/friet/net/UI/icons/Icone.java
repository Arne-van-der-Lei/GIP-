/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author arne
 */
public class Icone implements Icon {

    private BufferedImage image;

    public Icone(String icon) {
        try {
            image = ImageIO.read(new File("src/res/" + icon + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(image, x, y, 64, 64, c);
    }

    @Override
    public int getIconWidth() {
        return 64;
    }

    @Override
    public int getIconHeight() {
        return 64;
    }

}
