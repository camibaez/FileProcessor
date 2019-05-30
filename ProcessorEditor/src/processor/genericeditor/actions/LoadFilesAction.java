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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

import processor.core.file.FileMatcher;
import processor.core.file.FileProcessor;
import processor.core.file.Profile;

import processor.genericeditor.ProjectCentral;


public abstract class LoadFilesAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Profile profile = ProjectCentral.instance().getProfile();
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
            File file = fc.getCurrentDirectory();
            profile.setWorkingDirectory(file.getAbsolutePath());
            FileMatcher fileMatcher = new FileMatcher(profile);
            profile.setFileMatcher(fileMatcher);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Files.walkFileTree(Paths.get(profile.getWorkingDirectory()), fileMatcher);
                        ProjectCentral.instance().getProfile().getFileMatcher().setDone(true);
                        System.out.println("Matching done!!!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

            FileProcessor fileProcessor = new FileProcessor(profile, profile.getCleaners());
            profile.setFileProcessor(fileProcessor);
            openProcessorWindow();

        } else {
            Logger.getLogger(LoadFilesAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
    
    public abstract void openProcessorWindow();
    
}
