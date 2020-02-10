/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import processor.editor.windows.ConsoleEditorTopComponent;
import processor.editor.windows.EditorProcessorTopComponent;
import processor.editor.windows.ProjectEditorTopComponent;

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
public final class OpenProjectAction extends processor.genericeditor.actions.OpenProjectAction {

    @Override
    public void openProjectWindow() {
        new ProjectEditorTopComponent().open();
        new EditorProcessorTopComponent().open();
        new ConsoleEditorTopComponent().open();
        //new ProjectExplorerTopComponent().open();
        
    }

}
