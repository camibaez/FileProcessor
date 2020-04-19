/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.profile.DIEmulator;
import com.datapro.nfp.profile.ProfileSerializer;

public abstract class OpenProjectAction implements ActionListener {
    ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        //fc.setCurrentDirectory(new java.io.File("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace"));
        fc.setDialogTitle("Open project");
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            projectCentral.setProfile(ProfileSerializer.loadProject(file));
            projectCentral.setProfileFile(file);
            openProjectWindow();
        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
    
    public abstract void openProjectWindow();
}
