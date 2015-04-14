/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.info.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Button;
import org.friet.net.UI.Table;
import org.friet.net.main.Main;
import org.friet.net.main.PopupV2;

/**
 *
 * @author arne
 */
public class PanelInhoud extends JPanel {

    private JTabbedPane soorten;
    private JPanel p3;
    public JScrollPane scroll;
    public Table list;
    public Map<String, Object> items;

    public PanelInhoud() {
        this.setLayout(new BorderLayout());
        items = new HashMap<String, Object>();
        soorten = new JTabbedPane();

        list = new Table(new DefaultTableModel(new Object[]{"", ""}, 0));
        scroll = new JScrollPane(list);

        refresh();

        this.add(scroll, BorderLayout.EAST);
        this.add(soorten);

    }

    public void refresh() {
        items = Main.db.getInfoInhoud();
        soorten.removeAll();

        for (Object soortt : items.keySet()) {
            Map<String, Object> soort = (Map<String, Object>) items.get(soortt);
            JPanel p = new JPanel(new GridLayout(5, 5));
            for (Object itemm : soort.values()) {
                if (itemm.getClass() != String.class) {
                    Map<String, Object> item = (Map<String, Object>) itemm;
                    Button button = new Button(item.get("naam") + "");
                    button.addActionListener(new Event());
                    Color c = Main.db.randomKleur();
                    button.setBackground(c);
                    button.setC2(c);
                    button.setName(item.get("naam") + "");
                    p.add(button);
                }
            }

            Button button = new Button("+");
            button.addActionListener(new Event());
            Color c = Main.db.randomKleur();
            button.setBackground(c);
            button.setC2(c);
            button.setName("add " + soort.get("naam"));
            p.add(button);

            p.setName("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>" + soort.get("naam") + "<p></body></html>");
            soorten.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>" + soort.get("naam") + "<p></body></html>", p);
        }

        JPanel p = new JPanel(new GridLayout(5, 5));
        p.setName("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>+</p></body></html>");
        soorten.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>+<p></body></html>", p);
        soorten.addAncestorListener(new Event2());
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().getClass() == Button.class) {
                Button b = (Button) e.getSource();
                System.out.println(b.getName());
                if (b.getName().startsWith("add")) {
                    PopupV2 popup = new PopupV2(true, 2, b.getName().split(" ")[1]);
                } else {
                    ((DefaultTableModel) list.getModel()).setRowCount(0);
                    Map<String, String> map = (Map<String, String>) ((Map<String, Object>) items.get(soorten.getSelectedComponent().getName().split("<p>")[1])).get(((JButton) e.getSource()).getText());
                    System.out.println(((JButton) e.getSource()).getText());
                    for (String s : map.keySet()) {
                        ((DefaultTableModel) list.getModel()).addRow(new Object[]{s, map.get(s)});
                    }
                }
            }
        }
    }

    private class Event2 implements AncestorListener {

        @Override
        public void ancestorAdded(AncestorEvent event) {
            System.out.println(event.getComponent().getName());
            if (event.getComponent().getName().split("<p>")[1].equals("+")) {
                PopupV2 popup = new PopupV2(true, 1, " ");
            }
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
        }
    }
}
