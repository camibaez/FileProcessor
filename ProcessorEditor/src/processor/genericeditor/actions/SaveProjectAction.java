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
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.profile.DIEmulator;
import com.datapro.nfp.profile.ProfileSerializer;

public class SaveProjectAction implements ActionListener {
    ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (projectCentral.getProfile() == null) {
                throw new Exception("No project loaded");
            }
            //System.out.println(generateProfileJSON(ProjectCentral.instance().getProfile()));
            ProfileSerializer.saveProject(projectCentral.getProfile(), projectCentral.getProfileFile().getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
