/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.bestelling.panel;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelBestelling extends JPanel {

    public Map<String, Map<String, Float>> items;
    public JScrollPane list;
    public DefaultTableModel model;
    public JTable table;
    public JButton delete, cansel, nieuweKlant;

    public PanelBestelling() {

        items = new HashMap<String, Map<String, Float>>();
        getItems();
        Event event = new Event();

        this.setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(2, 1));
        model = new DefaultTableModel(new Object[]{"Naam:", "Prijs"}, 0);
        table = new JTable(model);
        list = new JScrollPane(table);
        p.add(list);

        JPanel p1 = new JPanel(new GridLayout(3, 1));
        p.add(p1);
        delete = new JButton("Delete");
        delete.setVisible(true);
        delete.addActionListener(event);
        p1.add(delete);

        cansel = new JButton("Cancel");
        cansel.setVisible(true);
        cansel.addActionListener(event);
        p1.add(cansel);

        nieuweKlant = new JButton("Nieuwe klant");
        nieuweKlant.setVisible(true);
        nieuweKlant.addActionListener(event);
        p1.add(nieuweKlant);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        for (String key : items.keySet()) {

            JPanel panel = new JPanel(new GridLayout(5, 5));

            panel.setVisible(true);
            tabs.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style=\"font-size:20px\"><p>" + key + "<p></body></html>", panel);
            int x = -1;
            int y = 0;
            for (String naam : items.get(key).keySet()) {
                x++;
                JButton btn = new JButton("<html><body style=\"font-size:20px\"><p>" + naam + "<p></body></html>");

                btn.setVisible(true);
                btn.addActionListener(event);
                panel.add(btn);

                if (x == 5) {
                    x = -1;
                    y++;
                }
            }

        }
        this.add(p, BorderLayout.EAST);
        this.add(tabs, BorderLayout.CENTER);
    }

    protected void getItems() {
        items = Main.db.getItems();
    }

    public class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().getClass() == JButton.class) {
                if (e.getActionCommand().startsWith("<html><body style=\"font-size:20px\"><p>")) {
                    for (String key : items.keySet()) {
                        for (String naam : items.get(key).keySet()) {
                            if (naam == null ? e.getActionCommand().split("<p>")[1] == null : naam.equals(e.getActionCommand().split("<p>")[1])) {
                                model.addRow(new Object[]{e.getActionCommand().split("<p>")[1], items.get(key).get(naam)});
                            }
                        }
                    }

                }
                if (e.getSource() == delete) {
                    int[] ints = table.getSelectedRows();

                    for (int i : ints) {
                            model.removeRow(i);
                    }
                }

                if (e.getSource() == cansel) {
                    model.setRowCount(0);
                }

                if (e.getSource() == nieuweKlant) {
                    float prijs = 0;
                    for (Object i : model.getDataVector().toArray()) {
                        float i2 = (float) Float.parseFloat(i.toString());
                        prijs += i2;
                    }
                    int bool = JOptionPane.showConfirmDialog(nieuweKlant, prijs, "Einde klant", 0);
                    if (bool == 0) {
                        for (Object i : model.getDataVector().toArray()) {
                            Main.db.verminderItem((String) i);
                        }
                        Main.db.addBestelling(1, Main.Werknemmersnummer, prijs);
                        model.setRowCount(0);
                    }
                }
            }
        }
    }
}
