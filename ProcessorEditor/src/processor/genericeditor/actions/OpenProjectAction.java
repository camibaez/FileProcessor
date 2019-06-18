/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import processor.core.file.ProjectCentral;
import processor.profile.ProfileAdministration;

public abstract class OpenProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace"));
        fc.setDialogTitle("Open project");
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ProjectCentral.instance().setProfile(ProfileAdministration.loadProject(file));
            ProjectCentral.instance().setProfileFile(file);
            openProjectWindow();
            
        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
    
    public abstract void openProjectWindow();
}
