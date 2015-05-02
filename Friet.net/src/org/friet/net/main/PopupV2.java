/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.friet.net.UI.Button;

/**
 *
 * @author arne
 */
public class PopupV2 extends JFrame {

    private List<String> naamen = Arrays.asList("Naam", "HoeveelheidPerItem", "Prijs", "Inhoud", "Barcode");
    private List<String> naamenInhoud = Arrays.asList("Naam", "Hoeveelheid", "Barcode");
    private List<JTextField> text = new ArrayList<JTextField>();
    private Button button = new Button("OK");
    private String soortnaam;
    private int soort;
    private boolean inhoud;

    public PopupV2(boolean inhoud, int soort, String soortnaam) {
        this.setTitle(Main.config.getString("frmPopup2"));
        this.soort = soort;
        this.soortnaam = soortnaam;
        this.inhoud = inhoud;
        GridLayout g2 = new GridLayout(6, 1);
        g2.setVgap(10);
        JPanel p = new JPanel(g2);
        if (inhoud) {
            for (int i = 0; i < (soort == 1 ? 1 : 3); i++) {
                GridLayout g = new GridLayout(1, 2);
                g.setVgap(10);
                JPanel p2 = new JPanel(g);

                JTextField textfield = new JTextField();
                JLabel label = new JLabel(naamenInhoud.get(i));

                textfield.setName(naamenInhoud.get(i));
                text.add(textfield);

                p2.add(label);
                p2.add(textfield);
                p.add(p2);
            }
        } else {
            for (int i = 0; i < (soort == 1 ? 1 : 5); i++) {
                GridLayout g = new GridLayout(1, 2);
                g.setVgap(10);
                JPanel p2 = new JPanel(g);

                JTextField textfield = new JTextField();
                JLabel label = new JLabel(naamen.get(i));

                textfield.setName(naamen.get(i));
                text.add(textfield);

                p2.add(label);
                p2.add(textfield);
                p.add(p2);
            }
        }
        this.setSize(400, 300);
        button.addActionListener(new Event());
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(button);
        this.add(p);

        this.setVisible(true);
    }

    public void drop() {
        this.setVisible(false);
        this.dispose();
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String error = "";
            if (soort == 3) {
                if (!inhoud) {
                    error = Main.db.addNewItem(text.get(0).getText(), text.get(1).getText(), text.get(2).getText(), text.get(3).getText(), text.get(4).getText(), soortnaam);
                } else {
                    error = Main.db.addNewInhoud(text.get(0).getText(), text.get(1).getText(), text.get(2).getText(), soortnaam);
                }
            } else {
                error = Main.db.addNewSoort(text.get(0).getText());
                error += Main.db.addNewInhoud("Inhoud item", "1", "1", text.get(0).getText());
                error += Main.db.addNewItem("Item", "1", "1", "Inhoud item", "1", text.get(0).getText());
            }
            
            JOptionPane.showMessageDialog(null, error);

            Main.frame.main.Inhoud.refresh();
            Main.frame.main.items.refresh();
            Main.frame.main.bestelling.refresh();
            Main.frame.main.levering.refresh();
            drop();
        }
    }
}
