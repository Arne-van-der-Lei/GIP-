package org.friet.net.main.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.friet.net.bestelling.panel.PanelBestelling;
import org.friet.net.info.panels.PanelBestellingen;
import org.friet.net.info.panels.PanelInhoud;
import org.friet.net.info.panels.PanelItems;
import org.friet.net.info.panels.PanelWerknemers;
import org.friet.net.levering.panel.PanelLevering;

/**
 *
 * @author arne
 */
public class MainPanel extends JPanel {

    private BufferedImage image;

    public PanelFooter footer;
    public PanelBestelling bestelling;
    public PanelLevering levering;
    public PanelWerknemers werknemers;
    public PanelBestellingen InfoBestellingen;
    public PanelItems items;
    public PanelInhoud Inhoud;
    public JPanel centerPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());
        try {
            this.image = ImageIO.read(new File("src/res/friet.png"));
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        centerPanel = new JPanel(new CardLayout());
        footer = new PanelFooter();
        bestelling = new PanelBestelling();
        levering = new PanelLevering();
        werknemers = new PanelWerknemers();
        InfoBestellingen = new PanelBestellingen();
        items = new PanelItems();
        Inhoud = new PanelInhoud();

        levering.setVisible(false);
        werknemers.setVisible(false);
        bestelling.setVisible(false);
        InfoBestellingen.setVisible(false);
        levering.setVisible(false);
        Inhoud.setVisible(false);
        footer.setVisible(true);

        centerPanel.add(bestelling, "1");
        centerPanel.add(levering, "2");
        centerPanel.add(werknemers, "3");
        centerPanel.add(items, "4");
        centerPanel.add(InfoBestellingen, "5");
        centerPanel.add(Inhoud, "6");

        this.add(centerPanel);
        this.add(footer, BorderLayout.SOUTH);

        bestelling.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, this.getWidth() / 2 - image.getWidth() / 2, this.getHeight() / 2 - image.getHeight() / 2, this);

    }
}
