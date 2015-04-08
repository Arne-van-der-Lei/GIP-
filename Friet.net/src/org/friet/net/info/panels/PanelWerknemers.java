/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.info.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Button;
import org.friet.net.UI.Table;
import org.friet.net.UI.UI;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelWerknemers extends JPanel {

    private ArrayList naam;
    private Button remove, add, edit;
    private JScrollPane select;
    private DefaultTableModel model;
    private JTable table;
    private ArrayList werknemersInfo;
    private boolean editing;

    public PanelWerknemers() {
        this.setLayout(new BorderLayout());

        naam = new ArrayList();
        werknemersInfo = Main.db.getwerknemers();

        model = new DefaultTableModel(((Map<String, String>) werknemersInfo.get(0)).keySet().toArray(), 0);
        table = new Table(model);
        table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(20000);
        table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(90);
        select = new JScrollPane(table);
        select.setMinimumSize(new Dimension(200, 0));

        remove = new Button("Verwijderen");
        remove.setName("2");
        remove.addActionListener(new Event());
        remove.setBackground(UI.geel);
        remove.setC2(UI.geel);
        remove.setForeground(Color.black);

        add = new Button("Nieuw");
        add.setName("2");
        add.addActionListener(new Event());
        add.setBackground(UI.geel);
        add.setC2(UI.geel);
        add.setForeground(Color.black);

        edit = new Button("Opslaan");
        edit.setName("2");
        edit.addActionListener(new Event());
        edit.setBackground(UI.geel);
        edit.setC2(UI.geel);
        edit.setForeground(Color.black);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        GridLayout g = new GridLayout(1, 3);
        g.setHgap(10);
        JPanel p2 = new JPanel(g);
        p2.add(add);
        p2.add(edit);
        p2.add(remove);
        p1.add(p2);
        for (Object s : werknemersInfo) {
            TreeMap<String, String> map = (TreeMap<String, String>) s;
            model.addRow(map.values().toArray());
        }

        this.add(p1, BorderLayout.NORTH);
        this.add(select);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                model.setRowCount(0);
                werknemersInfo = Main.db.getwerknemers();
                for (Object s : werknemersInfo) {
                    TreeMap<String, String> map = (TreeMap<String, String>) s;
                    model.addRow(map.values().toArray());
                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }

        });
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == remove) {
                Main.db.delwerknemer(new Integer(model.getValueAt(table.getSelectedRow(), 0).toString()));
                model.removeRow(table.getSelectedRow());

            }

            if (e.getSource() == add) {
                model.addRow(new Object[model.getColumnCount()]);
            }

            if (e.getSource() == edit) {
                for (Object v : model.getDataVector().toArray()) {
                    Vector vv = (Vector) v;
                    if (vv.get(0) != null) {
                        Main.db.updatewerknemer(
                                vv.get(2) + "",
                                vv.get(1) + "",
                                vv.get(3) + "",
                                vv.get(5) + "",
                                vv.get(4) + "",
                                vv.get(7) + "",
                                vv.get(6) + "",
                                new Integer(vv.get(0).toString()));

                    } else {
                        Main.db.addwerknemer(
                                vv.get(2) + "",
                                vv.get(1) + "",
                                vv.get(3) + "",
                                vv.get(5) + "",
                                vv.get(4) + "",
                                vv.get(7) + "",
                                JOptionPane.showInputDialog("Geef het nieuwe passwoord in van " + vv.get(2)),
                                vv.get(6) + "");
                    }
                }
                werknemersInfo = Main.db.getwerknemers();
                model.setRowCount(0);
                for (Object s : werknemersInfo) {
                    TreeMap<String, String> map = (TreeMap<String, String>) s;
                    model.addRow(map.values().toArray());
                }
            }
        }

    }
}
