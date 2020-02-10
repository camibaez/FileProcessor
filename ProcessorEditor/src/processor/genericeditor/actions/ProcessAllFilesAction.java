/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.datapro.nfp.core.file.FileWalker;
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.profile.DIEmulator;

public class ProcessAllFilesAction implements ActionListener {
    ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    @Override
    public void actionPerformed(ActionEvent e) {

        int response = JOptionPane.showConfirmDialog(null, "Are you shure you want to process all the files?");
        if (response == JOptionPane.YES_OPTION) {
            projectCentral.getProfile().getFileProcessor().processAll(true);
            /*
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Select backup directory");

            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);

            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                
                
            }
            */
            
        }

    }
}
