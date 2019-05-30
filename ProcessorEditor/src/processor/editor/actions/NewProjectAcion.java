/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import processor.editor.windows.ProjectEditorTopComponent;

@ActionID(
        category = "File",
        id = "processor.editor.actions.NewProjectAcion"
)
@ActionRegistration(
        displayName = "#CTL_NewProjectAcion"
)
@ActionReference(path = "Menu/File", position = 101)
@Messages("CTL_NewProjectAcion=New Project")
public final class NewProjectAcion extends processor.genericeditor.actions.NewProjectAcion {

    @Override
    public void openProjectEditorWindow() {
         new ProjectEditorTopComponent().open();
    }
    
}
