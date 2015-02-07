/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.friet.net.info.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.friet.net.UI.Table;
import org.friet.net.main.Main;

/**
 *
 * @author arne
 */
public class PanelWerknemers extends JPanel {

    private ArrayList naam;
    private JButton remove, add, edit;
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
        select = new JScrollPane(table);
        select.setMinimumSize(new Dimension(200, 0));

        remove = new JButton("Verwijderen");
        remove.setName("-");
        remove.addActionListener(new Event());
        add = new JButton("Nieuw");
        add.setName("-");
        add.addActionListener(new Event());
        edit = new JButton("Aanpassen");
        edit.setName("-");
        edit.addActionListener(new Event());

        JPanel p1 = new JPanel();

        p1.add(add);
        p1.add(edit);
        p1.add(remove);

        for (Object s : werknemersInfo) {
            Map<String, String> map = (Map<String, String>) s;
            model.addRow(map.values().toArray());
        }

        this.add(p1, BorderLayout.NORTH);
        this.add(select);
    }

    private class Event implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == remove) {

            }

            if (e.getSource() == add) {

            }

            if (e.getSource() == edit) {

                if (editing) {
                    edit.setText("Update werknemer");
                    for (Object o : naam) {
                        JTextField os = (JTextField) o;
                        os.setEditable(editing);
                    }
                } else {
                    String[] s;
                    for (Object o : naam) {
                        JTextField os = (JTextField) o;
                        os.setEditable(editing);
                    }
                    //Main.db.updatewerknemer(, TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, WIDTH);
                    edit.setText("verander de werknemer");
                }
                
                editing = !editing;
            }
        }

    }
}
