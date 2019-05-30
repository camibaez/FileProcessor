/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Exceptions;
import processor.genericeditor.ProjectCentral;
import processor.profile.ProfileAdministration;


public class SaveProjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(ProjectCentral.instance().getProfile() == null)
                throw new Exception("No project loaded");
            ProfileAdministration.saveProject(ProjectCentral.instance().getProfile(), ProjectCentral.instance().getProfileFile().getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
