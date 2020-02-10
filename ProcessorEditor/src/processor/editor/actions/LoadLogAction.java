/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import com.datapro.nfp.core.file.VariableHolder;
import processor.editor.windows.EditorLogTopComponent;
import processor.genericeditor.windows.LogEditor;
import com.datapro.nfp.profile.log.FilesLog;

@ActionID(
        category = "File",
        id = "processor.editor.actions.LoadLogAction"
)
@ActionRegistration(
        displayName = "#CTL_LoadLogAction"
)
@ActionReference(path = "Menu/File", position = 787)
@Messages("CTL_LoadLogAction=Load Log")
public final class LoadLogAction extends processor.genericeditor.actions.LoadLogAction {


    @Override
    public void openProjectWindow(FilesLog log, VariableHolder data) {
        EditorLogTopComponent editorLogTopComponent = new EditorLogTopComponent();
        editorLogTopComponent.open();
        editorLogTopComponent.addLogPanel(new LogEditor(log, data));
    }
}
