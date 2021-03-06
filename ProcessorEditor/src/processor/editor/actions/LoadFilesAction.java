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
public final class LoadFilesAction extends com.datapro.nfp.ui.actions.LoadFilesAction{

    @Override
    public void openProcessorWindow() {
        new EditorProcessorTopComponent().open();
        new ConsoleEditorTopComponent().open();
    }

   
}
