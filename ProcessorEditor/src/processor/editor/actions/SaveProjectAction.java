/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import processor.editor.ProjectCentral;
import processor.project.ProjectAdministration;

@ActionID(
        category = "File",
        id = "processor.editor.actions.SaveProjectAction"
)
@ActionRegistration(
        displayName = "#CTL_SaveProjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Shortcuts", name = "D-S")
})
@Messages("CTL_SaveProjectAction=Save Project")
public final class SaveProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(ProjectCentral.instance().getProject() == null)
                throw new Exception("No project loaded");
            ProjectAdministration.saveProject(ProjectCentral.instance().getProject(), ProjectCentral.instance().getProjectFile().getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
