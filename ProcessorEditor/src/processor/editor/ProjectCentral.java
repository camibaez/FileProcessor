/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor;

import java.io.File;
import processor.core.file.Profile;
import processor.project.ProjectAdministration;

/**
 *
 * @author cbaez
 */
public class ProjectCentral {

    protected static ProjectCentral instance;
    protected static Profile profile;
    protected static File profileFile;

    public static ProjectCentral instance() {
        if (instance == null) {
            instance = new ProjectCentral();
        }
        return instance;
    }

    
    public void reloadProject(){
        profile = ProjectAdministration.loadProject(profileFile.getAbsolutePath());
    }
    
    
    
    public void setProject(Profile p) {
        profile = p;

    }

    public Profile getProject() {
        return profile;
    }

    public File getProjectFile() {
        return profileFile;
    }

    public void setProjectFile(File profileFile) {
        ProjectCentral.profileFile = profileFile;
    }

}
