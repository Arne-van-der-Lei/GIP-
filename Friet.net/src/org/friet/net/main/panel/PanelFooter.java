package org.friet.net.main.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelFooter extends JPanel {

    public JLabel labelFooterText;

    public PanelFooter() {
        labelFooterText = new JLabel("Gemaakt door Arne van der Lei GIP 2014 - 2015");

        labelFooterText.setVisible(true);

        this.add(labelFooterText);
    }
}
