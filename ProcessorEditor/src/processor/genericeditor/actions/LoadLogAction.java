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
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.core.file.VariableHolder;
import com.datapro.nfp.profile.log.FilesLog;
import com.datapro.nfp.profile.log.LogSerializer;
import com.datapro.nfp.profile.ProfileSerializer;

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
