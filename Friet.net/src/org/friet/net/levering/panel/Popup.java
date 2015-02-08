/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.levering.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Button;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class Popup extends JFrame {

    private JButton[] buttons;
    private JButton ok;
    private JTextField ipfield;
    private String str;

    public Popup(String ok) {
        this.str = ok;
        GridLayout g = new GridLayout(6, 1);
        g.setVgap(5);
        JPanel p = new JPanel(g);

        ipfield = new JTextField();
        p.add(ipfield);
        GridLayout g2 = new GridLayout(1, 3);
        g2.setHgap(5);
        buttons = new Button[12];
        int x = 9;
        for (int i = 1; i <= 3; i++) {
            JPanel p1 = new JPanel(g2);
            for (int j = 1; j <= 3; j++) {

                JButton b = new Button(x + "");
                b.addActionListener(new Event());
                p1.add(b);
                buttons[9 - x] = b;
                x--;
            }
            p.add(p1);
        }
        JPanel p1 = new JPanel(g2);

        JButton b = new Button("0");
        b.addActionListener(new Event());
        buttons[9] = b;
        p1.add(b);
        JButton b1 = new Button("00");
        b1.addActionListener(new Event());
        buttons[10] = b1;
        p1.add(b1);
        JButton b2 = new Button("C");
        b2.addActionListener(new Event());
        buttons[11] = b2;
        p1.add(b2);
        p.add(p1);
        this.ok = new Button("OK");
        this.ok.addActionListener(new Event());
        p.add(this.ok);
        this.add(p);
        this.setVisible(true);
        this.setSize(500, 500);
    }

    public void disp() {
        this.dispose();
    }

    public static void Create(String ok) {

        Popup p = new Popup(ok);
    }

    public class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton b : buttons) {
                if (e.getSource() == b) {
                    if (b.getText() == "C") {
                        ipfield.setText("");
                    } else {
                        ipfield.setText(ipfield.getText() + b.getText());
                    }
                }
            }

            if (e.getSource() == ok) {
                ((DefaultTableModel) Main.frame.main.levering.list.getModel()).addRow(new Object[]{str, ipfield.getText()});
                disp();
            }
        }
    }
}
