/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor;

import java.io.File;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class ProjectCentral {

    protected static ProjectCentral instance;
    protected static Profile currentProfile;
    protected static File profileFile;

    public static ProjectCentral instance() {
        if (instance == null) {
            instance = new ProjectCentral();
        }
        return instance;
    }

    public void setProject(Profile p) {
        currentProfile = p;

    }

    public Profile getProject() {
        return currentProfile;
    }

    public File getProjectFile() {
        return profileFile;
    }

    public void setProjectFile(File profileFile) {
        ProjectCentral.profileFile = profileFile;
    }

}
