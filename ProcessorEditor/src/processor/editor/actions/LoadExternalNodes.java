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

@ActionID(
        category = "File",
        id = "processor.editor.actions.LoadExternalNodes"
)
@ActionRegistration(
        displayName = "#CTL_LoadExternalNodes"
)
@ActionReference(path = "Menu/File", position = 0)
@Messages("CTL_LoadExternalNodes=Load External Nodes")
public final class LoadExternalNodes extends processor.genericeditor.actions.LoadExternalNodes {
    
}
