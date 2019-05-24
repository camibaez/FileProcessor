/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import processor.editor.ProjectCentral;
import processor.project.ProjectAdministration;

@ActionID(
        category = "File",
        id = "processor.editor.OpenProjectAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenProjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300),
    @ActionReference(path = "Shortcuts", name = "D-O")
})
@Messages("CTL_OpenProjectAction=Open Project")
public final class OpenProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Open project");
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ProjectCentral.instance().setProject(ProjectAdministration.loadProject(file.getAbsolutePath()));

        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
}
