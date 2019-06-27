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
import processor.core.file.FileWalker;
import processor.core.file.ProjectCentral;

public class ProcessAllFilesAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        int response = JOptionPane.showConfirmDialog(null, "Are you shure you want to process all the files?");
        if (response == JOptionPane.YES_OPTION) {
            ProjectCentral.instance().getProfile().getFileProcessor().processAll(true);
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
