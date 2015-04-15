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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Button;
import org.friet.net.UI.Table;
import org.friet.net.main.Main;
import org.friet.net.main.PopupV2;

/**
 *
 * @author arne
 */
public class PanelItems extends JPanel {

    private JTabbedPane soorten;
    private JPanel p3;
    public JScrollPane scroll;
    public Button remove, Save;
    public Table list;
    public Map<String, Object> items;
    private boolean item;
    private String currentSelected = "";

    public PanelItems(boolean item) {
        this.item = item;
        this.setLayout(new BorderLayout());
        items = new HashMap<String, Object>();
        soorten = new JTabbedPane();
        soorten.setName("ok");

        list = new Table(new DefaultTableModel(new Object[]{"", ""}, 0));
        scroll = new JScrollPane(list);
        JPanel p3 = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel(new GridLayout(1, 2));

        remove = new Button("Remove");
        remove.addActionListener(new Event3());
        
        Save = new Button("Save");
        Save.addActionListener(new Event4());
        refresh();

        p3.add(scroll);
        p3.add(p2, BorderLayout.SOUTH);
        p2.add(remove);
        p2.add(Save);
        this.add(p3, BorderLayout.EAST);
        this.add(soorten);
    }

    public void refresh() {
        if (soorten.getName().equals("soort")) {
            this.remove(soorten);
        }
        if (item) {
            items = Main.db.getInfoItems();
        } else {
            items = Main.db.getInfoInhoud();
        }
        soorten = new JTabbedPane();

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
        p.setName("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>+<p></body></html>");
        soorten.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>+<p></body></html>", p);
        soorten.addChangeListener(new Event2());
        soorten.setName("soort");
        this.add(soorten);
        this.revalidate();
        this.repaint();
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().getClass() == Button.class) {
                Button b = (Button) e.getSource();
                if (b.getName().startsWith("add")) {
                    PopupV2 popup = new PopupV2(!item, 3, b.getName().split(" ")[1]);
                } else {
                    ((DefaultTableModel) list.getModel()).setRowCount(0);
                    Map<String, String> map = (Map<String, String>) ((Map<String, Object>) items.get(soorten.getSelectedComponent().toString().split("<p>")[1])).get(((JButton) e.getSource()).getText());
                    System.out.println(((JButton) e.getSource()).getText());
                    for (String s : map.keySet()) {
                        ((DefaultTableModel) list.getModel()).addRow(new Object[]{s, map.get(s).replace('.', ',')});
                    }
                    currentSelected = map.get("naam");
                }
            }
        }
    }

    private class Event2 implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            String name = soorten.getComponentAt(soorten.getSelectedIndex()).getName();
            if (name.split("<p>")[1].equals("+")) {
                PopupV2 popup = new PopupV2(true, 1, " ");
            }
            ((DefaultTableModel) list.getModel()).setRowCount(0);
            ((DefaultTableModel) list.getModel()).addRow(new Object[]{"Naam", name.split("<p>")[1]});
            currentSelected = name.split("<p>")[1];
        }
    }

    private class Event3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Error;
            String Naam = ((DefaultTableModel) list.getModel()).getValueAt(list.getModel().getRowCount() - 1, 1).toString();
            int result = JOptionPane.showConfirmDialog(null, "Wilt u " + Naam + " verwijderen? hiermee verwijderd u ook alle onderliggende items en inhoud.", "Remove", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (((DefaultTableModel) list.getModel()).getRowCount() == 1) {
                    Error = Main.db.removeSoort(Naam);
                } else if (((DefaultTableModel) list.getModel()).getRowCount() == 2) {
                    Error = Main.db.removeInhoud(Naam);
                } else {
                    Error = Main.db.removeItem(Naam);
                }
                JOptionPane.showMessageDialog(null, Error);
            }
            Main.frame.main.Inhoud.refresh();
            Main.frame.main.items.refresh();
            Main.frame.main.bestelling.refresh();
            Main.frame.main.levering.refresh();
        }
    }

    private class Event4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String Error;
            String NaamNieuw = ((DefaultTableModel) list.getModel()).getValueAt(list.getModel().getRowCount() - 1, 1).toString();
            int result = JOptionPane.showConfirmDialog(null, "Wilt u " + currentSelected + " opslaan?", "Save", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (((DefaultTableModel) list.getModel()).getRowCount() == 1) {
                    Error = Main.db.updateSoort(currentSelected, NaamNieuw);
                } else if (((DefaultTableModel) list.getModel()).getRowCount() == 2) {
                    String HoeveelHeidNieuw = ((DefaultTableModel) list.getModel()).getValueAt(list.getModel().getRowCount() - 2, 1).toString();
                    Error = Main.db.updateInhoud(currentSelected, NaamNieuw, HoeveelHeidNieuw);
                } else {
                    String HoeveelheidPerItem = ((DefaultTableModel) list.getModel()).getValueAt(list.getModel().getRowCount() - 2, 1).toString();
                    String PrijsPerItem = ((DefaultTableModel) list.getModel()).getValueAt(list.getModel().getRowCount() - 3, 1).toString();
                    Error = Main.db.updateItem(currentSelected, NaamNieuw, HoeveelheidPerItem, PrijsPerItem);
                }
                JOptionPane.showMessageDialog(null, Error);
            }
            Main.frame.main.Inhoud.refresh();
            Main.frame.main.items.refresh();
            Main.frame.main.bestelling.refresh();
            Main.frame.main.levering.refresh();
        }
    }
}
