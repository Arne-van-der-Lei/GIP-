/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.info.panels;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.NETableModel;
import org.friet.net.UI.Table;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelBestellingen extends JPanel {

    private JPanel p3;
    public JScrollPane scroll;
    public Table list;
    public Map<String, Object> items;

    public PanelBestellingen() {
        this.setLayout(new BorderLayout());
        items = new HashMap<String, Object>();
        items = Main.db.getBestellingen();
        DefaultTableModel model = new NETableModel(new Object[]{Main.config.getString("tblInfoVerkoopsDatum"), Main.config.getString("tblInfoPrijs"), Main.config.getString("tblInfoNaam")}, 0);
        list = new Table(model);
        scroll = new JScrollPane(list);
        for (Object s : items.values()) {
            Map<String, String> map = (Map<String, String>) s;
            model.addRow(map.values().toArray());
        }
        this.add(scroll);
    }

    public void refresh() {
        items = new HashMap<String, Object>();
        items = Main.db.getBestellingen();
        DefaultTableModel model = new NETableModel(new Object[]{Main.config.getString("tblInfoVerkoopsDatum"), Main.config.getString("tblInfoPrijs"), Main.config.getString("tblInfoNaam")}, 0);
        list = new Table(model);
        scroll = new JScrollPane(list);
        for (Object s : items.values()) {
            Map<String, String> map = (Map<String, String>) s;
            model.addRow(map.values().toArray());
        }
    }
}
