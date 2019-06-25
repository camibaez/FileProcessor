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
import processor.profile.FilesLog;
import processor.profile.LogSerializer;
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
        fc.setCurrentDirectory(new java.io.File("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace"));
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FilesLog log = (FilesLog) LogSerializer.readLog(file.toString());
            openProjectWindow(log);
            
        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }

    public abstract void openProjectWindow(FilesLog log);

}
