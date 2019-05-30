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
import processor.genericeditor.ProjectCentral;
import processor.editor.windows.ProjectEditorTopComponent;

import processor.profile.ProfileAdministration;

@ActionID(
        category = "File",
        id = "processor.editor.OpenProjectAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenProjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 102),
    @ActionReference(path = "Shortcuts", name = "D-O")
})
@Messages("CTL_OpenProjectAction=Open Project")
public final class OpenProjectAction extends processor.genericeditor.actions.OpenProjectAction{

    
    
}
