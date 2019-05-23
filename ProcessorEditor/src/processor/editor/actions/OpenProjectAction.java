/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
        File file = new File("C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\proj1.json");
        ProjectCentral.instance().setProfile(ProjectAdministration.loadProject(file.getAbsolutePath()));
    }
}
