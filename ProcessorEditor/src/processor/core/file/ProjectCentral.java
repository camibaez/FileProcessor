/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.io.File;
import processor.profile.ProfileAdministration;

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
        profile = ProfileAdministration.loadProject(profileFile);
    }
    
    
    
    public void setProfile(Profile p) {
        profile = p;

    }

    public Profile getProfile() {
        return profile;
    }

    public File getProfileFile() {
        return profileFile;
    }

    public void setProfileFile(File profileFile) {
        ProjectCentral.profileFile = profileFile;
    }

}
