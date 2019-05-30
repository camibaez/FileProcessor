/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import processor.genericeditor.ProjectCentral;


public class ProcessAllFiles implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(null, "Are you shure you want to process all the files?");
        if(response == JOptionPane.YES_OPTION){
            ProjectCentral.instance().getProfile().getFileProcessor().processAll();
        }
    }
}
