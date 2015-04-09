/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.friet.net.UI.Button;

/**
 *
 * @author arne
 */
public class PopupV2 extends JFrame {

    private List<String> naamen = Arrays.asList("Naam", "HoeveelheidPerItem", "Prijs");
    private List<JTextField> text = new ArrayList<JTextField>();
    private Button button = new Button("OK");

    public PopupV2(int soort) {

        for (int i = 0; i < (soort == 1 ? 1 : 3); i++) {
            JTextField textfield = new JTextField();
            JLabel label = new JLabel(naamen.get(i));

            textfield.setName(naamen.get(i));

        }
        //Main.db.addNewItem(prijs, hoeveelheidPerItem, naam);
    }
}
