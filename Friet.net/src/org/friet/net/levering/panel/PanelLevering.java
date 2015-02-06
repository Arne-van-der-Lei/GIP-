/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.levering.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.friet.net.main.*;

/**
 *
 * @author arne
 */
public class PanelLevering extends JPanel {

    public Map<String, Map<String, Float>> items;
    public JScrollPane scroll;
    public JTable list;
    public JButton delete, cansel, nieuweKlant, add1, add10, add100, confirm;
    public int selected = -1;
    public JTabbedPane tabs;

    public PanelLevering() {

        items = new HashMap<String, Map<String, Float>>();
        getItems();
        Event event = new Event();

        this.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel(new GridLayout(2,1));
        JPanel p = new JPanel(new GridLayout(7,1));
        p.setMaximumSize(new Dimension(50,2000));
        
        list = new JTable(new DefaultTableModel(new Object[]{"Wat:", "Hoevelheid"}, 0));
        scroll = new JScrollPane(list);
        p1.add(scroll);

        delete = new JButton("Delete");
        delete.setVisible(true);
        delete.addActionListener(event);
        p.add(delete,BorderLayout.EAST);

        cansel = new JButton("Cancel");
        cansel.setVisible(true);
        cansel.addActionListener(event);
        p.add(cansel,BorderLayout.EAST);

        nieuweKlant = new JButton("Toevoegen");
        nieuweKlant.setVisible(true);
        nieuweKlant.addActionListener(event);
        p.add(nieuweKlant,BorderLayout.EAST);

        add1 = new JButton("1");
        add1.setVisible(true);
        add1.addActionListener(event);
        p.add(add1,BorderLayout.EAST);
        add10 = new JButton("10");
        add10.setVisible(true);
        add10.addActionListener(event);
        p.add(add10,BorderLayout.EAST);
        add100 = new JButton("100");
        add100.setVisible(true);
        add100.addActionListener(event);
        p.add(add100,BorderLayout.EAST);
        confirm = new JButton("confirm");
        confirm.setVisible(true);
        confirm.addActionListener(event);
        p.add(confirm,BorderLayout.EAST);
        
        
        tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        for (String key : items.keySet()) {

            JPanel panel = new JPanel(false);
            panel.setVisible(true);
            panel.setLayout(new GridLayout(5,5));
            
            tabs.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>" + key + "<p></body></html>", panel);
            for (String naam : items.get(key).keySet()) {
                JButton btn = new JButton("<html><body style='font-size:20px'><p>" + naam + "<p></body></html>");

                btn.setVisible(true);
                btn.addActionListener(event);
                panel.add(btn);
            }

        }
        p1.add(p);
        this.toggleKeypad();
        this.add(tabs);
        this.add(p1,BorderLayout.EAST);
    }

    protected void getItems() {
        items = Main.db.getInhoud();
    }

    public void toggleKeypad() {
        if (add1.isEnabled()) {
            add1.setEnabled(false);
            add10.setEnabled(false);
            add100.setEnabled(false);
            confirm.setEnabled(false);
            tabs.setEnabled(true);
            delete.setEnabled(true);
            nieuweKlant.setEnabled(true);
            cansel.setEnabled(true);
            for (Component c : tabs.getComponents()) {
                if (c.getClass() == JPanel.class) {
                    JPanel p = (JPanel) c;
                    for (Component co : p.getComponents()) {
                        co.setEnabled(true);
                    }
                }
                c.setEnabled(true);
            }
        } else {
            add1.setEnabled(true);
            add10.setEnabled(true);
            add100.setEnabled(true);
            confirm.setEnabled(true);
            tabs.setEnabled(false);
            delete.setEnabled(false);
            nieuweKlant.setEnabled(false);
            cansel.setEnabled(false);
            for (Component c : tabs.getComponents()) {
                if (c.getClass() == JPanel.class) {
                    JPanel p = (JPanel) c;
                    for (Component co : p.getComponents()) {
                        co.setEnabled(false);
                    }
                }
                c.setEnabled(false);
            }
        }
    }

    public class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().getClass() == JButton.class) {
                if (e.getActionCommand().startsWith("<html><body style='font-size:20px'><p>") && selected == -1) {
                    String[] str = e.getActionCommand().split("<p>");
                    ((DefaultTableModel) list.getModel()).addRow(new Object[]{str[1], 0});
                    selected = ((DefaultTableModel) list.getModel()).getRowCount() - 1;
                    toggleKeypad();
                }
                if (e.getSource() == add1) {
                    ((DefaultTableModel) list.getModel()).setValueAt((int) Integer.parseInt(((DefaultTableModel) list.getModel()).getValueAt(selected, 1).toString()) + 1, selected, 1);
                }
                if (e.getSource() == add10) {
                    ((DefaultTableModel) list.getModel()).setValueAt((int) Integer.parseInt(((DefaultTableModel) list.getModel()).getValueAt(selected, 1).toString()) + 10, selected, 1);
                }
                if (e.getSource() == add100) {
                    ((DefaultTableModel) list.getModel()).setValueAt((int) Integer.parseInt(((DefaultTableModel) list.getModel()).getValueAt(selected, 1).toString()) + 100, selected, 1);
                }
                if (e.getSource() == confirm) {
                    toggleKeypad();
                    selected = -1;
                }

                if (e.getSource() == delete) {
                    ListSelectionModel sm = list.getSelectionModel();
                    int[] ints = list.getSelectedRows();

                    for (int i : ints) {
                        if (sm.isSelectedIndex(i)) {
                            ((DefaultTableModel) list.getModel()).removeRow(i);
                        }
                    }
                }

                if (e.getSource() == cansel) {
                    list.setModel(new DefaultTableModel(new Object[]{"Wat:", "Hoeveelheid"}, 0));
                }

                if (e.getSource() == nieuweKlant) {
                    int bool = JOptionPane.showConfirmDialog(nieuweKlant, "Toevoegen van items?", "Toevoegen", 0);
                    if (bool == 0) {
                        for (Object i : ((DefaultTableModel) list.getModel()).getDataVector().toArray()) {
                            Vector is = (Vector) i;
                            Main.db.addItem((String) is.toArray()[0], (float) Float.parseFloat(is.toArray()[1].toString()));

                        }
                        list.setModel(new DefaultTableModel(new Object[]{"Wat:", "Hoeveelheid"}, 0));
                    }
                }
            }
        }
    }
}
