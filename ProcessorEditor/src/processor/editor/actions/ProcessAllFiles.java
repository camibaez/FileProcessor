/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import processor.editor.ProjectCentral;

@ActionID(
        category = "File",
        id = "processor.editor.actions.ProcessAllFiles"
)
@ActionRegistration(
        displayName = "#CTL_ProcessAllFiles"
)
@ActionReferences({
     @ActionReference(path = "Menu/File", position = 502),
    @ActionReference(path = "Menu/Run", position = 2),
    @ActionReference(path = "Shortcuts", name = "DS-R")
})
@Messages("CTL_ProcessAllFiles=Process All Files")
public final class ProcessAllFiles implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(null, "Are you shure you want to process all the files?");
        if(response == JOptionPane.YES_OPTION){
            ProjectCentral.instance().getProfile().getFileProcessor().processAll();
        }
    }
}
