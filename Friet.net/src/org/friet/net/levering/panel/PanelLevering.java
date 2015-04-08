/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.levering.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Button;
import org.friet.net.UI.NETableModel;
import org.friet.net.UI.Table;
import org.friet.net.UI.icons.Icone;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelLevering extends JPanel {

    public Map<String, Map<String, Float>> items;
    public JScrollPane scroll;
    public Table list;
    public JButton delete, cansel, nieuweKlant, confirm;
    public int selected = -1;
    public JTabbedPane tabs;
    public boolean select;
    public boolean allowsBar = false;

    public PanelLevering() {

        items = new HashMap<String, Map<String, Float>>();
        getItems();
        Event event = new Event();

        this.setLayout(new BorderLayout());
        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(1, 4));

        list = new Table(new NETableModel(new Object[]{"Artikel:", "Aantal"}, 0));
        scroll = new JScrollPane(list);
        p1.add(scroll);

        delete = new Button("");
        delete.setVisible(true);
        delete.addActionListener(event);
        delete.setName("-");
        delete.setIcon(new Icone("Delete"));
        p.add(delete);

        confirm = new Button("");
        confirm.setVisible(true);
        confirm.addActionListener(event);
        confirm.setIcon(new Icone("Multi"));
        p.add(confirm);

        cansel = new Button("");
        cansel.setVisible(true);
        cansel.addActionListener(event);
        cansel.setIcon(new Icone("Cancel"));
        p.add(cansel);

        nieuweKlant = new Button("");
        nieuweKlant.setVisible(true);
        nieuweKlant.addActionListener(event);
        nieuweKlant.setIcon(new Icone("Confirm"));
        p.add(nieuweKlant);

        tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        for (String key : items.keySet()) {

            JPanel panel = new JPanel(false);
            panel.setVisible(true);
            panel.setLayout(new GridLayout(5, 5));

            tabs.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style='font-size:20px'><p>" + key + "<p></body></html>", panel);
            for (String naam : items.get(key).keySet()) {
                Button btn = new Button("<html><body style='font-size:20px'><p>" + naam + "<p></body></html>");

                btn.setVisible(true);
                btn.addActionListener(event);
                Color c = Main.db.randomKleur();
                btn.setBackground(c);
                btn.setC2(c);
                panel.add(btn);
            }

        }
        p1.add(p, BorderLayout.SOUTH);
        this.add(tabs);
        this.add(p1, BorderLayout.EAST);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                allowsBar = true;
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                allowsBar = false;
            }

        });
    }

    protected void getItems() {
        items = Main.db.getInhoud();
    }

    public void barcode(String s) {
        s = (String) Main.db.getnaaml(s);
        if (select) {
            Popup.Create(s);
        } else {
            ((DefaultTableModel) list.getModel()).addRow(new Object[]{s, 1});
        }
        select = false;
    }

    public class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().getClass() == Button.class) {
                if (e.getActionCommand().startsWith("<html><body style='font-size:20px'><p>") && selected == -1) {
                    String[] str = e.getActionCommand().split("<p>");
                    if (select) {
                        Popup.Create(str[1]);
                    } else {
                        ((DefaultTableModel) list.getModel()).addRow(new Object[]{str[1], 1});
                    }
                    select = false;
                }

                if (e.getSource() == confirm) {
                    select = true;
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
                    list.setModel(new DefaultTableModel(new Object[]{"Artikel:", "Aantal"}, 0));
                }

                if (e.getSource() == nieuweKlant) {
                    int bool = JOptionPane.showConfirmDialog(nieuweKlant, "Toevoegen van items?", "Toevoegen", 0);
                    if (bool == 0) {
                        for (Object i : ((DefaultTableModel) list.getModel()).getDataVector().toArray()) {
                            Vector is = (Vector) i;
                            Main.db.addItem((String) is.toArray()[0], (float) Float.parseFloat(is.toArray()[1].toString()));
                        }
                        list.setModel(new DefaultTableModel(new Object[]{"Artikel:", "Aantal"}, 0));
                    }
                }
            }
        }
    }
}
