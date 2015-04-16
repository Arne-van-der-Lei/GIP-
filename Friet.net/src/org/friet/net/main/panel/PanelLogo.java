package org.friet.net.main.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelLogo extends JPanel {

    public BufferedImage logo;

    public PanelLogo() {
        try {
            logo = ImageIO.read(new File("src/res/friet.png"));
        } catch (Exception ex) {
            Logger.getLogger(PanelLogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, 100, 0, 200, 200, null);
    }
}
