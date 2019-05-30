/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import processor.genericeditor.ProjectCentral;
import processor.editor.windows.ProjectEditorTopComponent;
import processor.profile.ProfileAdministration;


public abstract class NewProjectAcion implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select working directory");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File currentDirectory = fc.getCurrentDirectory();
            String name = new SimpleDateFormat("yyMMdd_hhmmss").format(new Date()) + ".json";
            File file = new File(currentDirectory, name);

            try {
                if (file.createNewFile()) {
                    ProfileAdministration.createEmptyProject(file.getAbsolutePath());
                    ProjectCentral.instance().setProfile(ProfileAdministration.loadProject(file.getAbsolutePath()));
                    ProjectCentral.instance().setProfileFile(file);
                    openProjectEditorWindow();
                    
                }
            } catch (IOException ex) {
               ex.printStackTrace();
            }

        }
    }
    
    public abstract void openProjectEditorWindow();
    
   
}