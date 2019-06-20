/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import processor.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public class NodesTableModel extends DefaultTableModel {

    Class[] types = new Class[]{
        java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
    };
    boolean[] canEdit = new boolean[]{
        true, false, false, true, true, true
    };

    public NodesTableModel() {
        super(
                new Object[][]{},
                new String[]{
                    "Active", "Node", "Type", "In", "True Out", "False Out"
                }
        );
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        GraphNode node = (GraphNode) getValueAt(rowIndex,1);
        if (columnIndex == 0) {
            node.setActive((boolean) aValue);
            ((Vector)dataVector.get(rowIndex)).set(columnIndex, aValue);
        }
    }
}
