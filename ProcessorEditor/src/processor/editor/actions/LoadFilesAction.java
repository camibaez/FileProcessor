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
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import processor.core.file.FileMatcher;
import processor.core.file.FileProcessor;
import processor.core.file.Profile;
import processor.editor.EditorProcessorTopComponent;
import processor.editor.ProjectCentral;

@ActionID(
        category = "File",
        id = "processor.editor.actions.LoadFilesAction"
)
@ActionRegistration(
        iconBase = "processor/editor/actions/icon.png",
        displayName = "#CTL_LoadFilesAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 401),
    @ActionReference(path = "Menu/Run", position = 1),
    @ActionReference(path = "Toolbars/File", position = 0),
    @ActionReference(path = "Shortcuts", name = "D-L")
})
@Messages("CTL_LoadFilesAction=Load Files")
public final class LoadFilesAction implements ActionListener {

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
                        Exceptions.printStackTrace(ex);
                    }
                }
            }).start();

            FileProcessor fileProcessor = new FileProcessor(profile, profile.getCleaners());
            profile.setFileProcessor(fileProcessor);
            new EditorProcessorTopComponent().open();

        } else {
            Logger.getLogger(LoadFilesAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
}
