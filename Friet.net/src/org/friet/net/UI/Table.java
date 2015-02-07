/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author arne
 */
public class Table extends JTable {

    public Table(TableModel model) {
        super(model);
        this.setRowHeight(50);
    }
}
