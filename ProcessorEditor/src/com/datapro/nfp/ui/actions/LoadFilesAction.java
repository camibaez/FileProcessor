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
import com.datapro.nfp.core.file.Profile;
import com.datapro.nfp.profile.DIEmulator;


public abstract class LoadFilesAction implements ActionListener {
    Profile profile = DIEmulator.getProfile();
    @Override
    public void actionPerformed(ActionEvent e) {
        if (profile == null) {
            return;
        }
        final JFileChooser fc = new JFileChooser();
        String dir = profile.getLastWorkingDirectory();
        
        if (dir != null && !dir.isEmpty()) {
            fc.setCurrentDirectory(new File(dir));
        }
        fc.setDialogTitle("Select working directory");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            File file = fc.getSelectedFile();
            profile.setWorkingDirectory(file.getAbsolutePath());

            /*
            FileWalker fileMatcher = new FileWalker(profile);
            profile.setFileWalker(fileMatcher);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Matching Start");
                        Files.walkFileTree(Paths.get(profile.getWorkingDirectory()), fileMatcher);
                        profile.getFileWalker().setDone(true);
                        System.out.println("Matching done!!!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
            */
            //FileProcessor fileProcessor = new FileProcessor(profile, profile.getCleaners());
            //profile.setFileProcessor(fileProcessor);
            openProcessorWindow();

        } else {
            Logger.getLogger(LoadFilesAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
    
    public abstract void openProcessorWindow();
    
}
