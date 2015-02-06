/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.bestelling.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelBestelling extends JPanel {

    public Map<String, Map<String, Float>> items;
    public JList list;
    public DefaultListModel listModel;
    public JList listprijs;
    public DefaultListModel listModelprijs;
    public JButton delete;
    public JButton cansel;
    public JButton nieuweKlant;

    public PanelBestelling() {

        items = new HashMap<String, Map<String, Float>>();
        getItems();
        Event event = new Event();

        this.setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        list = new JList(listModel);

        this.add(list);
        listModelprijs = new DefaultListModel();
        listprijs = new JList(listModelprijs);
        this.add(listprijs);

        delete = new JButton("Delete");
        delete.setVisible(true);
        delete.addActionListener(event);
        this.add(delete);

        cansel = new JButton("Cancel");
        cansel.setVisible(true);
        cansel.addActionListener(event);
        this.add(cansel);

        nieuweKlant = new JButton("Nieuwe klant");
        nieuweKlant.setVisible(true);
        nieuweKlant.addActionListener(event);
        this.add(nieuweKlant);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        for (String key : items.keySet()) {

            JPanel panel = new JPanel(false);

            panel.setVisible(true);
            panel.setLayout(null);
            tabs.addTab("<html><body leftmargin=20 topmargin=12 marginwidth=20 marginheight=8 style=\"font-size:20px\"><p>" + key + "<p></body></html>", panel);
            int x = -1;
            int y = 0;
            for (String naam : items.get(key).keySet()) {
                x++;
                JButton btn = new JButton("<html><body style=\"font-size:20px\"><p>" + naam + "<p></body></html>");

                btn.setBounds(x * panel.getWidth() / 5, y * panel.getHeight() / 5, panel.getWidth() / 5, panel.getHeight() / 5);
                btn.setVisible(true);
                btn.addActionListener(event);
                panel.add(btn);

                if (x == 5) {
                    x = -1;
                    y++;
                }
            }

        }

        this.add(tabs);
    }

    protected void getItems() {
        items = Main.db.getItems();
    }

    public class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().getClass() == JButton.class) {
                if (e.getActionCommand().startsWith("<html><body style=\"font-size:20px\"><p>")) {
                    listModel.addElement(e.getActionCommand().split("<p>")[1]);
                    for (String key : items.keySet()) {
                        for (String naam : items.get(key).keySet()) {
                            if (naam == null ? e.getActionCommand().split("<p>")[1] == null : naam.equals(e.getActionCommand().split("<p>")[1])) {
                                listModelprijs.addElement(items.get(key).get(naam));
                            }
                        }
                    }

                }
                if (e.getSource() == delete) {
                    ListSelectionModel sm = list.getSelectionModel();
                    int[] ints = list.getSelectedIndices();

                    for (int i : ints) {
                        if (sm.isSelectedIndex(i)) {
                            listModel.remove(i);
                            listModelprijs.remove(i);
                        }
                    }
                }

                if (e.getSource() == cansel) {
                    listModel.clear();
                    listModelprijs.clear();
                }

                if (e.getSource() == nieuweKlant) {
                    float prijs = 0;
                    for (Object i : listModelprijs.toArray()) {
                        float i2 = (float) Float.parseFloat(i.toString());
                        prijs += i2;
                    }
                    int bool = JOptionPane.showConfirmDialog(nieuweKlant, prijs, "Einde klant", 0);
                    if (bool == 0) {
                        for (Object i : listModel.toArray()) {
                            Main.db.verminderItem((String) i);
                        }
                        Main.db.addBestelling(1, Main.Werknemmersnummer, prijs);
                        listModel.clear();
                        listModelprijs.clear();
                    }
                }
            }
        }
    }
}
