/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import processor.core.file.FileMatcher;
import processor.core.file.FileProcessor;
import processor.core.file.Profile;
import processor.editor.ProjectCentral;

@ActionID(
        category = "File",
        id = "processor.editor.actions.LoadMatcher"
)
@ActionRegistration(
        displayName = "#CTL_LoadMatcher"
)
@ActionReferences({
    @ActionReference(path = "Menu/Run", position = 1200),
    @ActionReference(path = "Shortcuts", name = "D-L")
})
@Messages("CTL_LoadMatcher=Load Files")
public final class LoadMatcher implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Profile profile = ProjectCentral.instance().getProfile();
        if (profile == null) {
            return;
        }
        final JFileChooser fc = new JFileChooser();
        if (profile.getLastWorkingDirectory() == null || profile.getLastWorkingDirectory().isEmpty()) {
            fc.setCurrentDirectory(new java.io.File("."));
        } else {
            fc.setCurrentDirectory(new File(profile.getLastWorkingDirectory()));
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
            try {
                long time = System.currentTimeMillis();
                Files.walkFileTree(Paths.get(profile.getWorkingDirectory()), fileMatcher);

                System.out.println("Serching ended. Matched = "
                        + profile.getFileCentral().getMatchedFiles().size()
                        + " Time = " + (System.currentTimeMillis() - time)
                );
                

                FileProcessor fileProcessor = new FileProcessor(profile, profile.getCleaners());
                profile.setFileProcessor(fileProcessor);
            } catch (IOException ex) {
                Logger.getLogger(LoadMatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Logger.getLogger(LoadMatcher.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
}
