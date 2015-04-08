/*
 * Copyright 2015 arne van der Lei
 */
package org.friet.net.UI;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arne
 */
public class NETableModel extends DefaultTableModel {

    public NETableModel(Object[] columnNames, int rowCount) {
        super(convertToVector(columnNames), rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
