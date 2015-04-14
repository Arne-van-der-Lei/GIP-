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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.friet.net.UI.Button;

/**
 *
 * @author arne
 */
public class PopupV2 extends JFrame {

    private List<String> naamen = Arrays.asList("Naam", "HoeveelheidPerItem", "Prijs", "Inhoud");
    private List<String> naamenInhoud = Arrays.asList("Naam", "Hoeveelheid");
    private List<JTextField> text = new ArrayList<JTextField>();
    private Button button = new Button("OK");
    private String soortnaam;
    private int soort;
    private boolean inhoud;

    public PopupV2(boolean inhoud, int soort, String soortnaam) {
        this.soort = soort;
        this.soortnaam = soortnaam;
        this.inhoud = inhoud;
        GridLayout g2 = new GridLayout(5, 1);
        g2.setVgap(10);
        JPanel p = new JPanel(g2);
        if (inhoud) {
            for (int i = 0; i < 2; i++) {
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
            for (int i = 0; i < (soort == 1 ? 1 : 4); i++) {
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
        
        button.addActionListener(new Event());
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(button);
        this.add(p);

        this.setVisible(true);
        if (soort == 1) {
            this.setSize(100, 200);
        } else {
            this.setSize(400, 200);
        }
    }

    public void drop() {
        this.setVisible(false);
        this.dispose();
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (soort == 3) {
                if (!inhoud) {
                    Main.db.addNewItem(text.get(0).getText(), text.get(1).getText(), text.get(2).getText(), text.get(3).getText(), soortnaam);
                } else {
                    Main.db.addNewInhoud(text.get(0).getText(), text.get(1).getText(), soortnaam);
                }
            } else {
                Main.db.addNewSoort(text.get(0).getText());
            }
        }
    }
}
