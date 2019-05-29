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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.text.StyledDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import processor.editor.ProjectCentral;
import processor.editor.windows.ProjectEditorTopComponent;
import processor.project.ProjectAdministration;

@ActionID(
        category = "File",
        id = "processor.editor.actions.NewProjectAcion"
)
@ActionRegistration(
        displayName = "#CTL_NewProjectAcion"
)
@ActionReference(path = "Menu/File", position = 101)
@Messages("CTL_NewProjectAcion=New Project")
public final class NewProjectAcion implements ActionListener {

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
                    ProjectAdministration.createEmptyProject(file.getAbsolutePath());
                    ProjectCentral.instance().setProfile(ProjectAdministration.loadProject(file.getAbsolutePath()));
                    ProjectCentral.instance().setProfileFile(file);
                    new ProjectEditorTopComponent().open();
                    
                    
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }
    
   
}
