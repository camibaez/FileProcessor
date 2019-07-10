/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import processor.core.file.ProjectCentral;
import processor.core.file.VariableHolder;
import processor.profile.log.FilesLog;
import processor.profile.log.LogSerializer;
import processor.profile.ProfileSerializer;

/**
 *
 * @author cbaez
 */
public abstract class LoadLogAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open project");
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Map<String, Object> logData = LogSerializer.readLog(file.toString());
            
            
            FilesLog log = (FilesLog) logData.get("filesLog");
            VariableHolder data = (VariableHolder) logData.get("data");
            openProjectWindow(log, data);
            
        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }

    public abstract void openProjectWindow(FilesLog log, VariableHolder data);

}
