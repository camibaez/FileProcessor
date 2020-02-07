/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import processor.core.file.ProcessingResult;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;
import processor.core.graph.GraphNode;
import processor.profile.DIEmulator;

/**
 *
 * @author cbaez
 */
public class FilesTableModel extends DefaultTableModel{
    Profile profile = DIEmulator.getProfile();
    
    Class[] types = new Class[]{
        java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class};
    boolean[] canEdit = new boolean[]{
        true, false, false, 
    };

    public FilesTableModel() {
        super(
                new Object[][]{},
                new String[]{
                    "Active", "File", "Cleaners"
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
        
        if (columnIndex == 0) {
            String path = (String) getValueAt(rowIndex,1);
            Profile p  = profile;
            ProcessingResult result = p.getFileCentral().getResultMap().get(path);
            
            result.setActive((boolean) aValue);
            ((Vector)dataVector.get(rowIndex)).set(columnIndex, aValue);
        }
    }
    
   
}
